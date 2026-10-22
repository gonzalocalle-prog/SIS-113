/**
 * The whole flow: registers patients and doctors, schedules appointments and
 * performs consultations.
 *
 * ABSTRACTION: callers use record numbers and ids. They never see the arrays,
 * the counters, or how a lookup is done.
 */
public class HospitalSystem {

    private static final int MAX_PATIENTS = 500;
    private static final int MAX_DOCTORS = 50;
    private static final int MAX_APPOINTMENTS = 1000;

    private final Patient[] patients = new Patient[MAX_PATIENTS];
    private final Doctor[] doctors = new Doctor[MAX_DOCTORS];
    private final Appointment[] appointments = new Appointment[MAX_APPOINTMENTS];
    private int patientCount = 0;
    private int doctorCount = 0;
    private int appointmentCount = 0;

    public void registerPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("patient is required");
        }
        if (lookupPatient(patient.getRecordNumber()) != null) {
            throw new IllegalArgumentException("record number " + patient.getRecordNumber() + " already exists");
        }
        if (patientCount == MAX_PATIENTS) {
            throw new IllegalArgumentException("patient registry is full");
        }
        patients[patientCount] = patient;
        patientCount++;
    }

    public void registerDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("doctor is required");
        }
        if (lookupDoctor(doctor.getId()) != null) {
            throw new IllegalArgumentException("doctor id " + doctor.getId() + " already exists");
        }
        if (doctorCount == MAX_DOCTORS) {
            throw new IllegalArgumentException("doctor registry is full");
        }
        doctors[doctorCount] = doctor;
        doctorCount++;
    }

    public Patient findPatient(String recordNumber) {
        Patient patient = lookupPatient(recordNumber);
        if (patient == null) {
            throw new IllegalArgumentException("no patient with record number " + recordNumber);
        }
        return patient;
    }

    public Doctor findDoctor(String id) {
        Doctor doctor = lookupDoctor(id);
        if (doctor == null) {
            throw new IllegalArgumentException("no doctor with id " + id);
        }
        return doctor;
    }

    public Appointment scheduleAppointment(String recordNumber, String doctorId, String dateTime) {
        Patient patient = findPatient(recordNumber);
        Doctor doctor = findDoctor(doctorId);
        if (appointmentCount == MAX_APPOINTMENTS) {
            throw new IllegalArgumentException("appointment book is full");
        }
        if (!doctor.isTreating(patient)) {
            doctor.assignPatient(patient);
        }
        Appointment appointment = new Appointment(appointmentCount + 1, patient, doctor, dateTime);
        appointments[appointmentCount] = appointment;
        appointmentCount++;
        return appointment;
    }

    public void performConsultation(int appointmentNumber, String diagnosis) {
        if (appointmentNumber < 1 || appointmentNumber > appointmentCount) {
            throw new IllegalArgumentException("no appointment number " + appointmentNumber);
        }
        appointments[appointmentNumber - 1].perform(diagnosis);
    }

    public int getPatientCount() {
        return patientCount;
    }

    public int getDoctorCount() {
        return doctorCount;
    }

    public int getAppointmentCount() {
        return appointmentCount;
    }

    public int getPendingAppointmentCount() {
        int pending = 0;
        for (int i = 0; i < appointmentCount; i++) {
            if (!appointments[i].isCompleted()) {
                pending++;
            }
        }
        return pending;
    }

    private Patient lookupPatient(String recordNumber) {
        for (int i = 0; i < patientCount; i++) {
            if (patients[i].getRecordNumber().equals(recordNumber)) {
                return patients[i];
            }
        }
        return null;
    }

    private Doctor lookupDoctor(String id) {
        for (int i = 0; i < doctorCount; i++) {
            if (doctors[i].getId().equals(id)) {
                return doctors[i];
            }
        }
        return null;
    }
}
