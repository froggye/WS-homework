
package com.froggye.junit_2_testing;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JsonParserReadLocalTest {
    private JsonParser parser;
    
    @BeforeEach
    public void setup() {
        parser = new JsonParser();
    }
    
    
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
        List<String> actualData = parser.readLocal(DATA_LOCAL,
                String.class);
        
        // Assert
        List<String> expectData = Collections.singletonList("All by myself here :)");
        Assertions.assertEquals(expectData, actualData);
        
    }
    
    @Test
    @DisplayName("Прочитать файл с неправильным типом")
    public void readInputMismatchLocal() throws IOException 
    {   
        
        // Act
        List<String> actualReplacement = parser.readLocal(REPLACEMENT_LOCAL,
                String.class);
        
        // Assert
        Assertions.assertNull(actualReplacement);
        
    }
    
    @Test
    @DisplayName("Прочитать пустой файл")
    public void readEmptyLocal() throws IOException 
    {   
        
        // Act
        List<String> actualData = parser.readLocal(EMPTY_LOCAL,
                String.class);
        
        // Assert
        Assertions.assertNull(actualData);
        
    }
    
    @Test
    @DisplayName("Прочитать из несуществующего файла")
    public void readNonexistentLocal() throws IOException 
    {   
        
        // Act        
        Throwable exception = assertThrows(
            IllegalArgumentException.class, () -> {
                parser.readLocal(DOESNT_EXIST_LOCAL,
                String.class);
            }
        );
        
        // Assert
        Assertions.assertEquals(
                DOESNT_EXIST_LOCAL + " is not found", 
                exception.getMessage());
        
    }
}
