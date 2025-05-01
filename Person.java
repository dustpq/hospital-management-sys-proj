import java.util.ArrayList;
import java.util.List;

public class Person {
    private String availability;
    private String specialty;
    private List<String> patients;

    public Person(String availability, String specialty) {
        this.availability = availability;
        this.specialty = specialty;
        this.patients = new ArrayList<>();
    }

    public void setAvailability(String newAvailability) {
        this.availability = newAvailability;
    }

    public void setSpecialty(String newSpecialty) {
        this.specialty = newSpecialty;
    }

    public void addPatient(String newPatient) {
        if (patients.size() < 10) {
            this.patients.add(newPatient);
        }
    }

    public String getAvailability() {
        return availability;
    }

    public String getSpecialty() {
        return specialty;
    }

    public List<String> getPatients() {
        return patients;
    }

    public String toString() {
        return "Specialty: " + specialty + " || Patients: " + String.join(", ", patients) + " || Availability: " + availability;
    }
}
