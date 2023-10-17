
package com.froggye.junit_2_testing;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;


public class GetString {
    
    public GetString() {}
    
    // Перевести HttpsURLConnection в строку
    public String getFromURLConnection(HttpsURLConnection connection) 
            throws IOException {        
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            return String.valueOf(sb);
        }
        catch (Exception ex) {
            return null;
        }
        
    }
    
    
    // Перевести ресурс в строку
    public String getFromResource(String resourcePath) throws IOException
    {
        GetString instance = new GetString();
        InputStream is = instance.getFileAsIOStream(resourcePath);
        
        return new String(is.readAllBytes());
    }
    
    
    // получить поток ввода из файла внутри проекта
    private InputStream getFileAsIOStream(final String fileName) 
    {
        InputStream ioStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream(fileName);
        
        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }
    
}
