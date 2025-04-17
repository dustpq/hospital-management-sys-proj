import java.util.PriorityQueue;  // THE DASHES ARE MY NOTES 
import java.util.Scanner;

public class emergencyPatientMenu {

    // PriorityQueue to store patients, sorting based on severity level
    static PriorityQueue<Patient> patientQueue = new PriorityQueue<>((a, b) -> {
        // Checking the severity levels to prioritize higher severity levels
        return b.getSeverity().compareTo(a.getSeverity());
    });

    public static void main(String[] args) {
        // Loop to continue displaying the menu until the user exits
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("*** Emergency Patient Menu ***");
            System.out.println("1. Add new emergency patient");
            System.out.println("2. View emergency patients");
            System.out.println("3. Exit");

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

                default:
                    System.out.println("***Error***");
            }
        }
    }
}

//  Store details of each emergency patient
class Patient {

    private String name;
    private String condition;
    private String severity;

    // we use the constructor to give an object its value or to tell what exactly the object is 
    public Patient(String name, String condition, String severity) {
        this.name = name;
        this.condition = condition;
        this.severity = severity;
    }

    // Getter for severity (used for comparison in PriorityQueue)
    public String getSeverity() {
        return severity;
    }

    // wanted the outputs to stack 
    public void printDetails() {
        System.out.println("*".repeat(30)); 
        System.out.println("Patient: " + name);
        System.out.println("Condition: " + condition);
        System.out.println("Severity: " + severity);
        System.out.println("*".repeat(30)); 
    }
}

