import java.time.*;
import java.time.format.*;
import java.util.*;

public class appointmentMenu {

    static PriorityQueue<Appointment> appointmentQueue = new PriorityQueue<>(
            (a, b) ->
                      b.getDetail("date").compareTo(a.getDetail("date"))
                    + b.getDetail("time").compareTo(a.getDetail("time"))
    );

    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");


    public static void mainMenu() throws InterruptedException {

        Set<Integer> validMenuOptions = Set.of(1, 2, 3);
        int choice = -1;
        Scanner user_input = new Scanner(System.in);
        boolean invalidChoice = false;

        do {
            MainApp.clearScreen();
            System.out.println("Welcome to the Appointment Management Menu!");
            System.out.println("What would you like to do?");
            System.out.println("1. Create new appointment");
            System.out.println("2. Manage existing appointments");
            System.out.println("3. Exit to main menu");

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

    public static void newAppointmentMenu() throws InterruptedException {
        MainApp.clearScreen();
        System.out.println("New Appointment Creation");
        Scanner user_input = new Scanner(System.in);

        System.out.print("Enter patient name: ");
        String name = user_input.nextLine();

        System.out.print("Enter appointment date (MM/dd/yyyy) case-sensitive: ");
        String date = user_input.nextLine();
        while (!validateFormat(date, "date")) {
            System.out.println("Invalid format! Try again.");
            System.out.print("Enter appointment date (MM/dd/yyyy) case-sensitive: ");
            date = user_input.nextLine();
        }

        System.out.print("Enter appointment time (hh:mm _M) case-sensitive: ");
        String time = user_input.nextLine();
        while (!validateFormat(time, "time")) {
            System.out.println("Invalid format! Try again.");
            System.out.print("Enter appointment time (hh:mm a) case-sensitive: ");
            time = user_input.nextLine();
        }

        System.out.print("Enter doctor name: ");
        String doctor_name = user_input.nextLine();

        Appointment newAppointment = new Appointment(name, date, time, doctor_name);
        appointmentQueue.add(newAppointment);
        System.out.println("New Appointment created!");

        System.out.println("Returning to appointment menu...");
        pause(1000);
        mainMenu();

    }

    public static void manageExistingMenu() throws InterruptedException {
        try (Scanner user_input = new Scanner(System.in)) {
            MainApp.clearScreen();
            System.out.println("Managing existing appointments...");
            System.out.println("Current Appointments: ");
            int order = 1;

            for (Appointment appointment : appointmentQueue) {
                System.out.print(order + ".) ");
                appointment.printDetails();
            }

        }
    }

    public static void printCurrentAppointments() {
        System.out.println("Current Appointments: ");
        for (Appointment appointment : appointmentQueue) {
            appointment.printDetails();
            System.out.println();
        }
    }

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

    public static void pause(int time) throws InterruptedException {
        Thread.sleep(time);
    }

}