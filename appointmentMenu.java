public class appointmentMenu {

    public static void startMenu() {
        displayMenu();
    }

    public static void displayMenu() {

        System.out.println("Welcome to the Appointment Management Menu!");
        System.out.println("What would you like to do?");
        System.out.println("1. Create new appointment");
        System.out.println("2. Manage existing appointments");
        System.out.println("3. Exit to main menu");

    }

    public static void newAppointmentMenu() {
        System.out.println();
    }

}

class Appointment {

    private String appointmentName;
    private String name;
    private String date;
    private String time;

    public Appointment(String appointmentName, String name, String date, String time) {
        this.appointmentName = appointmentName;
        this.name = name;
        this.date = date;
        this.time = time;
    }

}

class AppointmentList {

}