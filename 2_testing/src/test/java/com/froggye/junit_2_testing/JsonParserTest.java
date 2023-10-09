
package com.froggye.junit_2_testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


public class JsonParserTest {
    
    private final JsonParser parser;
    
    public JsonParserTest() {
        
        // Arrange
        parser = new JsonParser();
        
    }

   
    // ===== readURL =====
    
    private static final String REPLACEMENT_URL = 
                "https://raw.githubusercontent.com/froggye/WS-homework/main/2_testing/src/main/resources/replacement_test.json";
    private static final String DATA_URL = 
                "https://raw.githubusercontent.com/froggye/WS-homework/main/2_testing/src/main/resources/data_test.json";
    private static final String EMPTY_URL = 
                "https://raw.githubusercontent.com/froggye/WS-homework/main/2_testing/src/main/resources/empty.json";
    private static final String DOESNT_EXIST_URL = 
                "https://raw.githubusercontent.com/froggye/WS-homework/main/nothing.json";
    
    
    @Test
    @DisplayName("Прочитать файл с тестовыми заменами из url")
    public void readRegularURL() throws IOException 
    {   
        
        // Act
        ArrayList<Replacement> actualReplacement = parser.readURL(REPLACEMENT_URL,
                Replacement.class);
        
        // Assert
        ArrayList<Replacement> expectReplacement = 
                Stream.of(new Replacement("TEST", "Only one item here"))
                        .collect(Collectors.toCollection(ArrayList::new));
        Assertions.assertEquals(expectReplacement, actualReplacement);
        
    }
    
    @Test
    @DisplayName("Прочитать файл из url с неправильным типом")
    public void readInputMismatchURL() throws IOException 
    {   
        
        // Act
        ArrayList<Replacement> actualData = parser.readURL(DATA_URL,
                Replacement.class);
        
        // Assert
        Assertions.assertNull(actualData);
        
    }
    
    @Test
    @DisplayName("Прочитать пустой файл из url")
    public void readEmptyURL() throws IOException 
    {   
        
        // Act
        ArrayList<String> actualData = parser.readURL(EMPTY_URL,
                String.class);
        
        // Assert
        Assertions.assertNull(actualData);
        
    }
    
    @Test
    @DisplayName("Прочитать из несуществующего url")
    public void readNonexistentURL() throws IOException 
    {   
        
        // Act
        ArrayList<String> actualData = parser.readURL(DOESNT_EXIST_URL,
                String.class);
        
        // Assert
        Assertions.assertNull(actualData);
        
    }
    
    @Test
    @DisplayName("Не url")
    public void readNoURL() throws IOException 
    {   
        
        // Act
        ArrayList<String> actualData = parser.readURL("url",
                String.class);
        
        // Assert
        Assertions.assertNull(actualData);
        
    }
    
    
    
    // ===== readLocal =====
    
    private static final String DATA_LOCAL = 
                "data_test.json";
    private static final String REPLACEMENT_LOCAL = 
                "replacement_test.json";
    private static final String EMPTY_LOCAL = 
                "empty.json";
    private static final String DOESNT_EXIST_LOCAL = 
                "nothing.json";
    
    @Test
    @DisplayName("Прочитать файл с тестовыми данными")
    public void readRegularLocal() throws IOException 
    {   
        
        // Act
        ArrayList<String> actualData = parser.readLocal(DATA_LOCAL,
                String.class);
        
        // Assert
        ArrayList<String> expectData = 
                Stream.of("All by myself here :)")
                        .collect(Collectors.toCollection(ArrayList::new));
        Assertions.assertEquals(expectData, actualData);
        
    }
    
    @Test
    @DisplayName("Прочитать файл с неправильным типом")
    public void readInputMismatchLocal() throws IOException 
    {   
        
        // Act
        ArrayList<String> actualReplacement = parser.readLocal(REPLACEMENT_LOCAL,
                String.class);
        
        // Assert
        Assertions.assertNull(actualReplacement);
        
    }
    
    @Test
    @DisplayName("Прочитать пустой файл")
    public void readEmptyLocal() throws IOException 
    {   
        
        // Act
        ArrayList<String> actualData = parser.readURL(EMPTY_LOCAL,
                String.class);
        
        // Assert
        Assertions.assertNull(actualData);
        
    }
    
    @Test
    @DisplayName("Прочитать из несуществующего файла")
    public void readNonexistentLocal() throws IOException 
    {   
        
        // Act
        ArrayList<String> actualData = parser.readURL(DOESNT_EXIST_LOCAL,
                String.class);
        
        // Assert
        Assertions.assertNull(actualData);
        
    }
    
    
    // ===== writeFile =====
    
    @Test
    @DisplayName("Записать массив элементов составного типа")
    public void writeFile() {
        
        // Arrange
        ArrayList<Replacement> toWrite = 
                Stream.of(new Replacement("TEST", "Only one item here"))
                        .collect(Collectors.toCollection(ArrayList::new));
        
        // Assert
        Assertions.assertTrue(parser.writeFile(toWrite));
        
    }
    
    @Test
    @DisplayName("Записать пустой массив")
    public void writeEmptyFile() {
        
        // Arrange
        ArrayList<Replacement> emptyWrite = new ArrayList<Replacement>();
        
        // Assert
        Assertions.assertTrue(parser.writeFile(emptyWrite));
        
    }
    
}