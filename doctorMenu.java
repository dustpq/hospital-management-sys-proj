import java.util.*;

class Person {

    private String specialty;
    private String availability;
    private List<String> patients;

    public Person(String specialty, String availability) {
        this.specialty = specialty;
        this.availability = availability;
        this.patients = new ArrayList<>();
    }

    public void setSpecialty(String newSpecialty) {
        this.specialty = newSpecialty;
    }

    public void setAvailability(String newAvailability) {
        this.availability = newAvailability;
    }

    public void addPatient(String newPatient) {
        if (patients.size() < 10) {
            this.patients.add(newPatient);
        }
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getAvailability() {
        return availability;
    }

    public List<String> getPatients() {
        return patients;
    }

    public String toString() {
        return "Specialty: " + specialty + " || Patients: " + String.join(", ", patients) + " || Availability: " + availability;
    }
}

public class Doctor {

    public static void main(String[] args) {
        HashMap<String, Person> doctors = new HashMap<>();
        String[] specialties = {
            "Family Medicine", "Pediatrics", "Dermatology", "Neurology", "Oncology",
            "Orthopedics", "Psychiatry", "Radiology", "Trauma Surgeon", "Surgery", "Emergency Medicine"
        };

        String[] availabilities = {
            "Mon-Fri: 7 AM – 3 PM", "Mon-Fri: 3 PM – 11 PM", "Mon-Fri: 11 PM – 7 AM",
            "Sat & Sun: 7 AM – 7 PM", "Sat: 7 AM – 7 PM", "Sun: 7 PM – 7 AM"
        };

        Random random = new Random();
        int startingId = 2231;

        // Generates 100 doctors
        for (int i = 0; i < 100; i++) {
            String doctorId = String.valueOf(startingId + i);
            String specialty = specialties[random.nextInt(specialties.length)];
            String availability = availabilities[random.nextInt(availabilities.length)];
            doctors.put(doctorId, new Person(specialty, availability));
        }

        // Generates 100 patients and randomly assigns them to doctors (max 10 per doctor)
        for (int i = 1; i <= 100; i++) {
            String patientName = "Patient" + i;
            boolean assigned = false;
            while (!assigned) {
                int docId = startingId + random.nextInt(100);
                String docKey = String.valueOf(docId);
                Person doc = doctors.get(docKey);
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
                        printDoctorInfo(count, key, doc);
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
                        System.out.println("\nDoctor found...");
                        printDoctorInfo(doctorNumber, searchKey, doc);
                    } else {
                        System.out.println("\nDoctor not found.");
                    }
                    break;

                case "update":
                    System.out.print("Enter doctor ID to update: ");
                    String updateKey = scanner.nextLine();
                    if (doctors.containsKey(updateKey)) {
                        Person doctor = doctors.get(updateKey);
                        System.out.print("New specialty (current: " + doctor.getSpecialty() + "): ");
                        String newSpecialty = scanner.nextLine();
                        System.out.print("New availability (current: " + doctor.getAvailability() + "): ");
                        String newAvailability = scanner.nextLine();
                        if (!newSpecialty.isEmpty()) {
                            doctor.setSpecialty(newSpecialty);
                        }
                        if (!newAvailability.isEmpty()) {
                            doctor.setAvailability(newAvailability);
                        }
                        System.out.println("\nUpdated:");
                        ArrayList<String> sortedKeys = new ArrayList<>(doctors.keySet());
                        sortedKeys.sort(null);
                        int doctorNumber = sortedKeys.indexOf(updateKey) + 1;
                        printDoctorInfo(doctorNumber, updateKey, doctor);
                    } else {
                        System.out.println("\nDoctor not found.");
                    }
                    break;

                case "delete":
                    System.out.print("Enter doctor ID to delete: ");
                    String deleteKey = scanner.nextLine();
                    if (doctors.remove(deleteKey) != null) {
                        System.out.println("\nDoctor " + deleteKey + " removed.");
                    } else {
                        System.out.println("\nDoctor not found.");
                    }
                    break;

                case "exit":
                    running = false;
                    break;

                default:
                    System.out.println("\nInvalid option.");
            }
        }

        scanner.close();
    }

    // Doctor Info
    public static void printDoctorInfo(int index, String doctorId, Person doctor) {
        System.out.println("Doctor " + index + " (ID: " + doctorId + ") || Specialty: " + doctor.getSpecialty()
                + " || Patients: " + String.join(", ", doctor.getPatients())
                + " || Availability: " + doctor.getAvailability());
    }
}
