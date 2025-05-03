/**
 *
 * @author michelle
 */

import java.util.*;

public class doctorMenu {
    static HashMap<String, Doctor> doctors = new HashMap<>();

    static String[] specialties = {
            "Family Medicine", "Pediatrics", "Dermatology", "Neurology", "Oncology",
            "Orthopedics", "Psychiatry", "Radiology", "Trauma Surgeon", "Surgery", "Emergency Medicine"
    };

    static String[] availabilities = {
            "Mon-Fri: 7 AM – 3 PM", "Mon-Fri: 3 PM – 11 PM", "Mon-Fri: 11 PM – 7 AM",
            "Sat & Sun: 7 AM – 7 PM", "Sat: 7 AM – 7 PM", "Sun: 7 PM – 7 AM"
    };

    public static void mainMenu() {


        Random random = new Random();
        int startingId = 2231;

        // Generate 100 doctors
        for (int i = 0; i < 100; i++) {
            String doctorId = String.valueOf(startingId + i);
            String specialty = specialties[random.nextInt(specialties.length)];
            String availability = availabilities[random.nextInt(availabilities.length)];
            String name = "Doctor " + (i + 1);
            doctors.put(doctorId, new Doctor(name, specialty, availability));
        }

        // Generate 100 patients and randomly assign to doctors (max 10 per doctor)
        int patientCounter = 100;
        for (int i = 1; i <= 100; i++) {
            String patientName = "Patient" + i;

            boolean assigned = false;
            while (!assigned) {
                // Pick a random doctor
                int docId = startingId + random.nextInt(100); // Ensure the ID is within the generated range
                String docKey = String.valueOf(docId);
                Doctor doc = doctors.get(docKey);

                // Check if the doctor exists and has less than 10 patients
                if (doc != null && doc.getPatients().size() < 10) {
                    doc.addPatient(patientName);
                    assigned = true;
                }
            }
        }

        // User interaction
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nOptions: view | search | update | delete | add doctor | add patient | exit");
            System.out.print("What would you like to do? ");
            String action = scanner.nextLine().toLowerCase();

            switch (action) {
                case "view":
                    ArrayList<String> keys = new ArrayList<>(doctors.keySet());
                    keys.sort(null);
                    int count = 1;
                    for (String key : keys) {
                        Doctor doctor = doctors.get(key);
                        if (doctor != null) {
                            // Inject fallback name only if null
                            if (doctor.getName() == null || doctor.getName().isEmpty()) {
                                doctor.setName("Doctor " + count);
                            }

                            // Inject other fallbacks if needed
                            if (doctor.getSpecialty() == null) doctor.setSpecialty("Unknown");
                            if (doctor.getAvailability() == null) doctor.setAvailability("Unknown");

                            printDoctorInfo(count, key, doctor);
                        } else {
                            System.out.println("Doctor #" + count + ": [No data]");
                            System.out.println("-----------------------------");
                        }
                        count++;
                    }
                    break;

                case "search":
                    System.out.print("Enter doctor ID to search: ");
                    String searchKey = scanner.nextLine();
                    if (doctors.containsKey(searchKey)) {
                        ArrayList<String> sortedKeys = new ArrayList<>(doctors.keySet());
                        sortedKeys.sort(null);
                        int doctorNumber = sortedKeys.indexOf(searchKey) + 1;
                        Doctor doc = doctors.get(searchKey);
                        System.out.println("\nDoctor found...");
                        printDoctorInfo(doctorNumber, searchKey, doc);
                    } else {
                        System.out.println("\nDoctor not found.");
                    }
                    break;

                case "update":
                    System.out.print("Enter doctor number to update: ");
                    String updateKey = scanner.nextLine();
                    if (doctors.containsKey(updateKey)) {
                        Doctor doctor = doctors.get(updateKey);
                        System.out.print("New name (current: " + doctor.getName() + "): ");
                        String newName = scanner.nextLine();
                        System.out.print("New specialty (current: " + doctor.getSpecialty() + "): ");
                        String newSpecialty = scanner.nextLine();
                        doctor.setName(newName);
                        doctor.setSpecialty(newSpecialty);
                        System.out.println("Updated: " + doctor);
                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;

                case "delete":
                    System.out.print("Enter doctor number to delete: ");
                    String deleteKey = scanner.nextLine();
                    if (doctors.remove(deleteKey) != null) {
                        System.out.println("Doctor " + deleteKey + " removed.");
                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;

                case "add doctor":
                    System.out.print("Enter new doctor's specialty: ");
                    String specialty = scanner.nextLine();
                    System.out.print("Enter new doctor's availability: ");
                    String availability = scanner.nextLine();

                    int maxId = doctors.keySet().stream()
                            .mapToInt(Integer::parseInt)
                            .max()
                            .orElse(startingId + 99);

                    String newDoctorId = String.valueOf(maxId + 1);
                    System.out.print("Enter new doctor's name: ");
                    String name = scanner.nextLine();
                    doctors.put(newDoctorId, new Doctor(name, specialty, availability));

                    System.out.println("\nDoctor added successfully:");
                    printDoctorInfo(doctors.size(), newDoctorId, doctors.get(newDoctorId));
                    break;

                case "add patient":
                    patientCounter++;
                    String newPatient = "Patient" + patientCounter;
                    boolean assigned = false;

                    for (String docKey : doctors.keySet()) {
                        Doctor doc = doctors.get(docKey);
                        if (doc != null && doc.getPatients().size() < 10) { // Ensure doc is not null
                            doc.addPatient(newPatient);
                            System.out.println("\n" + newPatient + " assigned to Doctor ID: " + docKey);
                            assigned = true;
                            break;
                        }
                    }

                    if (!assigned) {
                        System.out.println("\nNo available doctors to assign the patient.");
                        patientCounter--;
                    }
                    break;

                case "exit":
                    running = false;
                    System.out.println("Returning to main menu...");
                    MainApp.pause(1000);
                    MainApp.mainMenu();
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }

    /**
     * Prints detailed information about a doctor.
     * @param index The index of the doctor in the list.
     * @param doctorId The ID of the doctor.
     * @param doctor The Person object representing the doctor.
     */
    private static void printDoctorInfo(int index, String doctorId, Doctor doctor) {
        System.out.println("Doctor #" + index);
        System.out.println("ID: " + doctorId);
        System.out.println("Name: " + doctor.getName());
        System.out.println("Specialty: " + doctor.getSpecialty());
        System.out.println("Availability: " + doctor.getAvailability());
        System.out.println("Patients: " + String.join(", ", doctor.getPatients()));
        System.out.println("-----------------------------");
    }
}
