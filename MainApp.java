import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class MainApp {

    public static void main(String[] args) throws InterruptedException {
        mainMenu();
    }

    public static void mainMenu() throws InterruptedException {
        Set<Integer> validMenuOptions = Set.of(1, 2, 3, 4, 5);
        int choice = -1;
        Scanner user_input = new Scanner(System.in);
        boolean invalidChoice = false;

        do {
            clearScreen();
            System.out.println("Welcome to the hospital management system!");
            System.out.println("What would you like to do?");
            System.out.println("1. Manage Patients");
            System.out.println("2. Manage Appointments");
            System.out.println("3. Manage Doctors");
            System.out.println("4. Manage Patient Queue");
            System.out.println("5. Exit");

            // Display the invalid choice message after the menu items
            if (!invalidChoice) {
                System.out.println();
            } else {
                System.out.println("Invalid option! Enter a valid option!");
            }

            try {
                choice = user_input.nextInt();
                invalidChoice = !validMenuOptions.contains(choice);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                user_input.next(); // Clear the invalid input
                invalidChoice = true;
            }

        } while (invalidChoice);

        switch (choice) {
            case 1:
                System.out.println("Starting patient management...");
                //Insert method to start the patient menu
                break;
            case 2:
                System.out.println("Starting appointment management...");
                //Insert method to start appointment menu
                Thread.sleep(1000);
                appointmentMenu.mainMenu();
                break;
            case 3:
                System.out.println("Starting doctor management...");
                //Insert method to start doctor menu
                break;
            case 4:
                System.out.println("Starting patient queue management...");
                //Insert method to start patient queue method
                break;
            case 5:
                System.out.println("Exiting...");
                System.out.println("Goodbye!");
                break;
        }

    }

    public static void loadData() {

    }

    public static void clearScreen() {
        //Clears the screen, be sure to spam this when moving between menus lmao
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
