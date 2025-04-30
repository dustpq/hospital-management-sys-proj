import java.util.*;

class Person {

    private String specialty;
    private List<String> patients;

    public Person(String specialty) {
        this.specialty = specialty;
        this.patients = new ArrayList<>();
    }

    public void setSpecialty(String newSpecialty) {
        this.specialty = newSpecialty;
    }

    public void addPatient(String newPatient) {
        if (patients.size() < 10) {
            this.patients.add(newPatient);
        }
    }

    public String getSpecialty() {
        return specialty;
    }

    public List<String> getPatients() {
        return patients;
    }

    public String toString() {
        return "Specialty: " + specialty + " || Patients: " + String.join(", ", patients);
    }
}

public class Doctor {

    public static void main(String[] args) {
        HashMap<String, Person> doctors = new HashMap<>();
        String[] specialties = {
            "Cardiology", "Pediatrics", "Dermatology", "Neurology", "Oncology",
            "Orthopedics", "Psychiatry", "Radiology", "Surgery", "Emergency Medicine"
        };

        Random random = new Random();
        int startingId = 2231;

        // Generate 100 doctors
        for (int i = 0; i < 100; i++) {
            String doctorId = String.valueOf(startingId + i);
            String specialty = specialties[random.nextInt(specialties.length)];
            doctors.put(doctorId, new Person(specialty));
        }

        // Generate 100 patients and randomly assign to doctors (max 10 per doctor)
        for (int i = 1; i <= 100; i++) {
            String patientName = "Patient" + i;
            boolean assigned = false;
            while (!assigned) {
                int docId = startingId + random.nextInt(100);
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
            System.out.println("\nOptions: view | search | update | delete | exit");
            System.out.print("What would you like to do? ");
            String action = scanner.nextLine().toLowerCase();

            switch (action) {
                case "view":
                    ArrayList<String> keys = new ArrayList<>(doctors.keySet());
                    keys.sort(null);
                    int count = 1;
                    for (String key : keys) {
                        Person doc = doctors.get(key);
                        printDoctorInfo(count, doc);
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
                        Person doc = doctors.get(searchKey);
                        System.out.println("Doctor found...");
                        printDoctorInfo(doctorNumber, doc);
                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;

                case "update":
                    System.out.print("Enter doctor ID to update: ");
                    String updateKey = scanner.nextLine();
                    if (doctors.containsKey(updateKey)) {
                        Person doctor = doctors.get(updateKey);
                        System.out.print("New specialty (current: " + doctor.getSpecialty() + "): ");
                        String newSpecialty = scanner.nextLine();
                        if (!newSpecialty.isEmpty()) {
                            doctor.setSpecialty(newSpecialty);
                        }
                        System.out.println("Updated: " + doctor);
                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;

                case "delete":
                    System.out.print("Enter doctor ID to delete: ");
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

    // Method to print doctor info
    public static void printDoctorInfo(int index, Person doctor) {
        System.out.println("Doctor " + index + " || Specialty: " + doctor.getSpecialty()
                + " || Patients: " + String.join(", ", doctor.getPatients()));
    }
}
