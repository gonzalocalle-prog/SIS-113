/**
 * A scheduled appointment.
 *
 * ENCAPSULATION: like Snapshot in Sentinel, it is born valid. The only field
 * that changes is "completed", and only through perform(), which cannot be
 * undone and cannot happen twice.
 */
public final class Appointment {

    private final int number;
    private final Patient patient;
    private final Doctor doctor;
    private final String dateTime;
    private boolean completed = false;

    public Appointment(int number, Patient patient, Doctor doctor, String dateTime) {
        if (number <= 0) {
            throw new IllegalArgumentException("appointment number must be positive, got " + number);
        }
        if (patient == null || doctor == null) {
            throw new IllegalArgumentException("patient and doctor are required");
        }
        if (!patient.isActive()) {
            throw new IllegalArgumentException("patient " + patient.getRecordNumber() + " is inactive");
        }
        if (!doctor.hasActiveLicense()) {
            throw new IllegalArgumentException("doctor " + doctor.getId() + " has a suspended license");
        }
        if (!doctor.isTreating(patient)) {
            throw new IllegalArgumentException("doctor " + doctor.getId() + " does not treat patient " + patient.getRecordNumber());
        }
        if (dateTime == null || dateTime.isBlank()) {
            throw new IllegalArgumentException("date and time are required");
        }
        this.number = number;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
    }

    public int getNumber() {
        return number;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDateTime() {
        return dateTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    /** The consultation: the patient must still be active; Doctor and Patient check the rest. */
    public void perform(String diagnosis) {
        if (completed) {
            throw new IllegalArgumentException("appointment " + number + " was already completed");
        }
        if (!patient.isActive()) {
            throw new IllegalArgumentException("patient " + patient.getRecordNumber() + " is inactive");
        }
        doctor.diagnose(patient, diagnosis);
        completed = true;
    }

    @Override
    public String toString() {
        return "#" + number + " " + dateTime + " | " + patient.getName() + " with " + doctor.getName()
                + (completed ? " | completed" : " | pending");
    }
}
