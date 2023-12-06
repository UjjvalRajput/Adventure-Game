package Trolls;

import java.util.*;
import java.util.concurrent.*;

/**
 * Class CupTrollHard.
 */
public class CupTrollHard implements Troll, TrollsByCups {

    private String[] cups = {"A", "B", "C", "D", "E", "F"};
    private int ballIndex; // Index of the cup containing the ball
    private Map<String, String> deceptiveObjects; // Cup and name of deceptive objects
    private Scanner scanner;

    /**
     * CupTrollHard constructor.
     * Initializes the cups, places the ball under one of them, and introduces deceptive objects.
     */
    public CupTrollHard() {
        this.scanner = new Scanner(System.in);
        placeMagicBall();
        introduceDeceptiveObjects();
    }

    /**
     * Place the ball under a random cup.
     */
    @Override
    public void placeMagicBall() {
        List<String> cupsList = Arrays.asList(cups);
        Collections.shuffle(cupsList);
        ballIndex = cupsList.indexOf(cupsList.get(0));
    }

    /**
     * Introduce deceptive objects alongside cups.
     */
    public void introduceDeceptiveObjects() {
        deceptiveObjects = new HashMap<>();
        List<String> deceptiveObjectNames = Arrays.asList("Rabbit", "Hat", "Coin", "Dice", "Flower");

        for (String cup : cups) {
            if (!cup.equals(cups[ballIndex])) {
                Collections.shuffle(deceptiveObjectNames);
                deceptiveObjects.put(cup, deceptiveObjectNames.get(0));
            }
        }
    }

    /**
     * Make additional movements (swap positions) in the list.
     *
     * @param cupsAndObjectsList The list to perform additional movements on
     */
    private void makeAdditionalMovements(List<String> cupsAndObjectsList) {
        Collections.swap(cupsAndObjectsList, 0, 1);
        Collections.swap(cupsAndObjectsList, 2, 3);
        Collections.swap(cupsAndObjectsList, 4, 5);
    }

    /**
     * Shuffle the cups and deceptive objects and print them every 2-3 seconds.
     */
    @Override
    public void shuffleCups() throws InterruptedException {
        System.out.println("I have placed the ball under one of the cups (A, B, C, D, E, F).");
        System.out.println("Alongside the cups, there are deceptive objects: " + deceptiveObjects);
        System.out.println("Now, I will perform a random shuffle, but this one does not count.");

        List<String> cupsAndObjectsList = new ArrayList<>(Arrays.asList(cups));
        for (String cup : cups) {
            if (deceptiveObjects.containsKey(cup)) {
                cupsAndObjectsList.add(deceptiveObjects.get(cup));
            }
        }

        for (int i = 0; i < 5; i++) {
            Collections.shuffle(cupsAndObjectsList);
            printHorizontalCupsAndObjects(cupsAndObjectsList);
            TimeUnit.SECONDS.sleep(2); // Pause for 2 seconds
        }

        System.out.println("Now, I will make additional movements.");
        makeAdditionalMovements(cupsAndObjectsList);

        printHorizontalCupsAndObjects(cupsAndObjectsList);
    }

    /**
     * Print the cups and deceptive objects horizontally.
     */
    private void printHorizontalCupsAndObjects(List<String> cupsAndObjectsList) {
        for (String cupOrObject : cupsAndObjectsList) {
            System.out.print("[" + cupOrObject + "] ");
        }
        System.out.println(); // Move to the next line after printing cups and objects
    }

    /**
     * Print CupTrollHard instructions for the user.
     */
    @Override
    public void giveInstructions() {
        System.out.println("Welcome to the HARD CUP TROLL! You must beat me to pass.");
        System.out.println("The cups are labeled A, B, C, D, E, F. The ball is under one of them.");
        System.out.println("Alongside the cups, there are deceptive objects");
        System.out.println("I will perform a random shuffle, but this one does not count. So you will have to ignore and follow its predecessor.");
        System.out.println("Now, I will make additional movements. Try to guess the final position of the cup with the ball!");
        System.out.println("You need to enter your guess within 30 seconds.");
        System.out.println("Please type the letter of your guess (A, B, C, D, E, or F).");
        System.out.println("Good luck!");
    }

    /**
     * Play the CupBallTrollHard game with a time limit.
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

        System.out.println("Enter your guess (A, B, C, D, E, or F) within 30 seconds: ");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> userInputFuture = executorService.submit(() -> this.scanner.next().toUpperCase());

        try {
            String playerGuess = userInputFuture.get(30, TimeUnit.SECONDS); // Wait for 30 seconds max

            if (playerGuess.equals(cups[ballIndex])) {
                System.out.println("Congratulations! You guessed correctly. The ball was under cup " + playerGuess + "!");
                return true;
            } else {
                System.out.println("Oops! The ball was under cup " + cups[ballIndex] + ". Better luck next time!");
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

    public int getBallIndex() {
        return ballIndex;
    }

    public Map<String, String> getDeceptiveObjects() {
        return deceptiveObjects;
    }
}