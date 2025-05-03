import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private String name;
    private String specialty;
    private String availability;
    private List<String> patients;

    // Constructor
    public Doctor(String name, String specialty, String availability) {
        this.name = name;
        this.specialty = specialty;
        this.availability = availability;
        this.patients = new ArrayList<>();
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void addPatient(String newPatient) {
        if (patients.size() < 10) {
            this.patients.add(newPatient);
        }
    }

    // Getters
    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return "Name: " + name +
                " || Specialty: " + specialty +
                " || Availability: " + availability +
                " || Patients: " + String.join(", ", patients);
    }
}
