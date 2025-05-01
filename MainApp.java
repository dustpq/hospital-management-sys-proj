import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class MainApp {

    public static void main(String[] args) {
        // If the program is run with the "data" argument, load test data into the system.
        if (args.length > 0 && Objects.equals(args[0], "data")) {
            loadData();
        }
        mainMenu();
    }

    /**
     * Displays the main menu for the hospital management system.
     * Allows the user to navigate to different management modules or exit the application.
     */
    public static void mainMenu() {
        Set<Integer> validMenuOptions = Set.of(1, 2, 3, 4, 5);

        while (true) {
            clearScreen();
            String menuPrompt = """
            Welcome to the hospital management system!
            What would you like to do?
            1.) Manage Patients
            2.) Manage Appointments
            3.) Manage Doctors
            4.) Manage Patient Queue
            5.) Exit
            """;

            int choice = getMenuChoice(validMenuOptions, new Scanner(System.in), menuPrompt);

            switch (choice) {
                case 1:
                    System.out.println("Starting patient management...");
                    pause(1000);
                    clearScreen();
                    patientRecordMenu.mainMenu();
                    break;
                case 2:
                    System.out.println("Starting appointment management...");
                    pause(1000);
                    appointmentMenu.mainMenu();
                    break;
                case 3:
                    System.out.println("Starting doctor management...");
                    pause(1000);
                    clearScreen();
                    doctorMenu.mainMenu();
                    break;
                case 4:
                    System.out.println("Starting patient queue management...");
                    pause(1000);
                    clearScreen();
                    emergencyPatientMenu.mainMenu();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.out.println("Goodbye!");
                    pause(1000);
                    System.exit(0);
                    break;
            }
        }
    }

    /**
     * Displays a menu prompt and gets a valid choice from the user.
     * Handles invalid input and ensures the choice is within the valid options.
     * @param validOptions A set of valid menu options.
     * @param scanner The scanner for user input.
     * @param menuPrompt The menu prompt to display.
     * @return The user's valid choice.
     */
    public static int getMenuChoice(Set<Integer> validOptions, Scanner scanner, String menuPrompt) {
        int choice = -1;
        boolean invalidChoice;
        do {
            System.out.println(menuPrompt);
            invalidChoice = false;
            try {
                choice = scanner.nextInt();
                if (!validOptions.contains(choice)) {
                    System.out.println("Invalid option! Enter a valid option.");
                    invalidChoice = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); // Clear invalid input
                invalidChoice = true;
            }
        } while (invalidChoice);
        return choice;
    }

    /**
     * Loads test data into the system.
     * This method is triggered when the program is run with the "data" argument.
     */
    public static void loadData() {
        // Generates test data for the system using a helper class.
        TestDataGenerator.generateTestData();
    }

    /**
     * Clears the console screen.
     * This method uses ANSI escape codes to clear the terminal output.
     * Note: May not work on all systems (e.g., Windows Command Prompt).
     */
    public static void clearScreen() {
        //Clears the screen, be sure to spam this when moving between menus lmao
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Pauses the program for a specified amount of time.
     * Useful for creating a delay between menu transitions.
     * @param time The time to pause in milliseconds.
     */
    public static void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("An unexpected interruption occurred.");
            Thread.currentThread().interrupt();
        }
    }

}
