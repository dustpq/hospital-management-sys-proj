import java.util.Scanner;

class Patient {
    String name;
    int age;
    String diagnosis;
    String treatmentHistory;

    Patient(String name, int age, String diagnosis, String treatmentHistory) {
        this.name = name;
        this.age = age;
        this.diagnosis = diagnosis;
        this.treatmentHistory = treatmentHistory;
    }

    void display() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Treatment History: " + treatmentHistory);
    }
}

public class patientRecordMenu {
    static Patient[] patients = new Patient[100];
    static int patientCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Patient Record Management ---");
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
                case 4 -> System.out.println("Exiting program...");
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
        int age = sc.nextInt();
        sc.nextLine(); // consume newline
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
            if (patients[i].name.equalsIgnoreCase(searchName)) {
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
}

