package Trolls;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Class CupTroll.
 */
public class CupTroll implements Troll, TrollsByCups {

    private String[] cups = {"A", "B", "C"};
    private String ballCup;
    private Scanner scanner;

    /**
     * CupTroll constructor.
     * Initializes the cups and places the ball under one of them.
     */
    public CupTroll() {
        this.scanner = new Scanner(System.in);
        placeMagicBall();
    }

    /**
     * Place the ball under a random cup.
     */
    @Override
    public void placeMagicBall() {
        List<String> cupsList = Arrays.asList(cups);
        Collections.shuffle(cupsList);
        ballCup = cupsList.get(0);
    }

    /**
     * Shuffle the cups around.
     */
    @Override
    public void shuffleCups() throws InterruptedException {
        System.out.println("Shuffling cups...");

        for (int i = 0; i < 5; i++) {
            Collections.shuffle(Arrays.asList(cups));
            printHorizontalCups();
            TimeUnit.SECONDS.sleep(2); // Pause for 2 seconds
        }
    }

    /**
     * Print the cups horizontally.
     */
    private void printHorizontalCups() {
        for (String cup : cups) {
            System.out.print("[" + cup + "] ");
        }
        System.out.println(); // Move to the next line after printing cups
    }

    /**
     * Print CupTroll instructions for the user.
     */
    @Override
    public void giveInstructions() {
        System.out.println("Welcome to the CUP TROLL! You must beat me to pass.");
        System.out.println("I have placed the ball under one of the cups (A, B, or C).");
        System.out.println("I will now shuffle the cups. Try to guess which cup the ball is under!");
        System.out.println("You need to enter your guess within 15 seconds.");
        System.out.println("The cups are labeled A, B, and C.");
        System.out.println("Good luck!");
    }

    /**
     * Play the CupBallTroll game with a time limit.
     *
     * @return true if the player wins the game within the time limit, else false
     */
    public boolean playGame() {
        giveInstructions();
        try {
            shuffleCups();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Enter your guess (A, B, or C) within 15 seconds: ");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> userInputFuture = executorService.submit(() -> this.scanner.next().toUpperCase());

        try {
            String playerGuess = userInputFuture.get(15, TimeUnit.SECONDS); // Wait for 15 seconds max

            if (playerGuess.equals(this.ballCup)) {
                System.out.println("Congratulations! You guessed correctly. The ball was under cup " + ballCup + "!");
                return true;
            } else {
                System.out.println("Oops! The ball was under cup " + ballCup + ". Better luck next time!");
                return false;
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            userInputFuture.cancel(true); // Interrupt the user input thread
            System.out.println("Time's up! You took too long. Better luck next time!");
            return false;
        } finally {
            executorService.shutdown();
        }
    }

    public String[] getCups() {
        return cups;
    }

    public void setCups(String[] cups) {
        this.cups = cups;
    }

    public String getBallCup() {
        return ballCup;
    }

    public void setBallCup(String ballCup) {
        this.ballCup = ballCup;
    }

}
