import java.util.*;

public class appointmentMenu {

    static PriorityQueue<Appointment> appointmentQueue = new PriorityQueue<>(
            (a, b) -> a.getDetails("date").compareTo(b.getDetails("date"))
                    + a.getDetails("time").compareTo(b.getDetails("time"))
    );

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

    public static void newAppointmentMenu() {
        System.out.println("New Appointment Creation");
        try (Scanner user_input = new Scanner(System.in)) {

            System.out.print("Enter patient name: ");
            String name = user_input.nextLine();
            System.out.print("Enter appointment date (YYYY-MM-DD): ");
            String date = user_input.nextLine();
            System.out.print("Enter appointment time (HH:MM): ");
            String time = user_input.nextLine();
            System.out.print("Enter doctor name: ");
            String doctor_name = user_input.nextLine();

            Appointment newAppointment = new Appointment(name, date, time, doctor_name);
            appointmentQueue.add(newAppointment);

        }
    }

    public static void manageExistingMenu() {
        try (Scanner user_input = new Scanner(System.in)) {
            MainApp.clearScreen();
            System.out.println("Managing existing appointments...");
        }
    }

    public static void printCurrentAppointments() {
        System.out.println("Current Appointments: ");
        for (Appointment appointment : appointmentQueue) {
            appointment.printDetails();
            System.out.println();
        }
    }

    public static void pause(int time) throws InterruptedException {
        Thread.sleep(time);
    }

}



class Appointment {

    private String name;
    private String date;
    private String time;
    private String doctor_name;


    public Appointment(String name, String date, String time, String doctor_name) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.doctor_name = doctor_name;
    }

    //setters for the appointment
    public String getDetails(String detail) {
        return switch (detail) {
            case "name" -> name;
            case "date" -> date;
            case "time" -> time;
            case "doctor_name" -> doctor_name;
            default -> null;
        };
    }

    //getters for the appointment
    public void setDetail(String detail, String value) {
        switch (detail) {
            case "name" -> name = value;
            case "date" -> date = value;
            case "time" -> time = value;
            case "doctor_name" -> doctor_name = value;
        }

    }

    public void printDetails() {
        System.out.println("Patient name: " + name);
        System.out.println("Date of appointment: " + date);
        System.out.println("Time of appointment: " + time);
        System.out.println("Doctor Attending: " + doctor_name);
    }

}