/**
 * A doctor with a license and at most 20 assigned patients.
 *
 * ENCAPSULATION: the patient array is private and never returned. The only way
 * to add a patient is assignPatient(), which enforces every rule.
 */
public class Doctor {

    private static final int MAX_PATIENTS = 20;

    private final String id;
    private final String name;
    private final String specialty;
    private boolean licenseActive = true;
    private final Patient[] patients = new Patient[MAX_PATIENTS];
    private int patientCount = 0;

    public Doctor(String id, String name, String specialty) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("doctor id is required");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("doctor name is required");
        }
        if (specialty == null || specialty.isBlank()) {
            throw new IllegalArgumentException("specialty is required");
        }
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public boolean hasActiveLicense() {
        return licenseActive;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public void assignPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("patient is required");
        }
        if (!licenseActive) {
            throw new IllegalArgumentException("doctor " + id + " has a suspended license");
        }
        if (!patient.isActive()) {
            throw new IllegalArgumentException("patient " + patient.getRecordNumber() + " is inactive");
        }
        if (isTreating(patient)) {
            throw new IllegalArgumentException("patient " + patient.getRecordNumber() + " is already assigned");
        }
        if (patientCount == MAX_PATIENTS) {
            throw new IllegalArgumentException("doctor " + id + " already has " + MAX_PATIENTS + " patients");
        }
        patients[patientCount] = patient;
        patientCount++;
    }

    public boolean isTreating(Patient patient) {
        for (int i = 0; i < patientCount; i++) {
            if (patients[i] == patient) {
                return true;
            }
        }
        return false;
    }

    /** ABSTRACTION: the doctor delegates; Patient decides whether the doctor is allowed. */
    public void diagnose(Patient patient, String diagnosis) {
        if (patient == null) {
            throw new IllegalArgumentException("patient is required");
        }
        patient.assignDiagnosis(this, diagnosis);
    }

    public void suspendLicense() {
        licenseActive = false;
    }

    public void reactivateLicense() {
        licenseActive = true;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + specialty + " | " + (licenseActive ? "active" : "suspended");
    }
}
