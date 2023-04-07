/*
 * This file was automatically generated by SmartUnit
 */

package smartunit.com.example.demo.util;

import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Method;
import com.example.demo.util.HttpBody;
import com.example.demo.util.KeyValuePair;
import com.example.demo.util.SimpleFormDataPair;
import java.util.LinkedList;
import java.util.List;
import org.junit.runner.RunWith;
import org.smartunit.runtime.SmartRunner;
import org.smartunit.runtime.SmartRunnerParameters;

@RunWith(SmartRunner.class) @SmartRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true) 
public class HttpBody_SSTest extends HttpBody_SSTest_scaffolding {
// allCoveredLines:[14, 16, 18, 20, 22]

  @Test(timeout = 4000)
  public void test_equals_0()  throws Throwable  {
      //caseID:a11605d07e3db7cc77a62c7df352b235
      //CoveredLines: [14, 14, 14, 14, 14, 14, 14, 14, 14]
      //Input_0_Object: null
      //Assert: assertFalse(method_result);
      
      HttpBody httpBody0 = new HttpBody();
      
      //Call method: equals
      boolean boolean0 = httpBody0.equals(null);
      
      //Test Result Assert
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test_equals_1()  throws Throwable  {
      //caseID:9a985504597468d0bf103512494ebac0
      //CoveredLines: [14, 14, 14, 14, 14, 14, 14, 14, 14]
      //Input_0_Object: httpBody0
      //Assert: assertTrue(method_result);
      
      HttpBody httpBody0 = new HttpBody();
      
      //Call method: equals
      boolean boolean0 = httpBody0.equals(httpBody0);
      
      //Test Result Assert
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test_equals_2()  throws Throwable  {
      //caseID:6d0586e97694d3e70f75deace10f4f6f
      //CoveredLines: [14, 14, 14, 14, 14, 14, 14, 14, 14, 16, 18, 20, 22]
      //Input_0_Object: httpBody0
      //Assert: assertTrue(method_result);
      
      HttpBody httpBody0 = new HttpBody();
      HttpBody httpBody1 = new HttpBody();
      
      //Call method: equals
      boolean boolean0 = httpBody1.equals(httpBody0);
      
      //Test Result Assert
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test_equals_3()  throws Throwable  {
      //caseID:97700c913cdec2b7142973f5f2dd1624
      //CoveredLines: [14, 14, 14, 14, 14, 14, 14, 14, 14, 16, 18, 20, 22]
      //Input_0_Object: httpBody1
      //Assert: assertTrue(method_result);
      
      HttpBody httpBody0 = new HttpBody();
      HttpBody httpBody1 = new HttpBody();
      
      //Call method: equals
      boolean boolean0 = httpBody0.equals(httpBody1);
      
      //Test Result Assert
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test_hashCode_4()  throws Throwable  {
      //caseID:2fab3fa30bc74e05953b2aa03c1dd67c
      //CoveredLines: [14, 14, 14, 14, 14, 14, 14, 14, 14, 16, 18, 20, 22]
      
      HttpBody httpBody0 = new HttpBody();
      
      //Call method: hashCode
      httpBody0.hashCode();
  }

  @Test(timeout = 4000)
  public void test_setFormDataPairs_5()  throws Throwable  {
      //caseID:bf6c16b34157adcdd65041be09a7860b
      //CoveredLines: [14, 14, 14, 14, 14, 14, 14, 14, 14]
      //Input_0_List<SimpleFormDataPair>: linkedList0
      
      HttpBody httpBody0 = new HttpBody();
      LinkedList<SimpleFormDataPair> linkedList0 = new LinkedList<SimpleFormDataPair>();
      
      //Call method: setFormDataPairs
      httpBody0.setFormDataPairs(linkedList0);
      
      //Test Result Assert
      assertNull(httpBody0.getRaw());
  }

  @Test(timeout = 4000)
  public void test_setJsonData_6()  throws Throwable  {
      //caseID:29d349527f10804e82f3a342e522da74
      //CoveredLines: [14, 14, 14, 14, 14, 14, 14, 14, 14]
      //Input_0_String: \" header
      
      HttpBody httpBody0 = new HttpBody();
      
      //Call method: setJsonData
      httpBody0.setJsonData("\" header");
      
      //Test Result Assert
      assertEquals("\" header", httpBody0.getJsonData());
  }

  @Test(timeout = 4000)
  public void test_setRaw_7()  throws Throwable  {
      //caseID:dbb61cca36c67b88d2f3d34d46cd12a6
      //CoveredLines: [14, 14, 14, 14, 14, 14, 14, 14, 14]
      //Input_0_String: http
      
      HttpBody httpBody0 = new HttpBody();
      
      //Call method: setRaw
      httpBody0.setRaw("http");
      
      //Test Result Assert
      assertNull(httpBody0.getJsonData());
  }

  @Test(timeout = 4000)
  public void test_setUrlEncodedPairs_8()  throws Throwable  {
      //caseID:79365d3791dfdf8b320747b20f20cf26
      //CoveredLines: [14, 14, 14, 14, 14, 14, 14, 14, 14]
      //Input_0_List<KeyValuePair>: linkedList0
      
      HttpBody httpBody0 = new HttpBody();
      LinkedList<KeyValuePair> linkedList0 = new LinkedList<KeyValuePair>();
      
      //Call method: setUrlEncodedPairs
      httpBody0.setUrlEncodedPairs(linkedList0);
      
      //Test Result Assert
      assertNull(httpBody0.getJsonData());
  }

  @Test(timeout = 4000)
  public void test_toString_9()  throws Throwable  {
      //caseID:269619c8e45ea68726b43b0e972e4f28
      //CoveredLines: [14, 14, 14, 14, 14, 14, 14, 14, 14, 16, 18, 20, 22]
      //Assert: assertEquals("HttpBody(raw=null, jsonData=null, urlEncodedPairs=null, formDataPairs=null)", method_result);
      
      HttpBody httpBody0 = new HttpBody();
      
      //Call method: toString
      String string0 = httpBody0.toString();
      
      //Test Result Assert
      assertEquals("HttpBody(raw=null, jsonData=null, urlEncodedPairs=null, formDataPairs=null)", string0);
  }
}
