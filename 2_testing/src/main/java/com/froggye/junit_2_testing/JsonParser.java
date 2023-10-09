
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
import java.util.Scanner;


public class JsonParser {
    
    // чтение и запись json файлов
    // 1. Чтение из URL
    // 2. Чтение из файла в проекте
    // 3. Запись json файла
    
    private final ObjectMapper objectMapper;
    
    public JsonParser() {
        objectMapper = new ObjectMapper();
    }
    
    // прочитать из url
    // возвращает список элементов типа или класса itemType
    public <T> ArrayList<T> readURL(final String url, Class<T> itemType) 
            throws IOException {   
        if (!isUrlValid(url)){
            return null;
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
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(connection.getInputStream());
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
        }            
        scanner.close();
          
        try {
            if(sb.length() > 0){
                TypeFactory t = TypeFactory.defaultInstance(); // нужен, чтобы правильно составить ArrayList из элементов itemType
                
                return objectMapper.readValue(
                        String.valueOf(sb),
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
    public <T> ArrayList<T> readLocal(final String path, Class<T> itemType) 
            throws IOException {   
        try {
            JsonParser instance = new JsonParser();
            InputStream is = instance.getFileAsIOStream(path);
            TypeFactory t = TypeFactory.defaultInstance();
            
            return objectMapper.readValue(is,
                    t.constructCollectionType(ArrayList.class,itemType));
        }
        catch (FileNotFoundException | MismatchedInputException e){
            return null;
        }
    
        
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
    
    // создать файл json с массивом объектов
    public <T> boolean writeFile(ArrayList<T> data) {
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File("result.json"), data);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
}