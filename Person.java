import java.util.ArrayList;
import java.util.List;

public class Person {
    private String availability;
    private String specialty;
    private List<String> patients;
    private String name; // Add a name field to the Person class

    public Person(String availability, String specialty) {
        this.availability = availability;
        this.specialty = specialty;
        this.patients = new ArrayList<>();
    }

    public Person(String availability, String specialty, String name) {
        this.availability = availability;
        this.specialty = specialty;
        this.patients = new ArrayList<>();
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
        return "Name: " + name + " || Specialty: " + specialty + " || Patients: " + String.join(", ", patients) + " || Availability: " + availability;
    }
}
