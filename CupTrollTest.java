import Trolls.CupTroll;
import Trolls.CupTrollHard;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CupTrollTest {

    @Test
    void giveInstructionsTest() {
        CupTroll cupTroll = new CupTroll();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        cupTroll.giveInstructions();
        System.setOut(System.out);
        String expectedInstructions = "Welcome to the CUP TROLL! You must beat me to pass.\n" +
                "I have placed the ball under one of the cups (A, B, or C).\n" +
                "I will now shuffle the cups. Try to guess which cup the ball is under!\n" +
                "You need to enter your guess within 5 seconds.\n" +
                "The cups are labeled A, B, and C.\n" +
                "Good luck!\n";
        assertEquals(expectedInstructions, outputStream.toString().replace("\r\n", "\n"));
    }

    @Test
    void getCupsReturnsCorrectCups() {
        CupTroll cupTroll = new CupTroll();
        String[] expectedCups = {"A", "B", "C"};
        String[] actualCups = cupTroll.getCups();
        Arrays.sort(expectedCups);
        Arrays.sort(actualCups);
        assertArrayEquals(expectedCups, actualCups);
    }

    @Test
    void getBallCupReturnsCorrectBallCup() {
        CupTroll cupTroll = new CupTroll();
        cupTroll.setBallCup("A");
        String actualBallCup = cupTroll.getBallCup();
        assertEquals("A", actualBallCup);
    }

    //Cup Troll Hard Tests are from hereon-wards:
    @Test
    void giveInstructionsTestHardTroll() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        CupTrollHard cupTroll = new CupTrollHard();
        cupTroll.giveInstructions();
        System.setOut(System.out);
        String expectedInstructions = "Welcome to the HARD CUP TROLL! You must beat me to pass.\n" +
                "The cups are labeled A, B, C, D, E, F. The ball is under one of them.\n" +
                "Alongside the cups, there are deceptive objects\n" +
                "I will perform a random shuffle, but this one does not count. So you will have to ignore and follow its predecessor.\n" +
                "Now, I will make additional movements. Try to guess the final position of the cup with the ball!\n" +
                "You need to enter your guess within 10 seconds.\n" +
                "Please type the letter of your guess (A, B, C, D, E, or F).\n" +
                "Good luck!\n";
        assertEquals(expectedInstructions, outputStream.toString().replace("\r\n", "\n"));
    }

    @Test
    void testPlaceMagicBallBallIsUnderOneOfTheCups() {
        CupTrollHard cupTrollHard = new CupTrollHard();
        cupTrollHard.placeMagicBall();
        int ballIndex = cupTrollHard.getBallIndex();
        assertTrue(ballIndex >= 0 && ballIndex < cupTrollHard.getCups().length);
    }

    @Test
    void testIntroduceDeceptiveObjectsDeceptiveObjectsAreIntroduced() {
        CupTrollHard cupTrollHard = new CupTrollHard();
        cupTrollHard.introduceDeceptiveObjects();
        assertFalse(cupTrollHard.getDeceptiveObjects().isEmpty());
    }
}