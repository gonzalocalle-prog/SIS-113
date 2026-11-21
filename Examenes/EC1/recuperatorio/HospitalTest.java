import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Makeup exam (recuperatorio) test. DO NOT MODIFY THIS FILE.
 *
 * Copy it next to your classes and run:
 *   javac -d out *.java
 *   java -cp out HospitalTest
 *
 * If it does not compile, a class or method signature does not match the
 * statement. Each line prints PASS or FAIL; the last line is your score.
 * Sections 1 to 4 are the four cases of the original exercise.
 */
public class HospitalTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== HOSPITAL MANAGEMENT SYSTEM ===");

        run("1. Registration and validation", HospitalTest::registrationAndValidation);
        run("2. Medical operations", HospitalTest::medicalOperations);
        run("3. Security", HospitalTest::security);
        run("4. Limits and appointments", HospitalTest::limitsAndAppointments);
        run("5. Consultations", HospitalTest::consultations);
        run("6. Encapsulation is real", HospitalTest::encapsulationIsReal);

        System.out.println();
        System.out.println("Result: " + passed + "/" + (passed + failed) + " checks passed");
        if (failed > 0) {
            System.exit(1);
        }
    }

    // ── 1. Registration and validation ──────────────────────────────────────

    private static void registrationAndValidation() {
        HospitalSystem hospital = new HospitalSystem();

        Patient juan = hospital.registerPatient("H001", "Juan Perez", 35);
        Patient maria = hospital.registerPatient("H002", "Maria Garcia", 28);
        Patient invalid = hospital.registerPatient("H003", "Pedro Invalid", 150);

        Doctor rodriguez = hospital.registerDoctor("M001", "Dr. Rodriguez", "Cardiology");
        Doctor lopez = hospital.registerDoctor("M002", "Dr. Lopez", "Pediatrics");

        check("valid patients are registered", juan != null && maria != null);
        check("a new patient starts active", juan != null && juan.isActive());
        check("getters return the registered values", juan != null
                && juan.getRecordNumber().equals("H001") && juan.getName().equals("Juan Perez") && juan.getAge() == 35);
        check("age 150 is not registered (returns null)", invalid == null);
        check("total patients: 2", hospital.getPatientCount() == 2);
        check("total doctors: 2", rodriguez != null && lopez != null && hospital.getDoctorCount() == 2);
        check("a new doctor has an active license and no patients",
                rodriguez != null && rodriguez.hasActiveLicense() && rodriguez.getPatientCount() == 0);
        check("a repeated record number is not registered",
                hospital.registerPatient("H001", "Copy Cat", 40) == null && hospital.getPatientCount() == 2);
        check("a repeated doctor id is not registered",
                hospital.registerDoctor("M001", "Copy Cat", "Surgery") == null && hospital.getDoctorCount() == 2);
        check("a blank name is not registered", hospital.registerPatient("H004", " ", 30) == null);
        check("a blank specialty is not registered", hospital.registerDoctor("M003", "Dr. Nobody", "") == null);

        // The rules live in Patient and Doctor, not in HospitalSystem.
        check("age 0 is accepted", new Patient("H005", "Baby Doe", 0).getAge() == 0);
        check("age 120 is accepted", new Patient("H006", "Old Doe", 120).getAge() == 120);
        rejects("Patient itself rejects age 150", () -> new Patient("H007", "Bad Age", 150));
        rejects("Patient itself rejects age -1", () -> new Patient("H008", "Bad Age", -1));
        rejects("Doctor itself rejects a blank id", () -> new Doctor(" ", "No Id", "Surgery"));
    }

    // ── 2. Medical operations ───────────────────────────────────────────────

    private static void medicalOperations() {
        HospitalSystem hospital = new HospitalSystem();
        Patient carlos = hospital.registerPatient("H101", "Carlos Mendez", 45);
        Doctor silva = hospital.registerDoctor("M101", "Dr. Silva", "General Medicine");

        check("assignPatient returns true", silva.assignPatient(carlos));
        check("the doctor now treats the patient", silva.isTreating(carlos) && silva.getPatientCount() == 1);
        check("assigning the same patient twice returns false",
                !silva.assignPatient(carlos) && silva.getPatientCount() == 1);

        check("diagnosePatient returns true for an assigned patient",
                silva.diagnosePatient(carlos, "Hypertension"));
        check("the treating doctor reads the diagnosis back", "Hypertension".equals(carlos.getDiagnosis(silva)));

        Patient ana = hospital.registerPatient("H102", "Ana Torres", 32);
        check("diagnosing a patient who is not assigned returns false", !silva.diagnosePatient(ana, "Diabetes"));
        check("that failed diagnosis was not stored", silva.assignPatient(ana) && ana.getDiagnosis(silva) == null);

        Doctor other = hospital.registerDoctor("M102", "Dr. Other", "Oncology");
        check("another doctor cannot write through the patient", !carlos.assignDiagnosis(other, "Lupus"));
        check("another doctor cannot read the diagnosis (null)", carlos.getDiagnosis(other) == null);
        check("a null doctor cannot write", !carlos.assignDiagnosis(null, "Lupus"));
        check("an empty diagnosis is rejected", !silva.diagnosePatient(carlos, ""));
        check("the original diagnosis was kept", "Hypertension".equals(carlos.getDiagnosis(silva)));
    }

    // ── 3. Security ─────────────────────────────────────────────────────────

    private static void security() {
        HospitalSystem hospital = new HospitalSystem();
        Patient elena = hospital.registerPatient("H201", "Elena Ruiz", 29);
        Doctor morales = hospital.registerDoctor("M201", "Dr. Morales", "Neurology");
        morales.assignPatient(elena);
        morales.diagnosePatient(elena, "Migraine");

        String info = elena.basicInfo();
        System.out.println("        basicInfo(): " + info);
        check("basicInfo() shows the record number and the name", info.contains("H201") && info.contains("Elena Ruiz"));
        check("basicInfo() never shows the diagnosis", !info.contains("Migraine"));

        morales.suspendLicense();
        check("suspendLicense() suspends", !morales.hasActiveLicense());

        Patient newcomer = hospital.registerPatient("H202", "New Comer", 50);
        check("a suspended doctor cannot take patients (false)",
                !morales.assignPatient(newcomer) && !morales.isTreating(newcomer));
        check("a suspended doctor cannot diagnose", !morales.diagnosePatient(elena, "Epilepsy"));
        check("a suspended doctor cannot read the diagnosis (null)", elena.getDiagnosis(morales) == null);

        morales.reactivateLicense();
        check("reactivateLicense() restores the license", morales.hasActiveLicense());
        check("after reactivating, the diagnosis is still the original", "Migraine".equals(elena.getDiagnosis(morales)));
        check("after reactivating, the doctor takes patients again", morales.assignPatient(newcomer));
    }

    // ── 4. Limits and appointments ──────────────────────────────────────────

    private static void limitsAndAppointments() {
        HospitalSystem hospital = new HospitalSystem();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        Doctor busy = hospital.registerDoctor("M301", "Dr. Busy", "General Medicine");

        int accepted = 0;
        boolean extraRejected = true;
        for (int i = 1; i <= 22; i++) {
            Patient patient = hospital.registerPatient(String.format("H3%02d", i), "Patient " + i, 25 + i);
            boolean assigned = busy.assignPatient(patient);
            if (i <= 20 && assigned) {
                accepted++;
            }
            if (i > 20 && assigned) {
                extraRejected = false;
            }
        }
        check("the first 20 patients are assigned", accepted == 20);
        check("patients 21 and 22 are rejected", extraRejected);
        check("the doctor keeps exactly 20 patients", busy.getPatientCount() == 20);

        Patient inactive = hospital.registerPatient("H399", "Inactive Patient", 40);
        inactive.changeStatus(false);
        check("changeStatus(false) deactivates", !inactive.isActive());

        Doctor free = hospital.registerDoctor("M302", "Dr. Free", "General Medicine");
        check("an inactive patient cannot be assigned", !free.assignPatient(inactive));
        check("appointment with an inactive patient returns false",
                !hospital.scheduleAppointment(free, inactive, tomorrow));

        inactive.changeStatus(true);
        check("changeStatus(true) reactivates", inactive.isActive());
        check("appointment with a doctor who has 20 patients returns false",
                !hospital.scheduleAppointment(busy, inactive, tomorrow));
        check("no appointment in the past",
                !hospital.scheduleAppointment(free, inactive, LocalDateTime.now().minusDays(1)));
        check("no appointment without a date", !hospital.scheduleAppointment(free, inactive, null));

        Doctor stranger = new Doctor("M900", "Dr. Stranger", "Surgery");   // never registered
        check("no appointment with a doctor from outside the hospital",
                !hospital.scheduleAppointment(stranger, inactive, tomorrow));
        check("the rejected cases created no appointments", hospital.getAppointmentCount() == 0);

        check("a valid appointment returns true", hospital.scheduleAppointment(free, inactive, tomorrow));
        check("scheduling assigns the patient to the doctor", free.isTreating(inactive));
        check("the new appointment is pending",
                hospital.getAppointmentCount() == 1 && hospital.getPendingAppointmentCount() == 1);
    }

    // ── 5. Consultations ────────────────────────────────────────────────────

    private static void consultations() {
        HospitalSystem hospital = new HospitalSystem();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        Patient rosa = hospital.registerPatient("H401", "Rosa Mamani", 61);
        Patient quispe = hospital.registerPatient("H402", "Juan Quispe", 25);
        Doctor vargas = hospital.registerDoctor("M401", "Dr. Vargas", "Cardiology");

        check("no consultation without an appointment", !hospital.performConsultation(vargas, rosa, "Arrhythmia"));

        hospital.scheduleAppointment(vargas, rosa, tomorrow);
        check("the scheduled consultation is performed", hospital.performConsultation(vargas, rosa, "Arrhythmia"));
        check("the consultation stores the diagnosis", "Arrhythmia".equals(rosa.getDiagnosis(vargas)));
        check("the appointment is no longer pending",
                hospital.getAppointmentCount() == 1 && hospital.getPendingAppointmentCount() == 0);
        check("the same appointment cannot be performed twice", !hospital.performConsultation(vargas, rosa, "Again"));

        hospital.scheduleAppointment(vargas, quispe, tomorrow);
        vargas.suspendLicense();
        check("no consultation if the license was suspended after scheduling",
                !hospital.performConsultation(vargas, quispe, "Flu"));
        check("that appointment stays pending", hospital.getPendingAppointmentCount() == 1);

        vargas.reactivateLicense();
        quispe.changeStatus(false);
        check("no consultation if the patient became inactive after scheduling",
                !hospital.performConsultation(vargas, quispe, "Flu"));

        quispe.changeStatus(true);
        check("once everything is valid again, the consultation goes through",
                hospital.performConsultation(vargas, quispe, "Flu") && hospital.getPendingAppointmentCount() == 0);
    }

    // ── 6. Encapsulation, checked by the compiler's own metadata ───────────

    private static void encapsulationIsReal() {
        Class<?>[] classes = {Patient.class, Doctor.class, HospitalSystem.class};
        for (Class<?> c : classes) {
            boolean allPrivate = true;
            for (Field f : c.getDeclaredFields()) {
                if (!Modifier.isPrivate(f.getModifiers())) {
                    allPrivate = false;
                    System.out.println("        field not private: " + c.getSimpleName() + "." + f.getName());
                }
            }
            check(c.getSimpleName() + ": every field is private", allPrivate);

            boolean noCollections = true;
            boolean noSetters = true;
            for (Method m : c.getDeclaredMethods()) {
                if (!Modifier.isPublic(m.getModifiers())) {
                    continue;
                }
                Class<?> type = m.getReturnType();
                if (type.isArray() || Iterable.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type)) {
                    noCollections = false;
                    System.out.println("        public method returns a collection: " + c.getSimpleName() + "." + m.getName());
                }
                if (m.getName().startsWith("set")) {
                    noSetters = false;
                    System.out.println("        public setter: " + c.getSimpleName() + "." + m.getName());
                }
            }
            check(c.getSimpleName() + ": no public method hands out an internal list or array", noCollections);
            check(c.getSimpleName() + ": no public setters", noSetters);
        }

        boolean openDiagnosis = false;
        for (Method m : Patient.class.getDeclaredMethods()) {
            if (Modifier.isPublic(m.getModifiers()) && m.getName().equals("getDiagnosis") && m.getParameterCount() == 0) {
                openDiagnosis = true;
            }
        }
        check("Patient: there is no getDiagnosis() without a doctor", !openDiagnosis);
    }

    // ── Helpers ─────────────────────────────────────────────────────────────

    /** Runs one section. If the student's code crashes, the section counts one FAIL and the test goes on. */
    private static void run(String title, Runnable section) {
        System.out.println();
        System.out.println("--- " + title + " ---");
        try {
            section.run();
        } catch (RuntimeException e) {
            failed++;
            System.out.println("  FAIL  the section crashed: " + e);
        }
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("  PASS  " + name);
        } else {
            failed++;
            System.out.println("  FAIL  " + name);
        }
    }

    private static void rejects(String name, Runnable action) {
        try {
            action.run();
            failed++;
            System.out.println("  FAIL  " + name + " (no exception was thrown)");
        } catch (IllegalArgumentException e) {
            passed++;
            System.out.println("  PASS  " + name + " -> " + e.getMessage());
        } catch (RuntimeException e) {
            failed++;
            System.out.println("  FAIL  " + name + " (threw " + e.getClass().getSimpleName()
                    + ", expected IllegalArgumentException)");
        }
    }
}
