import com.sun.tools.javac.Main;

import java.util.LinkedHashMap;
/**
 *
 * @author michelle
 */

import java.util.*;

public class doctorMenu {
    public static void mainMenu() {
        HashMap<String, Person> doctors = new HashMap<>();
        String[] specialties = {
            "Cardiology", "Pediatrics", "Dermatology", "Neurology", "Oncology",
            "Orthopedics", "Psychiatry", "Radiology", "Surgery", "Emergency Medicine"
        };

        Random random = new Random();

        // Generate 100 doctors
        for (int i = 1; i <= 100; i++) {
            String doctorId = String.valueOf(i);
            String name = "Dr" + doctorId;
            String specialty = specialties[random.nextInt(specialties.length)];
            doctors.put(doctorId, new Person(name, specialty));
        }

        // Generate 100 patients and randomly assign to doctors (max 10 per doctor)
        int patientCounter = 100;
        for (int i = 1; i <= 100; i++) {
            String patientName = "Patient" + i;

            boolean assigned = false;
            while (!assigned) {
                // Pick a random doctor
                int docId = 1 + random.nextInt(100);
                String docKey = String.valueOf(docId);
                Person doc = doctors.get(docKey);

                if (doc.getPatients().size() < 10) {
                    doc.addPatient(patientName);
                    assigned = true;
                }
            }
        }

        // User interaction
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nOptions: view | update | delete | add doctor | add doctor | exit");
            System.out.print("What would you like to do? ");
            String action = scanner.nextLine().toLowerCase();

            switch (action) {
                case "view":
                    ArrayList<String> keys = new ArrayList<>(doctors.keySet());
                    keys.sort(null);
                    for (String key : keys) {
                        System.out.println(doctors.get(key));
                    }
                    break;

                case "update":
                    System.out.print("Enter doctor number to update: ");
                    String updateKey = scanner.nextLine();
                    if (doctors.containsKey(updateKey)) {
                        Person doctor = doctors.get(updateKey);
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
                    doctors.put(newDoctorId, new Person(specialty, availability));

                    System.out.println("\nDoctor added successfully:");
                    printDoctorInfo(doctors.size(), newDoctorId, doctors.get(newDoctorId));
                    break;

                case "add patient":
                    patientCounter++;
                    String newPatient = "Patient" + patientCounter;
                    boolean assigned = false;

                    for (String docKey : doctors.keySet()) {
                        Person doc = doctors.get(docKey);
                        if (doc.getPatients().size() < 10) {
                            doc.addPatient(newPatient);
                            System.out.println("\n" + newPatient + " assigned to Doctor ID: " + docKey);
                            assigned = true;
                            break;
                        }
                    }

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
}
