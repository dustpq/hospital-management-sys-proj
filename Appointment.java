public class Appointment {

    private String name; // Patient's name
    private String date; // Appointment date in MM/dd/yyyy format
    private String time; // Appointment time in hh:mm a format
    private String doctor_name; // Doctor's name
    private Doctor attending_doctor; // Reference to the attending doctor (optional)

    /**
     * Constructor to initialize an appointment with the given details.
     * @param name The patient's name.
     * @param date The appointment date.
     * @param time The appointment time.
     * @param doctor_name The doctor's name.
     */
    public Appointment(String name, String date, String time, String doctor_name) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.doctor_name = doctor_name;
    }

    /**
     * Retrieves the value of a specific detail of the appointment.
     * @param detail The detail to retrieve ("name", "date", "time", or "doctor_name").
     * @return The value of the requested detail, or null if the detail is invalid.
     */
    public String getDetail(String detail) {
        return switch (detail) {
            case "name" -> name;
            case "date" -> date;
            case "time" -> time;
            case "doctor_name" -> doctor_name;
            default -> null;
        };
    }

    /**
     * Updates the value of a specific detail of the appointment.
     * @param detail The detail to update ("name", "date", "time", or "doctor_name").
     * @param value The new value for the detail.
     */
    public void setDetail(String detail, String value) {
        switch (detail) {
            case "name" -> name = value;
            case "date" -> date = value;
            case "time" -> time = value;
            case "doctor_name" -> doctor_name = value;
        }

    }

    /**
     * Prints the details of the appointment in a readable format.
     */
    public void printDetails() {
        System.out.println(
                "Appointment for " + name +
                        " on " + date +
                        " at " + time +
                        " with Dr. " + doctor_name
        );
    }

    public String getDetails() {
        return "Appointment for " + name +
                " on " + date +
                " at " + time +
                " with Dr. " + doctor_name;
    }

}
