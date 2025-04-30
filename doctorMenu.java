import java.util.LinkedHashMap;
/**
 *
 * @author michelle
 */

import java.util.*;

class Person {
    private String name;
    private String specialty;
    private List<String> patients;

    public Person(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
        this.patients = new ArrayList<>();
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setSpecialty(String newSpecialty) {
        this.specialty = newSpecialty;
    }

    public void addPatient(String newPatient) {
        if (patients.size() < 10) {
            this.patients.add(newPatient);
        }
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public List<String> getPatients() {
        return patients;
    }

    public String toString() {
        return "Doctor " + name + " || Specialty: " + specialty + " || Patients: " + patients;
    }
}

public class doctorMenu {
    public static void main(String[] args) {
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
            System.out.println("\nOptions: view | update | delete | exit");
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

                case "exit":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}
