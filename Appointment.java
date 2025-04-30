public class Appointment {

    private String name;
    private String date;
    private String time;
    private String doctor_name;
    private Doctor attending_doctor;

    public Appointment(String name, String date, String time, String doctor_name) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.doctor_name = doctor_name;
    }

    //setters for the appointment
    public String getDetail(String detail) {
        return switch (detail) {
            case "name" -> name;
            case "date" -> date;
            case "time" -> time;
            case "doctor_name" -> doctor_name;
            default -> null;
        };
    }

    //getters for the appointment
    public void setDetail(String detail, String value) {
        switch (detail) {
            case "name" -> name = value;
            case "date" -> date = value;
            case "time" -> time = value;
            case "doctor_name" -> doctor_name = value;
        }

    }

    public void printDetails() {
        System.out.println(
                "Appointment for " + name +
                        " on " + date +
                        " at " + time +
                        " with Dr. " + doctor_name
        );
    }

}