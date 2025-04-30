import java.util.ArrayList;
import java.util.List;

public class Person {
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
