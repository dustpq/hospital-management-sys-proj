import java.time.*;
import java.time.format.*;
import java.util.*;

/*
* @author:
* Dustin Quintanilla
* This class provides a menu-driven interface for managing appointments.
*/

public class appointmentMenu {

    // Priority queue to store appointments, sorted by date and time in descending order.
    static PriorityQueue<Appointment> appointmentQueue = new PriorityQueue<>(
            Comparator.comparing((Appointment a) -> LocalDate.parse(a.getDetail("date"), DateTimeFormatter.ofPattern("MM/dd/yyyy")))
                    .thenComparing(a -> LocalTime.parse(a.getDetail("time"), DateTimeFormatter.ofPattern("hh:mm a")))
    );

    // List to store appointments for easier iteration and pagination.
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
        Set<Integer> validMenuOptions = Set.of(1, 2, 3, 4);

        while (true) {
            clearScreen();
            String menuPrompt = """
            Welcome to the Appointment Management Menu!
            What would you like to do?
            1.) Create new appointment
            2.) Manage existing appointments
            3.) Search for appointment
            4.) Exit to main menu
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
                    System.out.println("Preparing to search for appointment...");
                    pause(1000);
                    searchAppointments();
                    break; // Added missing break
                case 4:
                    System.out.println("Exiting to main menu...");
                    pause(1000);
                    return;
            }
        }
    }

    /**
     * Handles the creation of a new appointment.
     * Prompts the user for appointment details and adds it to the queue.
     */
    public static void newAppointmentMenu() {
        clearScreen();
        System.out.println("New Appointment Creation");

        user_input.nextLine();
        System.out.print("Enter patient name: ");
        String name = user_input.nextLine();

        System.out.print("Enter appointment date (MM/dd/yyyy) case-sensitive: ");
        String date = user_input.nextLine();
        while (!validateFormat(date, "date")) {
            System.out.println("Invalid format! Try again.");
            System.out.println("Previous input: " + date);
            System.out.print("Enter appointment date (MM/dd/yyyy) case-sensitive: ");
            date = user_input.nextLine().trim();
        }

        System.out.print("Enter appointment time (hh:mm a): ");
        String time = user_input.nextLine();
        time = time.toUpperCase();
        while (!validateFormat(time, "time")) {
            System.out.println("Invalid format! Try again.");
            System.out.println("Previous input: " + time);
            System.out.print("Enter appointment time (hh:mm a): ");
            time = user_input.nextLine();
            time = time.toUpperCase();
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

        clearScreen();
        System.out.println("Managing existing appointments...");

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
     * @return The index of the selected appointment, or -1 if no selection is made.
     */
    public static int printCurrentAppointments() {
        syncAppointments(); // Ensure the list is up-to-date with the queue

        if (appointmentList.isEmpty()) {
            System.out.println("No appointments available.");
            pause(1000);
            return -1; // Indicate no appointments
        }

        int pageSize = 10;
        int totalPages = (int) Math.ceil((double) appointmentList.size() / pageSize);
        int currentPage = 1;

        while (true) {
            clearScreen();
            String menuContent = """
            Current Appointments (Page %d of %d):
            """.formatted(currentPage, totalPages);

            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, appointmentList.size());

            StringBuilder appointmentDetails = new StringBuilder(menuContent);
            for (int i = start; i < end; i++) {
                appointmentDetails.append((i + 1)).append(".) ");
                appointmentDetails.append(appointmentList.get(i).getDetails()).append("\n");
            }

            appointmentDetails.append("""
            \nOptions:
            1.) Next Page
            2.) Previous Page
            3.) Exit
            4.) Enter number to select
            """);

            Set<Integer> validOptions = Set.of(1, 2, 3, 4);
            int choice = getMenuChoice(Set.of(1, 2, 3, 4), user_input, appointmentDetails.toString());

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
     * @param a The appointment to be edited.
     */
    public static void editAppointment(Appointment a) {
        clearScreen();
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
        time = time.toUpperCase();
        while (!validateFormat(time, "time")) {
            System.out.println("Invalid format! Try again.");
            System.out.print("Enter appointment time (hh:mm a) case-sensitive: ");
            time = user_input.nextLine();
            time = time.toUpperCase();
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

    /**
     * Deletes an appointment after user confirmation.
     * @param a The appointment to be deleted.
     */
    public static void deleteAppointment(Appointment a) {
        clearScreen();
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
     * Allows the user to search for appointments by patient name, doctor name, or date.
     * Displays the search results and allows further actions on the results.
     */
    public static void searchAppointments() {
        clearScreen();

        String menuPrompt = """
        Search Appointments
        Search by:
        1.) Patient Name
        2.) Doctor Name
        3.) Appointment Date (MM/dd/yyyy)
        4.) Go Back
        """;

        Set<Integer> validOptions = Set.of(1, 2, 3, 4);
        int choice = getMenuChoice(validOptions, user_input, menuPrompt);

        if (choice == 4) {
            System.out.println("Returning to previous menu...");
            pause(1000);
            return;
        }

        List<Appointment> results;
        switch (choice) {
            case 1 -> {
                System.out.print("Enter patient name: ");
                user_input.nextLine();
                String searchTerm = user_input.nextLine();
                results = appointmentList.stream()
                        .filter(a -> {
                            String name = a.getDetail("name");
                            return name != null && name.equalsIgnoreCase(searchTerm);
                        })
                        .toList();
            }
            case 2 -> {
                System.out.print("Enter doctor name: ");
                user_input.nextLine();
                String searchTerm = user_input.nextLine().trim();

                results = appointmentList.stream()
                        .filter(a -> {
                            String doctor = a.getDetail("doctor");
                            return doctor != null && doctor.equalsIgnoreCase(searchTerm);
                        })
                        .toList();

                if (results.isEmpty()) {
                    System.out.println("No appointments found for the doctor name: " + searchTerm);
                }
            }
            case 3 -> {
                String date;
                while (true) {
                    System.out.print("Enter appointment date (MM/dd/yyyy): ");
                    user_input.nextLine();
                    date = user_input.nextLine().trim();
                    try {
                        LocalDate.parse(date, dateFormatter); // Validate the date format
                        break; // Exit the loop if the format is valid
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format! Please try again.");
                    }
                }

                String finalDate = date;
                results = appointmentList.stream()
                        .filter(a -> {
                            String appointmentDate = a.getDetail("date");
                            return appointmentDate != null && appointmentDate.equals(finalDate);
                        })
                        .toList();

                if (results.isEmpty()) {
                    System.out.println("No appointments found for the date: " + finalDate);
                }
            }
            default -> {
                System.out.println("Invalid choice. Returning to main menu...");
                pause(1000);
                return;
            }
        }

        if (results.isEmpty()) {
            System.out.println("No appointments found matching the search criteria.");
            pause(2000);
            return;
        }

        StringBuilder output = new StringBuilder("Search Results:\n");
        for (int i = 0; i < results.size(); i++) {
            output.append((i + 1)).append(".) ").append(results.get(i).getDetails()).append("\n");
        }

        output.append("""
                \nOptions:
                1.) Edit Appointment
                2.) Delete Appointment
                3.) Go Back
                """);

        int action = getMenuChoice(Set.of(1, 2, 3), user_input, output.toString());

        if (action == 3) {
            System.out.println("Returning to previous menu...");
            pause(1000);
            return;
        }

        System.out.print("Enter the number of the appointment to select: ");
        int selectedIndex = user_input.nextInt() - 1;
        user_input.nextLine(); // Clear the newline

        if (selectedIndex < 0 || selectedIndex >= results.size()) {
            System.out.println("Invalid selection! Returning to main menu...");
            pause(2000);
            return;
        }

        Appointment selectedAppointment = results.get(selectedIndex);

        if (action == 1) {
            editAppointment(selectedAppointment);
        } else if (action == 2) {
            deleteAppointment(selectedAppointment);
        }
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
     * References the method in MainApp to avoid duplication.
     * @param validOptions A set of valid menu options.
     * @param scanner The scanner for user input.
     * @param menuPrompt The menu prompt to display.
     * @return The user's valid choice.
     */
    private static int getMenuChoice(Set<Integer> validOptions, Scanner scanner, String menuPrompt) {
        return MainApp.getMenuChoice(validOptions, scanner, menuPrompt);
    }

    /**
     * Syncs the queue and list whenever called.
     * Should be called whenever one of the two is updated to ensure consistency.
     */
    public static void syncAppointments() {
        appointmentList = new ArrayList<>(appointmentQueue);

        appointmentQueue = new PriorityQueue<>(
                Comparator.comparing((Appointment a) -> LocalDate.parse(a.getDetail("date"), DateTimeFormatter.ofPattern("MM/dd/yyyy")))
                        .thenComparing(a -> LocalTime.parse(a.getDetail("time"), DateTimeFormatter.ofPattern("hh:mm a")))
        );
        appointmentQueue.addAll(appointmentList);
    }

    /**
     * Pauses the program for a specified amount of time.
     * References the method in MainApp to avoid duplication.
     * @param time The time to pause in milliseconds.
     */
    public static void pause(int time) {
        MainApp.pause(time);
    }

    /**
     * Clears the console screen.
     * References the method in MainApp to avoid duplication.
     */
    public static void clearScreen() {
        MainApp.clearScreen();
    }

}
