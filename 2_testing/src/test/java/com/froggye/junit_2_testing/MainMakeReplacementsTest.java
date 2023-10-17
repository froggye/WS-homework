
package com.froggye.junit_2_testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class MainMakeReplacementsTest {
    
    @Test
    @DisplayName("Правильная работа перестановок")
    public void makeReplacementsCorrect() throws IOException 
    {   
        // Arrange
        
        List<String> data = Stream.of(
                "Two roads diverged in a yellow d12324344rgg6f5g6gdf2ddjf,",
                "Robert Frost poetAnd sorry I cou1d not trave1 both",
                "An other text",
                "And be one trave1er, long I stood",
                "And 1ooked down one as far as I cou1d",
                "Bla-bla-bla-blaaa, just some RANDOM tExT",
                "To where it bent in the undergrowth;",
                "Then Random text, yeeep the other, as just as fair,",
                "And having perhaps parentheses - that is a smart word,",
                "Bla-bla-bla-blaaa, just some RANDOM tExT",
                "Because it was grassy and wanted wear;",
                "An other text",
                "An other text",
                "Though as for that the passing there",
                "Emptry... or NOT! them rea11y about the same,",
                "And both that morning equally lay",
                "In 1eaves no step had trodden b1ack.",
                "Oh, I kept the first for another day!",
                "Yet Skooby-dooby-doooo 1eads on to way,",
                "Ha-haaa, hacked you.",
                "An other text",
                "I shall be te11ing this with a sigh",
                "sdshdjdskfm sfjsdif jfjfidjf",
                "Two roads diverged in a d12324344rgg6f5g6gdf2ddjf, and I",
                "I Random text, yeeep the one less traveled by,",
                "And that has made a11 the difference.",
                "Bla-bla-bla-blaaa, just some RANDOM tExT")
                .collect(Collectors.toList());
        
        List<Replacement> replacement = Stream.of(
                new Replacement("Ha-haaa, hacked you", "I doubted if I should ever come back"),
                new Replacement("sdshdjdskfm sfjsdif jfjfidjf", "Somewhere ages and ages hence:"),
                new Replacement("1", "l"),
                new Replacement("Emptry... or NOT!", null),
                new Replacement("d12324344rgg6f5g6gdf2ddjf", "wood"),
                new Replacement("Random text, yeeep", "took"),
                new Replacement("Bla-bla-bla-blaaa, just some RANDOM tExT", null),
                new Replacement("parentheses - that is a smart word", "the better claim"),
                new Replacement("sdshdjdskfm sfjsdif jfjfidjf", "Somewhere ages and ages hence:"),
                new Replacement("Emptry... or NOT!", "Had worn"),
                new Replacement("Skooby-dooby-doooo", "knowing how way leads on"),
                new Replacement("sdshdjdskfm sfjsdif jfjfidjf", "Somewhere ages and ages hence:"),
                new Replacement("An other text", null),
                new Replacement("Skooby-dooby-doooo", "knowing how way"))
                .collect(Collectors.toList());
        
        // Act        
        
        List<String> actual = Main.makeReplacements(data, replacement);
        
        // Assert
        
        List<String> expected = Stream.of(
                "Two roads diverged in a yellow wood,",
                "Robert Frost poetAnd sorry I could not travel both",
                "And be one traveler, long I stood",
                "And looked down one as far as I could",
                "To where it bent in the undergrowth;",
                "Then took the other, as just as fair,",
                "And having perhaps the better claim,",
                "Because it was grassy and wanted wear;",
                "Though as for that the passing there",
                "Had worn them really about the same,",
                "And both that morning equally lay",
                "In leaves no step had trodden black.",
                "Oh, I kept the first for another day!",
                "Yet knowing how way leads on to way,",
                "I doubted if I should ever come back.",
                "I shall be telling this with a sigh",
                "Somewhere ages and ages hence:",
                "Two roads diverged in a wood, and I",
                "I took the one less traveled by,",
                "And that has made all the difference.")
                .collect(Collectors.toList());
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Передать перестановкам пустые списки")
    public void makeReplacementsEmpty() throws IOException 
    {         
        // Act        
        List<String> actual = Main.makeReplacements(null, null);
        
        // Assert
        Assertions.assertNull(actual);
    }
    
    @Test
    @DisplayName("Перевернуть replacement")
    public void makeReplacementsReversed() throws IOException 
    {   
        // Arrange
        
        String initList[] = { "Two roads diverged in a ye11ow d123," };
        List data = new ArrayList(Arrays.asList(initList));
        
        List<Replacement> replacement = Stream.of(
                new Replacement("1", "l"),
                new Replacement("d123", "wood"))
                .collect(Collectors.toList());
        Collections.reverse(replacement); 
        
        // Act        
        
        List<String> actual = Main.makeReplacements(data, replacement);
        
        // Assert
        
        List<String> expected = Collections
                .singletonList("Two roads diverged in a yellow dl23,");
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Фиксированный replacement")
    public void makeReplacementsImmutableReplacementList() throws IOException 
    {   
        // Arrange

        List<String> data = Stream.of(
                "First", "NOt first!!")
                .collect(Collectors.toList());
        
        List<Replacement> replacement = Collections
                .singletonList(new Replacement("NOt first!!", "Second"));
        
        // Act        
        
        List<String> actual = Main.makeReplacements(data, replacement);
        
        // Assert
        
        List<String> expected = Stream.of(
                "First", "Second")
                .collect(Collectors.toList());
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Фиксированный data")
    public void makeReplacementsImmutableDataList() throws IOException 
    {   
        // Arrange

        List<String> data = Collections
                .singletonList("NOt first!!");
        
        List<Replacement> replacement = Stream.of(
                new Replacement("NOt first!!", "Second"),
                new Replacement("!!!", null),
                new Replacement("No more message for you", "ok"))
                .collect(Collectors.toList());
        
        // Act        
        
        Throwable exception = assertThrows(
            UnsupportedOperationException.class, () -> {
                Main.makeReplacements(data, replacement);
            }
        );
        
        // Assert
        
        Assertions.assertEquals(
                "Data cannot be modified", 
                exception.getMessage());
    }
    
    @Test
    @DisplayName("Удаление и множественная замена")
    public void makeReplacementsToNull() throws IOException 
    {   
        // Arrange
        
        List<String> data = Stream.of(
                "cool!!!",
                "NOt first!!",
                "repeat it qww?! qww?! qww?! times")
                .collect(Collectors.toList());
        
        List<Replacement> replacement = Stream.of(
                new Replacement("NOt first!!", "Second"),
                new Replacement("!!!", null),
                new Replacement("qww?!", "many"))
                .collect(Collectors.toList());
        
        // Act        
        
        List<String> actual = Main.makeReplacements(data, replacement);
        
        // Assert
        
        List<String> expected = Stream.of(
                "Second",
                "repeat it many many many times")
                .collect(Collectors.toList());
        Assertions.assertEquals(expected, actual);
    }
}
