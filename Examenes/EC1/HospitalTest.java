import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Exam test — Part 2. DO NOT MODIFY THIS FILE.
 *
 * Copy it next to your four classes and run:
 *   javac -d out *.java
 *   java -cp out HospitalTest
 *
 * If it does not compile, a class or method signature does not match the
 * statement. Each line prints PASS or FAIL; the last line is your score.
 */
public class HospitalTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        patientRules();
        diagnosisIsProtected();
        doctorRules();
        hospitalFlow();
        encapsulationIsReal();

        System.out.println();
        System.out.println("Result: " + passed + "/" + (passed + failed) + " checks passed");
        if (failed > 0) {
            System.exit(1);
        }
    }

    // ── 1. Patient ──────────────────────────────────────────────────────────

    private static void patientRules() {
        section("1. Patient");
        Patient ana = new Patient("HC-001", "Ana Perez", 34);
        check("valid patient is created active", ana.isActive());
        check("getters return the constructor values",
                ana.getRecordNumber().equals("HC-001") && ana.getName().equals("Ana Perez") && ana.getAge() == 34);
        check("age 0 is accepted", new Patient("HC-002", "Baby Doe", 0).getAge() == 0);
        check("age 120 is accepted", new Patient("HC-003", "Old Doe", 120).getAge() == 120);
        rejects("age -1 is rejected", () -> new Patient("HC-004", "Bad Age", -1));
        rejects("age 121 is rejected", () -> new Patient("HC-005", "Bad Age", 121));
        rejects("blank name is rejected", () -> new Patient("HC-006", " ", 30));
        rejects("blank record number is rejected", () -> new Patient("", "No Record", 30));

        ana.changeStatus(false);
        check("changeStatus(false) deactivates", !ana.isActive());
        ana.changeStatus(true);
        check("changeStatus(true) reactivates", ana.isActive());
        check("basicInfo() shows the name", ana.basicInfo().contains("Ana Perez"));
    }

    // ── 2. Diagnosis: sensitive data ────────────────────────────────────────

    private static void diagnosisIsProtected() {
        section("2. Diagnosis is protected");
        Patient luis = new Patient("HC-010", "Luis Rojas", 50);
        Doctor house = new Doctor("MD-01", "Gregory House", "Diagnostics");
        Doctor wilson = new Doctor("MD-02", "James Wilson", "Oncology");
        house.assignPatient(luis);

        rejects("a doctor who does not treat the patient cannot diagnose",
                () -> wilson.diagnose(luis, "Lupus"));
        rejects("a doctor cannot write through the patient either",
                () -> luis.assignDiagnosis(wilson, "Lupus"));
        rejects("a null doctor cannot diagnose", () -> luis.assignDiagnosis(null, "Lupus"));
        rejects("an empty diagnosis is rejected", () -> house.diagnose(luis, ""));

        house.diagnose(luis, "Sarcoidosis");
        check("the treating doctor can diagnose and read it back",
                luis.getDiagnosis(house).equals("Sarcoidosis"));
        rejects("another doctor cannot read the diagnosis", () -> luis.getDiagnosis(wilson));
        check("basicInfo() never shows the diagnosis", !luis.basicInfo().contains("Sarcoidosis"));

        house.suspendLicense();
        rejects("a suspended doctor cannot diagnose", () -> house.diagnose(luis, "Lupus"));
        rejects("a suspended doctor cannot read the diagnosis", () -> luis.getDiagnosis(house));
        house.reactivateLicense();
        check("after reactivating, the doctor reads it again", luis.getDiagnosis(house).equals("Sarcoidosis"));
    }

    // ── 3. Doctor ───────────────────────────────────────────────────────────

    private static void doctorRules() {
        section("3. Doctor");
        Doctor grey = new Doctor("MD-03", "Meredith Grey", "Surgery");
        check("valid doctor starts with an active license and no patients",
                grey.hasActiveLicense() && grey.getPatientCount() == 0);
        rejects("blank doctor id is rejected", () -> new Doctor(" ", "No Id", "Surgery"));

        Patient first = new Patient("HC-100", "Patient 0", 40);
        grey.assignPatient(first);
        check("assignPatient adds one patient", grey.getPatientCount() == 1 && grey.isTreating(first));
        rejects("the same patient cannot be assigned twice", () -> grey.assignPatient(first));

        Patient inactive = new Patient("HC-199", "Inactive One", 40);
        inactive.changeStatus(false);
        rejects("an inactive patient cannot be assigned", () -> grey.assignPatient(inactive));

        for (int i = 1; i < 20; i++) {
            grey.assignPatient(new Patient("HC-1" + String.format("%02d", i), "Patient " + i, 40));
        }
        check("20 patients fit", grey.getPatientCount() == 20);
        rejects("the 21st patient is rejected", () -> grey.assignPatient(new Patient("HC-121", "One Too Many", 40)));
        check("the rejected patient did not get in", grey.getPatientCount() == 20);

        Doctor yang = new Doctor("MD-04", "Cristina Yang", "Cardiology");
        yang.suspendLicense();
        check("suspendLicense() suspends", !yang.hasActiveLicense());
        rejects("a suspended doctor cannot take patients", () -> yang.assignPatient(new Patient("HC-200", "Someone", 30)));
        yang.reactivateLicense();
        yang.assignPatient(new Patient("HC-201", "Someone Else", 30));
        check("after reactivateLicense() the doctor takes patients again", yang.getPatientCount() == 1);
    }

    // ── 4. HospitalSystem ───────────────────────────────────────────────────

    private static void hospitalFlow() {
        section("4. HospitalSystem");
        HospitalSystem hospital = new HospitalSystem();
        hospital.registerPatient(new Patient("HC-300", "Rosa Mamani", 61));
        hospital.registerPatient(new Patient("HC-301", "Juan Quispe", 25));
        hospital.registerDoctor(new Doctor("MD-10", "Elena Vargas", "Cardiology"));
        hospital.registerDoctor(new Doctor("MD-11", "Pablo Choque", "Pediatrics"));
        check("registers patients and doctors", hospital.getPatientCount() == 2 && hospital.getDoctorCount() == 2);
        rejects("a duplicated record number is rejected",
                () -> hospital.registerPatient(new Patient("HC-300", "Copy Cat", 40)));
        rejects("a duplicated doctor id is rejected",
                () -> hospital.registerDoctor(new Doctor("MD-10", "Copy Cat", "Surgery")));
        rejects("an unknown patient is rejected", () -> hospital.findPatient("HC-999"));
        rejects("an unknown doctor is rejected", () -> hospital.findDoctor("MD-99"));

        Appointment first = hospital.scheduleAppointment("HC-300", "MD-10", "2026-10-21 09:00");
        check("the first appointment gets number 1", first.getNumber() == 1);
        check("scheduling assigns the patient to the doctor",
                hospital.findDoctor("MD-10").isTreating(hospital.findPatient("HC-300")));
        check("a new appointment is pending", !first.isCompleted() && hospital.getPendingAppointmentCount() == 1);
        rejects("an appointment without date is rejected",
                () -> hospital.scheduleAppointment("HC-300", "MD-10", ""));

        hospital.findPatient("HC-301").changeStatus(false);
        rejects("an inactive patient cannot get an appointment",
                () -> hospital.scheduleAppointment("HC-301", "MD-11", "2026-10-21 10:00"));
        hospital.findPatient("HC-301").changeStatus(true);

        hospital.findDoctor("MD-11").suspendLicense();
        rejects("a suspended doctor cannot get an appointment",
                () -> hospital.scheduleAppointment("HC-301", "MD-11", "2026-10-21 10:00"));
        hospital.findDoctor("MD-11").reactivateLicense();

        hospital.performConsultation(1, "Hypertension");
        check("the consultation completes the appointment", first.isCompleted());
        check("the consultation stores the diagnosis",
                hospital.findPatient("HC-300").getDiagnosis(hospital.findDoctor("MD-10")).equals("Hypertension"));
        rejects("an appointment cannot be performed twice", () -> hospital.performConsultation(1, "Again"));
        rejects("an unknown appointment is rejected", () -> hospital.performConsultation(42, "Nothing"));

        Appointment second = hospital.scheduleAppointment("HC-301", "MD-11", "2026-10-22 11:00");
        hospital.findDoctor("MD-11").suspendLicense();
        rejects("no consultation if the license was suspended after scheduling",
                () -> hospital.performConsultation(second.getNumber(), "Flu"));
        check("that appointment stays pending", !second.isCompleted() && hospital.getPendingAppointmentCount() == 1);
    }

    // ── 5. Encapsulation, checked by the compiler's own metadata ───────────

    private static void encapsulationIsReal() {
        section("5. Encapsulation is real");
        Class<?>[] classes = {Patient.class, Doctor.class, Appointment.class, HospitalSystem.class};
        for (Class<?> c : classes) {
            boolean allPrivate = true;
            for (Field f : c.getDeclaredFields()) {
                if (!Modifier.isPrivate(f.getModifiers())) {
                    allPrivate = false;
                    System.out.println("       field not private: " + c.getSimpleName() + "." + f.getName());
                }
            }
            check(c.getSimpleName() + ": every field is private", allPrivate);

            boolean noArrays = true;
            boolean noSetters = true;
            for (Method m : c.getDeclaredMethods()) {
                if (!Modifier.isPublic(m.getModifiers())) {
                    continue;
                }
                if (m.getReturnType().isArray()) {
                    noArrays = false;
                    System.out.println("       public method returns an array: " + c.getSimpleName() + "." + m.getName());
                }
                if (m.getName().startsWith("set")) {
                    noSetters = false;
                    System.out.println("       public setter: " + c.getSimpleName() + "." + m.getName());
                }
            }
            check(c.getSimpleName() + ": no public method hands out an internal array", noArrays);
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

    private static void section(String title) {
        System.out.println();
        System.out.println("== " + title + " ==");
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
