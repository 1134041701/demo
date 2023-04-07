package com.example.demo.util;

import lombok.Data;

import java.util.List;


/**
 * @Classname HttpBody
 * @Description TODO
 * @Date 2023/1/4 17:38
 * @Created by Dongbo
 */
@Data
public class HttpBody {
    private String raw;

    private String jsonData;

    private List<KeyValuePair> urlEncodedPairs;

    private List<SimpleFormDataPair> formDataPairs;

}
