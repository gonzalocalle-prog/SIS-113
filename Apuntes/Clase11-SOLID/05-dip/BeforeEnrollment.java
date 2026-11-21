// D — Dependency Inversion Principle — BEFORE
//
// EnrollmentService holds the RULE of the business: a course has a capacity.
// MySqlEnrollmentDb is a DETAIL: where the rows happen to be stored.
//
//   EnrollmentService ────────> MySqlEnrollmentDb
//   (the rule: important)       (the detail: replaceable)
//
// The important thing depends on the replaceable thing. So:
//   - changing the database means editing the class with the rule
//   - checking the rule "capacity is 2" needs a database connection
//
//   java BeforeEnrollment.java

import java.util.ArrayList;
import java.util.List;

public class BeforeEnrollment {

    public static void main(String[] args) {
        EnrollmentService service = new EnrollmentService();

        service.enroll("Ana", "SIS-113");
        service.enroll("Luis", "SIS-113");
        service.enroll("Rosa", "SIS-113");

        System.out.println();
        System.out.println("The rule works. But to check a rule of 3 lines,");
        System.out.println("we connected to MySQL and wrote real rows. There is no other way in.");
    }
}

/** High level: the policy. This is the code the university cares about. */
class EnrollmentService {

    private static final int CAPACITY = 2;

    // The policy creates the detail itself: it is welded to MySQL.
    private final MySqlEnrollmentDb db = new MySqlEnrollmentDb();

    boolean enroll(String student, String course) {
        if (db.countRows(course) >= CAPACITY) {
            System.out.println("  [rule]   rejected " + student + ": " + course + " is full");
            return false;
        }
        db.insertRow(student, course);
        return true;
    }
}

/** Low level: a detail. Pretends to be MySQL. */
class MySqlEnrollmentDb {

    private final List<String> rows = new ArrayList<>();

    MySqlEnrollmentDb() {
        System.out.println("  [mysql]  connecting to db.ucb.edu.bo:3306 ... connected");
    }

    void insertRow(String student, String course) {
        rows.add(student + "," + course);
        System.out.println("  [mysql]  INSERT INTO enrollments VALUES ('" + student + "', '" + course + "')");
    }

    int countRows(String course) {
        int count = 0;
        for (String row : rows) {
            if (row.endsWith("," + course)) {
                count++;
            }
        }
        return count;
    }
}
