import java.util.Random;

public class TestDataGenerator {

    private static final Random random = new Random();

    public static void generateTestData() {
        generatePatients();
        generateEmergencyPatients();
        generateAppointments();
        generateDoctors();
    }

    private static void generatePatients() {
        for (int i = 1; i <= 100; i++) {
            String name = "Patient " + i;
            String condition = "Condition " + i;
            String diagnosis = "Diagnosis " + i;
            String treat_history = "History " + i;
            Patient patient = new Patient(name, condition, diagnosis, treat_history);
            patientRecordMenu.patients.add(patient);
        }
        System.out.println("Generated 100 test patients for patient records.");
    }

    private static void generateEmergencyPatients() {
        for (int i = 1; i <= 100; i++) {
            String name = "Emergency Patient " + i;
            String condition = "Emergency Condition " + i;
            String severity = switch (random.nextInt(3)) {
                case 0 -> "Stable";
                case 1 -> "Moderate";
                default -> "Severe";
            };
            Patient patient = new Patient(name, condition, severity);
            emergencyPatientMenu.patientQueue.add(patient);
        }
        System.out.println("Generated 100 test emergency patients.");
    }

    private static void generateAppointments() {
        for (int i = 1; i <= 100; i++) {
            String patientName = "Patient " + i;
            String date = String.format("%02d/15/2023", (i % 12) + 1);
            String time = String.format("%02d:00 %s", (i % 12) + 1, (i % 2 == 0) ? "AM" : "PM");
            String doctorName = "Doctor " + (i % 10 + 1);
            Appointment appointment = new Appointment(patientName, date, time, doctorName);
            appointmentMenu.appointmentQueue.add(appointment);
        }
        appointmentMenu.syncAppointments();
        System.out.println("Generated 100 test appointments.");
    }

    private static void generateDoctors() {
        for (int i = 1; i <= 100; i++) {
            String id = "D" + i;
            String name = "Doctor " + i;
            String specialty = doctorMenu.specialties[random.nextInt(doctorMenu.specialties.length)];
            String availability = doctorMenu.availabilities[random.nextInt(doctorMenu.availabilities.length)];
            Doctor doctor = new Doctor(availability, specialty, name);
            doctorMenu.doctors.put(id, doctor);
        }
        System.out.println("Generated 100 test doctors.");
    }
}
