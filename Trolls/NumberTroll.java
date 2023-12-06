package Trolls;

import java.util.Random;
import java.util.Scanner;

/**
 * Class NumberTroll.
 *  */
public class NumberTroll implements Troll {

    private int numberToGuess; //number to guess by the player

    private Scanner scanner; //use to read input from user

    /**
     * NumberTroll constructor.
     * Initializes the number to be guessed.
     */
    public NumberTroll() {
        this.scanner = new Scanner(System.in);
        Random random = new Random();
        numberToGuess = random.nextInt(5) + 1;
    }

    /**
     * Print GameTroll instructions for the user
     */
    public void giveInstructions()
    {
        System.out.println("Welcome to the NUMBER GUESSING TROLL! You must beat me to pass.");
        System.out.println("I have selected a number between 1 and 5 both inclusive.");
        System.out.println("Your task is to guess the number. Take your guess!");
    }

    /**
     * Play the GameTroll game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame()
    {
        giveInstructions();
        System.out.print("Enter your guess between 1 and 5 both inclusive: \n");
        int playerGuess = this.scanner.nextInt();
        if (playerGuess == this.numberToGuess) {
            System.out.println("Woah! Awesome! You guessed the number right!\n");
            return true;
        } else {
            System.out.println("Alas, I had selected " + this.numberToGuess + ". Hopefully, you will win next time!\n");
            return false;
        }
    }

    public int getNumberToGuess() {
        return numberToGuess;
    }

    public Scanner getScanner() {
        return scanner;
    }
}

