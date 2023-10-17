
package com.froggye.junit_2_testing;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
//import okhttp3.*; 
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


//@WireMockTest(httpsEnabled = true, httpsPort = 9090)
public class JsonParserReadURLTest {
    private JsonParser parser;
    private WireMockServer wireMockServer;
    
    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer();
        configureFor("https", "localhost", 8443);
//        configureFor("localhost", 8080);
        wireMockServer.start();
        
        parser = new JsonParser();
    }
    
    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }
    
    // Рабочие тесты
    
//    @Test
//    @DisplayName("Работа WireMock")
//    public void checkWireMock() throws IOException {
//
//        // Arrange
//        stubFor(get(urlPathEqualTo("/test"))
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", "text/plain")
//                        .withBody("Successfully created URL")));
//        
//        // Act 
//        OkHttpClient client = new OkHttpClient().newBuilder() 
//                .build(); 
//        Request request = new Request.Builder() 
//                .url("https://localhost:8080/test") 
//                .method("GET", null) 
//                .build(); 
//
//        Response response = client.newCall(request).execute(); 
//  
//        // Assert
//        Assertions.assertEquals(
//                "Successfully created URL", 
//                response.body().string()); 
        
        // ===========================================

//        stubFor(get(urlPathEqualTo("/test"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withStatusMessage("Everything was just fine!")
//                        .withHeader("Content-Type", "text/plain")));
//        
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("http://localhost:8080/some/thing/else")
//                .build();
//        Response httpResponse = client.newCall(request).execute();
//        Integer code = httpResponse.code();
//
//        Assertions.assertEquals(
//                404, 
//                code); 
//
//    }
    
    
    @Test
    @DisplayName("Прочитать файл с тестовыми заменами из url")
    public void readRegularURL() throws IOException {
        
        // Arrange
//        stubFor(get(urlPathEqualTo("/replacement"))
        stubFor(any(anyUrl())
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("[\n"
                                + "  {\n"
                                + "    \"replacement\": \"TEST\",\n"
                                + "    \"source\": \"Only one item here\"\n"
                                + "  }\n"
                                + "]")));

        // Act
        List<Replacement> actualReplacement = parser.readURL(
                "https://localhost:8443/replacement",
                Replacement.class);
        
        /*
        *   Unsupported or unrecognized SSL message
        */

        // Assert
        List<Replacement> expectReplacement = Collections.singletonList(
                new Replacement("TEST", "Only one item here"));
        Assertions.assertEquals(expectReplacement, actualReplacement);

    }
    
    @Test
    @DisplayName("Прочитать файл из url с неправильным типом")
    public void readInputMismatchURL() throws IOException 
    {   
        
        // Arrange
        stubFor(get(urlPathEqualTo("/data"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("[\n"
                                + "  \"All by myself here :)\""
                                + "]")));
        
        // Act
        List<Replacement> actualData = parser.readURL(
                "http://localhost:8080/data",
                Replacement.class);
        
        /*
        Если переключить конфигурацию на http
        
        class sun.net.www.protocol.http.HttpURLConnection cannot be cast to class 
        javax.net.ssl.HttpsURLConnection 
        */
        
        // Assert
        Assertions.assertNull(actualData);
        
    }
    
    @Test
    @DisplayName("Прочитать пустой файл из url")
    public void readEmptyURL() throws IOException 
    {   
        // Arrange
        stubFor(get(urlPathEqualTo("/empty"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("")));
        
        // Act
        List<String> actualData = parser.readURL(
                "http://localhost:8443/empty",
                String.class);
        
        // Assert
        Assertions.assertNull(actualData);
        
    }
    
    @Test
    @DisplayName("Прочитать из несуществующего url")
    public void readNonexistentURL() throws IOException 
    {   
        // Arrange
        stubFor(get(urlPathEqualTo("/data"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("")));
        
        // Act
        List<String> actualData = parser.readURL(
                "http://localhost:8443/nothing",
                String.class);
        
        // Assert
        Assertions.assertNull(actualData);
        
    }
    
    @Test
    @DisplayName("Не url")
    public void readNoURL() throws IOException 
    {   
        
        // Act        
        Throwable exception = Assertions.assertThrows(
            MalformedURLException.class, () -> {
                parser.readURL("url",
                String.class);
            }
        );
        
        // Assert
        Assertions.assertEquals(
                "url is not valid URL", 
                exception.getMessage());
        
    }
}
