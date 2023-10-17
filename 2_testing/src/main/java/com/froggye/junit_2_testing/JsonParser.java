
package com.froggye.junit_2_testing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class JsonParser {
    
    // чтение и запись json файлов
    // 1. Чтение из URL
    // 2. Чтение из файла в проекте
    // 3. Запись json файла
    
    private final ObjectMapper objectMapper;
    private final GetString getString;
    
    public JsonParser() {
        objectMapper = new ObjectMapper();
        getString = new GetString();
    }
    
    // прочитать из url
    // возвращает список элементов типа или класса itemType
    public <T> List<T> readURL(final String url, Class<T> itemType) 
            throws IOException {   
        if (!isUrlValid(url)){
            throw new MalformedURLException(url + " is not valid URL");
        }
        
        URL urlObj = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection)urlObj.openConnection();
        connection.setRequestMethod("GET");
        
        if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            // файл недоступен
            return null;
        }
        // успешно получили файл
            
        // прочитать как строку и переделать в список нужного типа    
        String urlContent = getString.getFromURLConnection(connection);
          
        try {
            if(urlContent.length() > 0){
                TypeFactory t = TypeFactory.defaultInstance(); // нужен, чтобы правильно составить ArrayList из элементов itemType
                
                return objectMapper.readValue(
                        urlContent,
                        t.constructCollectionType(ArrayList.class,itemType));
            } else {
                // пустой файл
                throw new EmptyStackException();
            }
        }
        catch(MismatchedInputException | EmptyStackException e) {
            return null;
        }
            
    }
    
    private static boolean isUrlValid(String url) {
      try {
         URL obj = new URL(url);
         obj.toURI();
         return true;
      } catch (MalformedURLException | URISyntaxException e) {
         return false;
      }
   }
    
    // прочитать из файла
    // возвращает список элементов типа или класса itemClass
    public <T> List<T> readLocal(final String path, Class<T> itemType) 
            throws IOException {   
        try {
            String localContent = getString.getFromResource(path);
            TypeFactory t = TypeFactory.defaultInstance();
            
            return objectMapper.readValue(
                    localContent,
                    t.constructCollectionType(ArrayList.class,itemType));
        }
        catch (FileNotFoundException | MismatchedInputException e){
            return null;
        }
    
        
    }
    
    // создать файл json с массивом объектов
    public <T> boolean writeFile(List<T> data, String path) {
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(path + "result.json"), data);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
}