import java.util.Random;
import java.util.Scanner;

public class GuessTheGame {

    //region Fields
    public static String[] gameStatusEnum = new String[]{"YOU WIN", "YOU LOST"};
    public static Random rnd;
    public static Scanner sc;
    public static long currentTime = 0;
    public static long timePlayed = 0;
    public static long bestTime = Long.MAX_VALUE;
    public static int currentNumber = 0;
    public static int played = 0;
    public static int currentGuesses = 0;
    public static int bestGuesses = Integer.MAX_VALUE;
    public static int gaveUp = 0;
    public static int min = 1;
    public static int max = 100;

    //endregion
    //region Main Initialization
    public static void main(String[] args) {
        //initialize scanner
        sc = new Scanner(System.in);
        //initialize random generator
        rnd = new Random();
        logo();
        //Separator
        System.out.printf("%n%n");
        instructions();
        currentNumber = rnd.nextInt(min, max + 1);
        run();
        sc.close();
    }

    public static void instructions() {
        //Instructions how to play
        System.out.println("HOW TO PLAY:");
        System.out.println("To exit game please write 'exit'");
        System.out.println("To end current game and start next 'giveup'");
        System.out.println("For statistics please write 'stat'");
        System.out.println("For settings please write 'settings'");
        System.out.println("For instructions please write 'help' or 'info'");
        System.out.printf("To start please write a number between %d and %d%n", min, max);
    }

    public static void logo() {
        //Game logo for the start
        System.out.println("Welcome to:");
        System.out.println("   _____                          _______  _             _   _  _    _  __  __  ____   ______  _____  ");
        System.out.println("  / ____|                        |__   __|| |           | \\ | || |  | ||  \\/  ||  _ \\ |  ____||  __ \\ ");
        System.out.println(" | |  __  _   _   ___  ___  ___     | |   | |__    ___  |  \\| || |  | || \\  / || |_) || |__   | |__) |");
        System.out.println(" | | |_ || | | | / _ \\/ __|/ __|    | |   | '_ \\  / _ \\ | . ` || |  | || |\\/| ||  _ < |  __|  |  _  / ");
        System.out.println(" | |__| || |_| ||  __/\\__ \\\\__ \\    | |   | | | ||  __/ | |\\  || |__| || |  | || |_) || |____ | | \\ \\ ");
        System.out.println("  \\_____| \\__,_| \\___||___/|___/    |_|   |_| |_| \\___| |_| \\_| \\____/ |_|  |_||____/ |______||_|  \\_\\");
        System.out.println("Game by Genadiy Dimitrov");
    }

    //region Cycle
    public static void run() {
        //get initial command
        String command = sc.nextLine();
        //loop until command = END
        while (!command.equalsIgnoreCase("exit")) {
            //switch for functions and indexes
            switch (command.toLowerCase()) {
                case "stat":
                    System.out.println("GAME STATISTICS:");
                    statistics();
                    break;
                case "info":
                case "help":
                    instructions();
                    break;
                case "giveup":
                    gaveUp++;
                    System.out.println("GAVE UP!");
                    System.out.printf("The computer chose number %d%n", currentNumber);
                    newGame();
                    break;
                case "settings":
                    settings();
                    System.out.printf("Continue by typing number between %d and %d%n", min, max);
                    System.out.println("Or end by: 'exit'");
                    break;
                default:
                    try {
                        int userGuess = Integer.parseInt(command);
                        play(userGuess);
                    } catch (Exception e) {
                        System.out.printf("Please select valid input: number between %d and %d%n", min, max);
                    }
                    break;
            }
            //wait for next command
            command = sc.nextLine();
        }
        statistics();
    }

    private static void play(int playerGuess) {
        currentGuesses++;
        if (currentTime == 0) {
            played++;
            currentTime = System.currentTimeMillis() / 1000;
        }
        if (playerGuess > currentNumber) {
            System.out.printf("You chose %d and its BIGGER than the NUMBER%n", playerGuess);
        } else if (playerGuess < currentNumber) {
            System.out.printf("You chose %d and its LOWER than the NUMBER%n", playerGuess);
        } else {
            currentTime = System.currentTimeMillis() / 1000 - currentTime;
            timePlayed += currentTime;
            System.out.printf("CONGRATULATIONS!!! You guessed the number %d%n", currentNumber);
            if (bestTime > currentTime) {
                bestTime = currentTime;
                System.out.printf("NEW BEST TIME %d%n", bestTime);
            } else {
                System.out.printf("TIME: %d [Best:%d]%n", currentTime, bestTime);
            }
            if (bestGuesses > currentGuesses) {
                bestGuesses = currentGuesses;
                System.out.printf("NEW BEST GUESSES %d%n", bestGuesses);
            } else {
                System.out.printf("GUESSES: %d [Best:%d]%n", currentGuesses, bestGuesses);
            }
            newGame();
        }
    }

    //endregion
    //region Methods
    public static void newGame() {
        System.out.println("GAME STATISTICS:");
        statistics();
        System.out.println("GUESS THE NEXT NUMBER:");
        currentNumber = rnd.nextInt(min, max + 1);
        reset();
    }

    public static void reset() {
        currentTime = 0;
        currentGuesses = 0;
    }

    public static void statistics() {
        System.out.printf("Game played: %d%n", played);
        long sec = timePlayed % 60;
        long allMin = timePlayed / 60;
        long min = allMin % 60;
        long hours = allMin / 60;
        System.out.printf("Time played: %d:%d:%d%n", hours, min, sec);
        if (bestTime != Long.MAX_VALUE) {
            System.out.printf("Best time: %d%n", bestTime);
        }
        if (bestGuesses != Integer.MAX_VALUE) {
            System.out.printf("Best guesses: %d%n", bestGuesses);
        }
        System.out.printf("Gave up: %d%n", gaveUp);
    }

    public static void settings() {
        settingsInfo();
        String command = sc.nextLine();
        //loop until command = END
        while (!command.equalsIgnoreCase("exit")) {
            //reset command
            if (command.equalsIgnoreCase("reset")) {
                System.out.println("Are you sure you want to RESET game progress? 'y'-YES / 'n'-NO");
                //confirm reset
                String confirm = sc.nextLine();
                if (confirm.equalsIgnoreCase("y")) {
                    played = 0;
                    gaveUp = 0;
                    timePlayed = 0;
                    bestTime = 0;
                    System.out.println("RESET - Done!");
                }
            } else if (command.toLowerCase().startsWith("range ")) {
                //info command
                String[] arr = command.split(" ");
                try {
                    int s = Integer.parseInt(arr[1]);
                    int e = Integer.parseInt(arr[2]);
                    if (s < 1) s = 1;
                    if (e < s) {
                        System.out.println("Start index is bigger than end index - Swapping...");
                        min = e;
                        max = s;
                    } else if (e == s) {
                        System.out.println("Start index is equal to end index - Resetting...");
                        min = 1;
                        max = 100;
                    } else {
                        min = s;
                        max = e;
                    }
                    System.out.printf("Range - SET to %d <-> %d%n", min, max);
                } catch (Exception e) {
                    System.out.println("Please select valid input: numbers");
                }
            } else {
                System.out.println("Please select valid input.");
                settingsInfo();
            }

            command = sc.nextLine();
        }
    }

    public static void settingsInfo() {
        System.out.println("GAME SETTINGS:");
        System.out.println("To RESET progress type - 'reset'");
        System.out.println("To change range for next game onwards:");
        System.out.println("write 'range X Y' where X is min value and Y is max value");
        System.out.println("To exit settings type - 'exit'");
    }

    //endregion
}