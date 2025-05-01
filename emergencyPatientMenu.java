import java.util.PriorityQueue;
import java.util.Scanner;

public class emergencyPatientMenu {

    // PriorityQueue to store patients, sorting based on severity level
    static PriorityQueue<Patient> patientQueue = new PriorityQueue<>((a, b) -> {
        // Checking the severity levels and sorting them
        return b.getDetail("severity").compareTo(a.getDetail("severity"));
    });

    public static void mainMenu() {
        // Loop to continue displaying the menu until the user exits
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("*** Emergency Patient Menu ***");
            System.out.println("1. Add new emergency patient");
            System.out.println("2. View emergency patients");
            System.out.println("3. Search patient");
            System.out.println("4. Edit patient");
            System.out.println("5. Delete patient");
            System.out.println("6. Exit");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Add a new emergency patient
                    System.out.println("Patient name:");
                    String name = scanner.nextLine();

                    System.out.println("Patient's condition:");
                    String condition = scanner.nextLine();

                    String severity = "";
                    // Loop to ensure the severity level is valid
                    while (true) {
                        System.out.print("Severity level?: ");
                        severity = scanner.nextLine();

                        // Validating severity level
                        if (severity.equals("Stable") || severity.equals("Moderate") || severity.equals("Severe")) {
                            break;
                        } else {
                            System.out.println("***Error***");
                            System.out.println("Stable Moderate Severe");
                        }
                    }

                    // Create a new patient object
                    Patient newPatient = new Patient(name, condition, severity);
                    // Add the patient to the queue based on their severity
                    patientQueue.add(newPatient);
                    System.out.println("Patient added");
                    break;

                case 2:
                    // View emergency patients
                    if (patientQueue.isEmpty()) {
                        System.out.println("No patients in the queue.");
                    } else {
                        System.out.println("Patients by severity level:");
                        // Loop through and display patient details
                        for (Patient patient : patientQueue) {
                            patient.printDetails();
                        }
                    }
                    break;

                case 3:
                    // Search for a patient
                    System.out.print("Enter patient name to search: ");
                    String searchName = scanner.nextLine();
                    boolean foundSearch = false;

                    for (Patient patient : patientQueue) {
                        if (patient.getDetail("name").equalsIgnoreCase(searchName)) {
                            System.out.println("Patient found:");
                            patient.printDetails(); // Show patient info
                            foundSearch = true;
                            break;
                        }
                    }

                    if (!foundSearch) {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 4:
                    // Edit a patient
                    System.out.print("Enter name of patient to edit: ");
                    String editName = scanner.nextLine();
                    boolean foundEdit = false;

                    // Create a new queue to temporarily store updated patients
                    PriorityQueue<Patient> tempEditQueue = new PriorityQueue<>((a, b) -> {
                        return b.getDetail("severity").compareTo(a.getDetail("severity"));
                    });

                    // Go through the queue to find a match
                    while (!patientQueue.isEmpty()) {
                        Patient p = patientQueue.poll();
                        if (p.getDetail("name").equalsIgnoreCase(editName)) {
                            // Found the patient to edit
                            System.out.println("New name:");
                            String newName = scanner.nextLine();

                            System.out.println("New condition:");
                            String newCondition = scanner.nextLine();

                            String newSeverity = "";
                            // Validate severity again
                            while (true) {
                                System.out.print("New severity level?: ");
                                newSeverity = scanner.nextLine();
                                if (newSeverity.equals("Stable") || newSeverity.equals("Moderate") || newSeverity.equals("Severe")) {
                                    break;
                                } else {
                                    System.out.println("***Error***");
                                    System.out.println("Stable Moderate Severe");
                                }
                            }

                            // Update patient
                            tempEditQueue.add(new Patient(newName, newCondition, newSeverity));
                            foundEdit = true;
                        } else {
                            tempEditQueue.add(p); // Keep existing patients
                        }
                    }

                    patientQueue = tempEditQueue; // Replace old queue
                    if (foundEdit) {
                        System.out.println("Patient info updated.");
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 5:
                    // Delete a patient
                    System.out.print("Enter name of patient to delete: ");
                    String deleteName = scanner.nextLine();
                    boolean foundDelete = false;

                    // Create a new queue for patients we want to keep
                    PriorityQueue<Patient> tempDeleteQueue = new PriorityQueue<>((a, b) -> {
                        return b.getDetail("severity").compareTo(a.getDetail("severity"));
                    });

                    while (!patientQueue.isEmpty()) {
                        Patient p = patientQueue.poll();
                        if (p.getDetail("name").equalsIgnoreCase(deleteName)) {
                            foundDelete = true; // Skip this patient
                        } else {
                            tempDeleteQueue.add(p);
                        }
                    }

                    patientQueue = tempDeleteQueue;
                    if (foundDelete) {
                        System.out.println("Patient deleted.");
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 6:
                    // Exit the menu
                    System.out.println("Exit");
                    MainApp.pause(1000);
                    MainApp.mainMenu();
                    return; // Exit the loop

                default:
                    System.out.println("***Error***");
            }
        }
    }
}
