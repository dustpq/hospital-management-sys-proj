import java.time.*;
import java.time.format.*;
import java.util.*;

/*
* @author: Dustin Quintanilla
*/

public class appointmentMenu {

    // Priority queue to store appointments, sorted by date and time in descending order.
    static PriorityQueue<Appointment> appointmentQueue = new PriorityQueue<>(
            (a, b) ->
                      b.getDetail("date").compareTo(a.getDetail("date"))
                    + b.getDetail("time").compareTo(a.getDetail("time"))
    );

    static List<Appointment> appointmentList = new ArrayList<>(appointmentQueue);

    static Scanner user_input = new Scanner(System.in);

    // Formatters for date and time input validation.
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

    /**
     * Displays the main menu for appointment management.
     * Allows the user to create new appointments, manage existing ones, or exit.
     */
    public static void mainMenu() {

        Set<Integer> validMenuOptions = Set.of(1, 2, 3);

        MainApp.clearScreen();
        String menuPrompt = """
        Welcome to the Appointment Management Menu!
        What would you like to do?
        1.) Create new appointment
        2.) Manage existing appointments
        3.) Exit to main menu
        """;

        int choice = getMenuChoice(validMenuOptions, user_input, menuPrompt);

        switch (choice) {
            case 1:
                System.out.println("Creating new appointment...");
                pause(1000);
                newAppointmentMenu();
                break;
            case 2:
                System.out.println("Managing existing appointments...");
                pause(1000);
                manageExistingMenu();
                break;
            case 3:
                System.out.println("Exiting to main menu...");
                pause(1000);
                MainApp.mainMenu();
                break;
        }

    }

    /**
     * Handles the creation of a new appointment.
     * Prompts the user for appointment details and adds it to the queue.
     */
    public static void newAppointmentMenu() {
        MainApp.clearScreen();
        System.out.println("New Appointment Creation");

        user_input.nextLine();
        System.out.print("Enter patient name: ");
        String name = user_input.nextLine();

        System.out.print("Enter appointment date (MM/dd/yyyy) case-sensitive: ");
        String date = user_input.nextLine();
        while (!validateFormat(date, "date")) {
            System.out.println("Invalid format! Try again.");
            System.out.print("Enter appointment date (MM/dd/yyyy) case-sensitive: ");
            date = user_input.nextLine();
        }

        System.out.print("Enter appointment time (hh:mm a): "); // Fixed typo in prompt
        String time = user_input.nextLine();
        time = time.toUpperCase();
        while (!validateFormat(time, "time")) {
            System.out.println("Invalid format! Try again.");
            System.out.print("Enter appointment time (hh:mm a): ");
            time = user_input.nextLine();
        }

        System.out.print("Enter doctor name: ");
        String doctor_name = user_input.nextLine();

        Appointment newAppointment = new Appointment(name, date, time, doctor_name);
        appointmentQueue.add(newAppointment);
        syncAppointments(); // Sync after adding a new appointment
        System.out.println("New Appointment created!");

        System.out.println("Returning to appointment menu...");
        pause(1000);
        mainMenu();

    }

    /**
     * Displays the menu for managing existing appointments.
     * Allows the user to edit, remove, or view appointments.
     */
    public static void manageExistingMenu() {

        Set<Integer> validMenuOptions = Set.of(1, 2, 3);

        MainApp.clearScreen();
        System.out.println("Managing existing appointments...");
        System.out.println("Current Appointments: ");
        int order = 1;

        for (Appointment appointment : appointmentQueue) {
            System.out.print(order + ".) ");
            appointment.printDetails();
        }

        String menuPrompt =
        """
        What would you like to do?
        1.) Edit Appointment | 2.) Remove Appointment | 3.) Go back
        """;

        int choice = getMenuChoice(validMenuOptions, user_input, menuPrompt);

        switch (choice) {
            case 1:
                System.out.println("Preparing to edit appointment...");
                pause(1000);
                Appointment toEdit = selectAppointment();

                if (toEdit == null) {
                    System.out.println("No appointment selected. Returning to previous menu...");
                    pause(1000);
                    manageExistingMenu();
                }
                editAppointment(toEdit);
                break;
            case 2:
                System.out.println("Preparing to remove an appointment...");
                pause(1000);
                Appointment toDelete = selectAppointment();

                if (toDelete == null) {
                    System.out.println("No appointment selected. Returning to previous menu...");
                    pause(1000);
                    manageExistingMenu();
                }
                deleteAppointment(toDelete);
                break;
            case 3:
                System.out.println("Returning to previous menu...");
                pause(1000);
                mainMenu();
                break;
        }

    }

    /**
     * Prints the current appointments in a paginated format.
     * Allows navigation through pages or exiting the view.
     */
    public static int printCurrentAppointments() {
        if (appointmentList.isEmpty()) {
            System.out.println("No appointments available.");
            pause(1000);
            return -1; // Indicate no appointments
        }

        int pageSize = 10;
        int totalPages = (int) Math.ceil((double) appointmentList.size() / pageSize);
        int currentPage = 1;

        if (appointmentList.isEmpty()) {
            System.out.println("No appointments available.");
            return -1; // Indicate no appointments
        }

        while (true) {
            MainApp.clearScreen();
            System.out.println("Current Appointments (Page " + currentPage + " of " + totalPages + "):");

            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, appointmentList.size());

            for (int i = start; i < end; i++) {
                System.out.print((i + 1) + ".) ");
                appointmentList.get(i).printDetails();
            }

            System.out.println("\nOptions:");
            System.out.println("1.) Next Page");
            System.out.println("2.) Previous Page");
            System.out.println("3.) Exit");
            System.out.println("4.) Enter number to select");

            Set<Integer> validOptions = Set.of(1, 2, 3, 4);
            int choice = getMenuChoice(validOptions, user_input, "Enter your choice:");

            switch (choice) {
                case 1: // Next Page
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("You are already on the last page.");
                    }
                    break;
                case 2: // Previous Page
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("You are already on the first page.");
                    }
                    break;
                case 3: // Exit
                    return -1;
                case 4: // Select Appointment
                    System.out.print("Enter the number of the appointment to select: ");
                    try {
                        int selectedIndex = user_input.nextInt() - 1;
                        user_input.nextLine(); // Clear the newline
                        if (selectedIndex >= start && selectedIndex < end) {
                            return selectedIndex;
                        } else {
                            System.out.println("Invalid selection! Please try again.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a number.");
                        user_input.next(); // Clear invalid input
                    }
                    break;
            }
        }
    }

    /**
     * Allows the user to select an appointment from the list.
     * Returns the selected appointment or null if no selection is made.
     */
    public static Appointment selectAppointment() {
        int selectedIndex = printCurrentAppointments();
        if (selectedIndex == -1) {
            return null; // No selection made
        }
        return new ArrayList<>(appointmentQueue).get(selectedIndex);
    }

    /**
     * Edits the details of an existing appointment.
     * Prompts the user for new details and updates the appointment.
     */
    public static void editAppointment(Appointment a) {
        MainApp.clearScreen();
        System.out.println("Editing Existing Appointment");

        System.out.println("Current Appointment Details:");
        a.printDetails();

        System.out.print("Enter patient name: ");
        String name = user_input.nextLine();

        System.out.print("Enter appointment date (MM/dd/yyyy) case-sensitive: ");
        String date = user_input.nextLine();
        while (!validateFormat(date, "date")) {
            System.out.println("Invalid format! Try again.");
            System.out.print("Enter appointment date (MM/dd/yyyy) case-sensitive: ");
            date = user_input.nextLine();
        }

        System.out.print("Enter appointment time (hh:mm a) case-sensitive: ");
        String time = user_input.nextLine();
        while (!validateFormat(time, "time")) {
            System.out.println("Invalid format! Try again.");
            System.out.print("Enter appointment time (hh:mm a) case-sensitive: ");
            time = user_input.nextLine();
        }

        System.out.print("Enter doctor name: ");
        String doctor_name = user_input.nextLine();

        appointmentQueue.remove(a);
        Appointment updatedAppointment = new Appointment(name, date, time, doctor_name);
        appointmentQueue.add(updatedAppointment);
        syncAppointments(); // Sync after editing an appointment

        System.out.println("Appointment edited!");
        System.out.println("Returning to appointment menu...");
        pause(1000);
        mainMenu();
    }

    public static void deleteAppointment(Appointment a) {
        MainApp.clearScreen();
        System.out.println("Appointment to be deleted:");
        a.printDetails();

        Set<Integer> validMenuOptions = Set.of(1, 2);
        String prompt = """
            Are you sure you want to delete this appointment?
            1.) Yes | 2.) No
            """;

        int choice = getMenuChoice(validMenuOptions, user_input, prompt);

        if (choice == 1) {
            appointmentQueue.remove(a);
            syncAppointments(); // Sync after deleting an appointment
            System.out.println("Appointment deleted.");
        } else {
            System.out.println("Cancelled deletion.");
        }

        System.out.println("Returning to previous menu...");
        pause(1000);
        manageExistingMenu();
    }

    /**
     * Validates the format of a date or time string.
     * @param datetime The string to validate.
     * @param type The type of validation ("date" or "time").
     * @return True if the format is valid, false otherwise.
     */
    public static boolean validateFormat(String datetime, String type) {
        boolean result = false;
        if (Objects.equals(type, "date")) {
            try {
                LocalDate.parse(datetime, dateFormatter);
                result = true;
            } catch (DateTimeParseException _) {}

        }
        if (Objects.equals(type, "time")) {
            try {
                LocalTime.parse(datetime, timeFormatter);
                result = true;
            } catch (DateTimeParseException _) {}
        }
        return result;
    }

    /**
     * Displays a menu prompt and gets a valid choice from the user.
     * @param validOptions A set of valid menu options.
     * @param scanner The scanner for user input.
     * @param menuPrompt The menu prompt to display.
     * @return The user's valid choice.
     */
    private static int getMenuChoice(Set<Integer> validOptions, Scanner scanner, String menuPrompt) {
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
     * Syncs the queue and list whenever called
     * Should be called whenever one of the two are updated
     */

    public static void syncAppointments() {
        // Update the ArrayList from the PriorityQueue
        appointmentList = new ArrayList<>(appointmentQueue);

        // Rebuild the PriorityQueue from the ArrayList to ensure consistency
        appointmentQueue = new PriorityQueue<>(
                (a, b) -> b.getDetail("date").compareTo(a.getDetail("date"))
                        + b.getDetail("time").compareTo(a.getDetail("time"))
        );
        appointmentQueue.addAll(appointmentList);
    }

    /**
     * Pauses the program for a specified amount of time.
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

