import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        for (int i = 1; i <= 100; i++) {
            String patientName = "Patient " + i;

            // Generate a random date within the next month
            LocalDate randomDate = LocalDate.now().plusDays(random.nextInt(30) + 1);
            String date = randomDate.format(dateFormatter);

            // Generate random time between 7 AM and 8 PM
            int hour = 7 + random.nextInt(14); // 7 AM to 8 PM (14 hours range)
            String period = (hour < 12) ? "AM" : "PM";
            int adjustedHour = (hour > 12) ? hour - 12 : hour; // Convert to 12-hour format
            String time = String.format("%02d:00 %s", adjustedHour, period);

            String doctorName = "Doctor " + (i % 10 + 1);

            Appointment appointment = new Appointment(patientName, date, time, doctorName);
            appointmentMenu.appointmentQueue.add(appointment);
        }
        appointmentMenu.syncAppointments();
        System.out.println("Generated 100 test appointments with dates spanning up to a month in advance.");
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
