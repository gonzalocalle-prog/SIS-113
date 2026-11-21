// S — Single Responsibility Principle — BEFORE
//
// One class that answers to THREE different groups of people (actors):
//   Finance  -> calculatePay()
//   HR       -> reportHours()
//   IT       -> save()
//
// The trap is regularHours(): a small helper SHARED by Finance and HR.
// Last month it was min(hoursWorked, 44). Finance asked for 40.
// The developer changed it... and nobody told HR.
//
//   java BeforeEmployee.java

public class BeforeEmployee {

    public static void main(String[] args) {
        Employee ana = new Employee("Ana Perez", 52, 30.0);

        System.out.println("Finance: pay for " + ana.getName() + " = Bs " + ana.calculatePay());
        System.out.println("HR:      " + ana.reportHours());
        System.out.println("IT:      " + ana.save());

        System.out.println();
        System.out.println("The pay is right: 40 regular hours + 12 overtime hours.");
        System.out.println("The HR report is WRONG: Ana worked 52 hours, and it says INCOMPLETE.");
        System.out.println("Finance asked for a change. HR got a bug. Nobody touched reportHours().");
    }
}

class Employee {

    private static final int CONTRACT_HOURS = 44;

    private final String name;
    private final int hoursWorked;
    private final double hourlyRate;

    Employee(String name, int hoursWorked, double hourlyRate) {
        this.name = name;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    String getName() {
        return name;
    }

    /** Shared by calculatePay() AND reportHours(). Changed for Finance: 44 -> 40. */
    private int regularHours() {
        return Math.min(hoursWorked, 40);
    }

    /** Actor: Finance. Overtime is paid double. */
    double calculatePay() {
        int regular = regularHours();
        int overtime = hoursWorked - regular;
        return regular * hourlyRate + overtime * hourlyRate * 2;
    }

    /** Actor: HR. Did the employee complete the hours of the contract? */
    String reportHours() {
        int regular = regularHours();
        String status = regular >= CONTRACT_HOURS ? "complete week" : "INCOMPLETE WEEK";
        return name + ": " + regular + "/" + CONTRACT_HOURS + " regular hours -> " + status;
    }

    /** Actor: IT. Pretends to be SQL. */
    String save() {
        return "INSERT INTO employees VALUES ('" + name + "', " + hoursWorked + ", " + hourlyRate + ")";
    }
}
