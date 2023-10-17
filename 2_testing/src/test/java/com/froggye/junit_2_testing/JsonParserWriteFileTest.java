
package com.froggye.junit_2_testing;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


public class JsonParserWriteFileTest {
    private final JsonParser parser;
    
    public JsonParserWriteFileTest() {
        
        // Arrange
        parser = new JsonParser();
        
    }
    
    @TempDir 
    File tempDir;
    
    @Test
    @DisplayName("Записать массив элементов составного типа")
    public void writeFile() {
        
        Assertions.assertTrue(this.tempDir.isDirectory());
        
        // Arrange

        List<Replacement> toWrite = Collections
                .singletonList(new Replacement("TEST", "Only one item here"));
        
        // Act + Assert
        Assertions.assertTrue(parser.writeFile(toWrite, tempDir.toString()));
        
        
    }
    
    @Test
    @DisplayName("Записать пустой массив")
    public void writeEmptyFile() {
        
        Assertions.assertTrue(this.tempDir.isDirectory());
        
        // Arrange
        List<Replacement> emptyWrite = new ArrayList<>();
        
        // Assert
        Assertions.assertTrue(parser.writeFile(emptyWrite, tempDir.toString()));
        
    }
}
