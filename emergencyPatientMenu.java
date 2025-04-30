import java.util.PriorityQueue;  // THE DASHES ARE MY NOTES
import java.util.Scanner;

public class emergencyPatientMenu {

    // PriorityQueue to store patients, sorting based on severity level
    static PriorityQueue<Patient> patientQueue = new PriorityQueue<>((a, b) -> {
        // Checking the severity levels and sorting them
        return b.getDetail("severity").compareTo(a.getDetail("severity"));
    });

    public static void main(String[] args) {
        // Loop to continue displaying the menu until the user exits
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("*** Emergency Patient Menu ***");
            System.out.println("1. Add new emergency patient");
            System.out.println("2. View emergency patients");
            System.out.println("3. Exit");
            System.out.println("4. Edit patient");
            System.out.println("5. Delete patient");
            System.out.println("6. Search for patient");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    //When adding a new patient
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
                        }// used to break out the loop if they have a typo
                    }

                    // this is to make a new object
                    Patient newPatient = new Patient(name, condition, severity);
                    // Adding the patient to the queue based on their severity
                    patientQueue.add(newPatient);
                    System.out.println("Patient added");
                    break;

                case 2:
                    // Viewing emergency quoue
                    if (patientQueue.isEmpty()) {
                        System.out.println("No patients in the queue.");
                    } else {
                        System.out.println(" Patients by severity level:");
                        //looping and making patient details
                        for (Patient patient : patientQueue) {
                            patient.printDetails();
                        }
                    }
                    break;

                case 3:
                    System.out.println("Exit"); // this is to go to menu
                    return; // exit out loop

                case 4:
                    // Edit a patient
                    System.out.print("Enter name of patient to edit: ");
                    String editName = scanner.nextLine();
                    boolean foundEdit = false;

                    // make a new queue to temporarily store updated patients
                    PriorityQueue<Patient> tempEditQueue = new PriorityQueue<>((a, b) -> {
                        return b.getDetail("severity").compareTo(a.getDetail("severity"));
                    });

                    // go through queue to find match
                    while (!patientQueue.isEmpty()) {
                        Patient p = patientQueue.poll();
                        if (p.getDetail("name").equalsIgnoreCase(editName)) {
                            // found the patient to edit
                            System.out.println("New name:");
                            String newName = scanner.nextLine();

                            System.out.println("New condition:");
                            String newCondition = scanner.nextLine();

                            String newSeverity = "";
                            // validate severity again
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

                            // update patient
                            tempEditQueue.add(new Patient(newName, newCondition, newSeverity));
                            foundEdit = true;
                        } else {
                            tempEditQueue.add(p); // keep existing patients
                        }
                    }

                    patientQueue = tempEditQueue; // replace old queue
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

                    // new queue for patients we want to keep
                    PriorityQueue<Patient> tempDeleteQueue = new PriorityQueue<>((a, b) -> {
                        return b.getDetail("severity").compareTo(a.getDetail("severity"));
                    });

                    while (!patientQueue.isEmpty()) {
                        Patient p = patientQueue.poll();
                        if (p.getDetail("name").equalsIgnoreCase(deleteName)) {
                            foundDelete = true; // skip this patient
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
                    // Search for a patient
                    System.out.print("Enter patient name to search: ");
                    String searchName = scanner.nextLine();
                    boolean foundSearch = false;

                    for (Patient patient : patientQueue) {
                        if (patient.getDetail("name").equalsIgnoreCase(searchName)) {
                            System.out.println("Patient found:");
                            patient.printDetails(); // show patient info
                            foundSearch = true;
                            break;
                        }
                    }

                    if (!foundSearch) {
                        System.out.println("Patient not found.");
                    }
                    break;

                default:
                    System.out.println("***Error***");
            }
        }
    }
}
