import java.util.Scanner;
import java.util.ArrayList;

public class patientRecordMenu {
    static ArrayList<Patient> patients = new ArrayList<>();

    public static void mainMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("--- Patient Record Management ---");
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient by Name");
            System.out.println("4. Edit Patient");
            System.out.println("5. Delete Patient");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addPatient(sc);
                case 2 -> viewAllPatients();
                case 3 -> searchPatient(sc);
                case 4 -> editPatient(sc);
                case 5 -> deletePatient(sc);
                case 6 -> {
                    System.out.println("Exiting patient menu...");
                    MainApp.pause(1000);
                    MainApp.mainMenu();
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 6);
    }

    static void addPatient(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        String age = sc.nextLine();
        System.out.print("Enter diagnosis: ");
        String diagnosis = sc.nextLine();
        System.out.print("Enter treatment history: ");
        String treatmentHistory = sc.nextLine();

        patients.add(new Patient(name, age, diagnosis, treatmentHistory));
        System.out.println("Patient added successfully!");
    }

    static void viewAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patient records available.");
            return;
        }
        System.out.println("\n--- All Patient Records ---");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println("\nPatient " + (i + 1) + ":");
            patients.get(i).display();
        }
    }

    static void searchPatient(Scanner sc) {
        System.out.print("Enter the patient's name to search: ");
        String searchName = sc.nextLine();
        boolean found = false;

        for (Patient patient : patients) {
            if (patient.getDetail("name").equalsIgnoreCase(searchName)) {
                System.out.println("\nPatient Found:");
                patient.display();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Patient not found.");
        }
    }

    static void editPatient(Scanner sc) {
        System.out.print("Enter the patient's name to edit: ");
        String name = sc.nextLine();
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getDetail("name").equalsIgnoreCase(name)) {
                System.out.print("Enter new age: ");
                String newAge = sc.nextLine();
                System.out.print("Enter new diagnosis: ");
                String newDiagnosis = sc.nextLine();
                System.out.print("Enter new treatment history: ");
                String newTreatment = sc.nextLine();

                patients.set(i, new Patient(name, newAge, newDiagnosis, newTreatment));
                System.out.println("Patient updated successfully!");
                return;
            }
        }
        System.out.println("Patient not found.");
    }

    static void deletePatient(Scanner sc) {
        System.out.print("Enter the patient's name to delete: ");
        String name = sc.nextLine();
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getDetail("name").equalsIgnoreCase(name)) {
                patients.remove(i);
                System.out.println("Patient deleted successfully.");
                return;
            }
        }
        System.out.println("Patient not found.");
    }
}
