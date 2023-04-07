package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
public class HttpSendUtil {

    private static String  reponseContent;

    private static Charset charset=Charset.forName("utf-8");

    public static Map<String,String> sendRequest(String requestMethod, String url, Map<String, String> headers, Map<String, String> parameters,
                                                  Map<String, Object> requestBody) {
        String body = requestBody == null ? null : JSON.toJSONString(requestBody);
        return sendRequest(requestMethod, url, headers, parameters, body);
    }

    public static Map<String,String> sendRequest(String requestMethod, String url, Map<String, String> headers, Map<String, String> parameters,
                                                  String body) {
        HttpBody httpBody = new HttpBody();
        httpBody.setJsonData(body);
        return sendRequest(requestMethod, url, headers, parameters, httpBody, "json");
    }

    public static Map<String,String> sendRequest(String requestMethod, String url, Map<String, String> headers, Map<String, String> parameters,
                                                  HttpBody body, String encrypt) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpRequestBase request;
        Map<String, String> res = null;
        requestMethod = requestMethod.toUpperCase();
        try {
            switch (requestMethod) {
                case "GET":
                    request = new HttpGet(url);
                    break;
                case "POST":
                    request = new HttpPost(url);
                    break;
                case "HEAD":
                    request = new HttpHead(url);
                    break;
                case "PUT":
                    request = new HttpPut(url);
                    break;
                case "DELETE":
                    request = new HttpDelete(url);
                    break;
                default:
                    return null;
            }
            //设置timeout
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).setRedirectsEnabled(false).build();
            request.setConfig(requestConfig);
            // 构建请求头
            setRequestHeaders(request, headers);
            // 构建请求参数
            setRequestParams(request, parameters);
            // 构建请求体
            if (request instanceof HttpEntityEnclosingRequestBase) {
                setRequestBody((HttpEntityEnclosingRequestBase) request, body, encrypt);
            };
            response = client.execute(request);
            res = new HashMap<>();
            int responseStatus = response.getStatusLine().getStatusCode();
            res.put("statusCode", Integer.toString(responseStatus));
            readResponseHeaders(response, res);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String data = EntityUtils.toString(responseEntity, Consts.UTF_8);
                res.put("resBody", data);
            }
        } catch (Exception e) {
            log.error("发送请求失败! 请求信息：{}", JSON.toJSONString(res), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private static void readResponseHeaders(HttpResponse response, Map<String, String> res) {
        Header[] headerArray = response.getAllHeaders();
        if (headerArray != null) {
            for (Header header : headerArray) {
                res.put(header.getName(), header.getValue());
            }
        }
    }

    private static void setRequestHeaders(HttpRequestBase httpRequest, Map<String, String> headers) {
        if (headers != null) {
            headers.keySet().forEach(header -> {
                if (StringUtils.isNotBlank(header)) {
                    httpRequest.addHeader(header, headers.get(header));
                }
            });
        }
    }

    private static List<NameValuePair> mapToNap(Map<String, String> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        params.keySet().forEach(key -> {
            NameValuePair nameValuePair = new BasicNameValuePair(key, params.get(key));
            nameValuePairs.add(nameValuePair);
        });
        return nameValuePairs;
    }

    private static void setRequestParams(HttpRequestBase httpRequest, Map<String, String> parameters) throws IOException, URISyntaxException {
        if (parameters != null) {
            String param = EntityUtils.toString(new UrlEncodedFormEntity(mapToNap(parameters), Consts.UTF_8));
            String uri = httpRequest.getURI().toString();
            if (uri.contains("?")) {
                httpRequest.setURI(new URIBuilder(uri + "&" + param).build());
            } else {
                httpRequest.setURI(new URIBuilder(uri + "?" + param).build());
            }
        }
    }

    private static void setRequestBody(HttpEntityEnclosingRequestBase httpRequest, HttpBody body, String enctype) {
        if (body != null && enctype != null) {
            switch (enctype) {
                case "x-www-form-urlencoded":
                    List<NameValuePair> list = constructNameValuePairs(body.getUrlEncodedPairs());
                    if (list.size() > 0) {
                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
                        httpRequest.setEntity(entity);
                    }
                    break;
                case "form-data":
                    if (!body.getFormDataPairs().equals(null) && !body.getFormDataPairs().equals("")) {
                        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                        body.getFormDataPairs().forEach(pair -> {
                            if (StringUtils.isNotBlank(pair.getKey()) && StringUtils.isNotBlank(pair.getValue())) {
                                if ("file".equals(pair.getType())) {
                                    builder.addBinaryBody(pair.getKey(), new File(pair.getValue()));
                                } else if ("text".equals(pair.getType())) {
                                    ContentType contentType = ContentType.create("text/plain", Consts.UTF_8);
                                    builder.addTextBody(pair.getKey(), pair.getValue(), contentType);
                                }
                            }
                        });
                        httpRequest.setEntity(builder.build());
                    }
                    break;
                case "raw":
                    if (StringUtils.isNotBlank(body.getRaw())) {
                        httpRequest.setEntity(new StringEntity(body.getRaw().trim(), Consts.UTF_8));
                    }
                    break;
                case "json":
                    if (StringUtils.isNotBlank(body.getJsonData())) {
                        httpRequest.addHeader("Content-Type", "application/json");
                        httpRequest.setEntity(new StringEntity(body.getJsonData().trim(), Consts.UTF_8));
                    }
                    break;
            }

        }
    }

    /**
     * 将 参数列表 转换成 List<NameValuePair>
     *
     * @param parameters
     * @return
     */
    private static List<NameValuePair> constructNameValuePairs(List<KeyValuePair> parameters) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if (parameters != null && !parameters.equals("")) {
            parameters.forEach(parameter -> {
                if (StringUtils.isNotBlank(parameter.getKey())) {
                    list.add(new BasicNameValuePair(parameter.getKey(), parameter.getValue()));
                }
            });
        }
        return list;
    }

    public static void main(String[] args) {
        String url = "https://chandao.fcbox.com/biz/dataset-ajaxQuery.json";
        Map<String, String> header = new HashMap<>();
        header.put("Cookie", "zentaosid=5d34c992e02c19145b1fb67858d06012;");
        Map<String, String> params = new HashMap<>();
        params.put("zentaosid", "5d34c992e02c19145b1fb67858d06012");
        HttpBody execution = new HttpBody();
        SimpleFormDataPair pair = new SimpleFormDataPair();
        pair.setType("text");
        pair.setKey("sql");
        pair.setValue("select t.* from zt_case t limit 10");
        execution.setFormDataPairs(Collections.singletonList(pair));
        Map<String, String> post = sendRequest("POST", url, header, params, execution, "form-data");
        System.out.println(post);
    }
}
