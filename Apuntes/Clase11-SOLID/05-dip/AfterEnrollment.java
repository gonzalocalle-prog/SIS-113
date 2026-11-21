// D — Dependency Inversion Principle — AFTER
//
// The policy declares WHAT it needs, as an interface, in its own words:
//
//   EnrollmentService ───> EnrollmentStore <─── MySqlEnrollmentStore
//   (the rule)             (interface)     <─── MemoryEnrollmentStore
//
// Look at the arrow of MySQL: it used to RECEIVE the dependency, now it
// points TOWARDS the policy's interface. That is the "inversion".
// The rule no longer knows MySQL exists. The detail obeys the rule.
//
//   java AfterEnrollment.java

import java.util.ArrayList;
import java.util.List;

public class AfterEnrollment {

    /** main is the edge of the program: the only place that names concrete classes. */
    public static void main(String[] args) {
        System.out.println("Production: the rule + MySQL");
        EnrollmentService real = new EnrollmentService(new MySqlEnrollmentStore());
        real.enroll("Ana", "SIS-113");
        real.enroll("Luis", "SIS-113");
        real.enroll("Rosa", "SIS-113");

        System.out.println();
        System.out.println("Check: the SAME rule, with no database at all");
        EnrollmentService inMemory = new EnrollmentService(new MemoryEnrollmentStore());
        check("first student is accepted", inMemory.enroll("Ana", "SIS-113"));
        check("second student is accepted", inMemory.enroll("Luis", "SIS-113"));
        check("third student is rejected: full", !inMemory.enroll("Rosa", "SIS-113"));
        check("another course still has room", inMemory.enroll("Rosa", "SIS-121"));
    }

    private static void check(String name, boolean condition) {
        System.out.println((condition ? "  PASS  " : "  FAIL  ") + name);
    }
}

/**
 * The abstraction. It belongs to the POLICY: it speaks of saving and
 * counting enrollments, not of rows, tables or SQL.
 */
interface EnrollmentStore {

    void save(String student, String course);

    int countFor(String course);
}

/** High level: the policy. It names no database, and has no "new" of a detail. */
class EnrollmentService {

    private static final int CAPACITY = 2;

    private final EnrollmentStore store;

    EnrollmentService(EnrollmentStore store) {
        if (store == null) {
            throw new IllegalArgumentException("store is required");
        }
        this.store = store;
    }

    boolean enroll(String student, String course) {
        if (store.countFor(course) >= CAPACITY) {
            System.out.println("  [rule]   rejected " + student + ": " + course + " is full");
            return false;
        }
        store.save(student, course);
        return true;
    }
}

/** Low level: a detail that implements the policy's interface. Pretends to be MySQL. */
class MySqlEnrollmentStore implements EnrollmentStore {

    private final List<String> rows = new ArrayList<>();

    MySqlEnrollmentStore() {
        System.out.println("  [mysql]  connecting to db.ucb.edu.bo:3306 ... connected");
    }

    @Override
    public void save(String student, String course) {
        rows.add(student + "," + course);
        System.out.println("  [mysql]  INSERT INTO enrollments VALUES ('" + student + "', '" + course + "')");
    }

    @Override
    public int countFor(String course) {
        int count = 0;
        for (String row : rows) {
            if (row.endsWith("," + course)) {
                count++;
            }
        }
        return count;
    }
}

/** Another detail: a plain list. Instant, silent, perfect for checks. */
class MemoryEnrollmentStore implements EnrollmentStore {

    private final List<String> courses = new ArrayList<>();

    @Override
    public void save(String student, String course) {
        courses.add(course);
    }

    @Override
    public int countFor(String course) {
        int count = 0;
        for (String saved : courses) {
            if (saved.equals(course)) {
                count++;
            }
        }
        return count;
    }
}
