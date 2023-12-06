import Trolls.NumberTroll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class NumberTrollTest {
    @Test
    void testGenerateNumberInRange() {
        NumberTroll numberTroll = new NumberTroll();
        int generatedNumber = numberTroll.getNumberToGuess();

        assertTrue(generatedNumber >= 1 && generatedNumber <= 5,
                "Generated number should be between 1 and 5 (inclusive)");
    }

    @Test
    void testGiveInstructions() {
        NumberTroll numberTroll = new NumberTroll();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        numberTroll.giveInstructions();
        System.setOut(System.out);
        String expectedInstructions = "Welcome to the NUMBER GUESSING TROLL! You must beat me to pass.\n" +
                "I have selected a number between 1 and 5 both inclusive.\n" +
                "Your task is to guess the number. Take your guess!\n";
        assertEquals(expectedInstructions, outputStream.toString().replace("\r\n", "\n"));
    }
}