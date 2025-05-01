import com.sun.tools.javac.Main;

import java.util.Scanner;

public class patientRecordMenu {
    static Patient[] patients = new Patient[100];
    static int patientCount = 0;

    public static void mainMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("--- Patient Record Management ---");
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient by Name");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addPatient(sc);
                case 2 -> viewAllPatients();
                case 3 -> searchPatient(sc);
                case 4 -> {
                    System.out.println("Exiting patient menu...");
                    MainApp.pause(1000);
                    MainApp.mainMenu();
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 4);
    }

    static void addPatient(Scanner sc) {
        if (patientCount >= patients.length) {
            System.out.println("Patient record is full!");
            return;
        }
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        String age = sc.nextLine();
        System.out.print("Enter diagnosis: ");
        String diagnosis = sc.nextLine();
        System.out.print("Enter treatment history: ");
        String treatmentHistory = sc.nextLine();

        patients[patientCount++] = new Patient(name, age, diagnosis, treatmentHistory);
        System.out.println("Patient added successfully!");
    }

    static void viewAllPatients() {
        if (patientCount == 0) {
            System.out.println("No patient records available.");
            return;
        }
        System.out.println("\n--- All Patient Records ---");
        for (int i = 0; i < patientCount; i++) {
            System.out.println("\nPatient " + (i + 1) + ":");
            patients[i].display();
        }
    }

    static void searchPatient(Scanner sc) {
        System.out.print("Enter the patient's name to search: ");
        String searchName = sc.nextLine();
        boolean found = false;

        for (int i = 0; i < patientCount; i++) {
            if (patients[i].getDetail("name").equalsIgnoreCase(searchName)) {
                System.out.println("\nPatient Found:");
                patients[i].display();
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
        for (int i = 0; i < patientCount; i++) {
            if (patients[i].getDetail("name").equalsIgnoreCase(name)) {
                System.out.print("Enter new age: ");
                String newAge = sc.nextLine();
                System.out.print("Enter new diagnosis: ");
                String newDiagnosis = sc.nextLine();
                System.out.print("Enter new treatment history: ");
                String newTreatment = sc.nextLine();

                patients[i] = new Patient(name, newAge, newDiagnosis, newTreatment);
                System.out.println("Patient updated successfully!");
                return;
            }
        }
        System.out.println("Patient not found.");
    }

    static void deletePatient(Scanner sc) {
        System.out.print("Enter the patient's name to delete: ");
        String name = sc.nextLine();
        for (int i = 0; i < patientCount; i++) {
            if (patients[i].getDetail("name").equalsIgnoreCase(name)) {
                for (int j = i; j < patientCount - 1; j++) {
                    patients[j] = patients[j + 1]; // shift elements left
                }
                patients[--patientCount] = null;
                System.out.println("Patient deleted successfully.");
                return;
            }
        }
        System.out.println("Patient not found.");
    }
}
