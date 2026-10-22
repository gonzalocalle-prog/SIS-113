/**
 * A hospital patient.
 *
 * ENCAPSULATION: every field is private. The diagnosis is sensitive data, so
 * there is no getDiagnosis() and no setDiagnosis(): it can only be read or
 * written by passing in a Doctor who is authorized to do so.
 */
public class Patient {

    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 120;

    private final String recordNumber;
    private final String name;
    private final int age;
    private String diagnosis = "";
    private boolean active = true;

    /** ENCAPSULATION: the guard. A patient aged 150 or with no name cannot exist. */
    public Patient(String recordNumber, String name, int age) {
        if (recordNumber == null || recordNumber.isBlank()) {
            throw new IllegalArgumentException("record number is required");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("patient name is required");
        }
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new IllegalArgumentException("age must be between 0 and 120, got " + age);
        }
        this.recordNumber = recordNumber;
        this.name = name;
        this.age = age;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isActive() {
        return active;
    }

    public void changeStatus(boolean active) {
        this.active = active;
    }

    /** ENCAPSULATION: only a doctor with an active license who treats this patient may write. */
    public void assignDiagnosis(Doctor doctor, String diagnosis) {
        checkAuthorized(doctor);
        if (diagnosis == null || diagnosis.isBlank()) {
            throw new IllegalArgumentException("diagnosis cannot be empty");
        }
        this.diagnosis = diagnosis;
    }

    /** ENCAPSULATION: the same rule protects reading. */
    public String getDiagnosis(Doctor requester) {
        checkAuthorized(requester);
        return diagnosis;
    }

    /** ABSTRACTION: basic information, never the diagnosis. */
    public String basicInfo() {
        return recordNumber + " | " + name + " | " + age + " years | " + (active ? "active" : "inactive");
    }

    @Override
    public String toString() {
        return basicInfo();
    }

    private void checkAuthorized(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("a doctor is required");
        }
        if (!doctor.hasActiveLicense()) {
            throw new IllegalArgumentException("doctor " + doctor.getId() + " has a suspended license");
        }
        if (!doctor.isTreating(this)) {
            throw new IllegalArgumentException("doctor " + doctor.getId() + " does not treat patient " + recordNumber);
        }
    }
}
