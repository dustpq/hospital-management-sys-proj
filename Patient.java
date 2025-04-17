public class Patient {
    private String name;
    private String age;
    private String severity;
    private String diagnosis;
    private String treatmentHistory;
    private String condition;

    public Patient(String name, String condition, String severity) {
        this.name = name;
        this.condition = condition;
        this.severity = severity;
    }

    public Patient(String name, String age, String diagnosis, String treatmentHistory) {
        this.name = name;
        this.age = age;
        this.diagnosis = diagnosis;
        this.treatmentHistory = treatmentHistory;
    }

    public void printDetails() {
        System.out.println("*".repeat(30));
        System.out.println("Patient: " + name);
        System.out.println("Condition: " + condition);
        System.out.println("Severity: " + severity);
        System.out.println("*".repeat(30));
    }

    void display() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Treatment History: " + treatmentHistory);
    }

    public String getDetail(String detail) {
        return switch (detail) {
            case "name" -> name;
            case "age" -> age;
            case "severity" -> severity;
            case "diagnosis" -> diagnosis;
            case "treatment" -> treatmentHistory;
            case "condition" -> condition;
            default -> null;
        };
    }
}
