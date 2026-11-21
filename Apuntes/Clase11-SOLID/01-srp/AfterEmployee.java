// S — Single Responsibility Principle — AFTER
//
// One class per actor. Each one changes for ONE group of people:
//   Employee            the data, and nothing else
//   PayCalculator       Finance
//   HoursReporter       HR
//   EmployeeRepository  IT
//
// Finance and HR each keep their OWN idea of "regular hours". They look
// alike today, but they are two different rules that change for different
// reasons. Finance can move its limit again and HR will never notice.
//
//   java AfterEmployee.java

public class AfterEmployee {

    public static void main(String[] args) {
        Employee ana = new Employee("Ana Perez", 52, 30.0);

        PayCalculator finance = new PayCalculator();
        HoursReporter hr = new HoursReporter();
        EmployeeRepository it = new EmployeeRepository();

        System.out.println("Finance: pay for " + ana.getName() + " = Bs " + finance.calculatePay(ana));
        System.out.println("HR:      " + hr.reportHours(ana));
        System.out.println("IT:      " + it.save(ana));

        System.out.println();
        System.out.println("Same pay as before. The HR report is right again.");
        System.out.println("A change asked by Finance now touches PayCalculator, and only PayCalculator.");
    }
}

/** Only data. No actor asks Employee to change its rules, because it has none. */
final class Employee {

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

    int getHoursWorked() {
        return hoursWorked;
    }

    double getHourlyRate() {
        return hourlyRate;
    }
}

/** Actor: Finance. */
class PayCalculator {

    private static final int REGULAR_LIMIT = 40;
    private static final int OVERTIME_FACTOR = 2;

    double calculatePay(Employee employee) {
        int regular = Math.min(employee.getHoursWorked(), REGULAR_LIMIT);
        int overtime = employee.getHoursWorked() - regular;
        return regular * employee.getHourlyRate()
                + overtime * employee.getHourlyRate() * OVERTIME_FACTOR;
    }
}

/** Actor: HR. */
class HoursReporter {

    private static final int CONTRACT_HOURS = 44;

    String reportHours(Employee employee) {
        int regular = Math.min(employee.getHoursWorked(), CONTRACT_HOURS);
        String status = regular >= CONTRACT_HOURS ? "complete week" : "INCOMPLETE WEEK";
        return employee.getName() + ": " + regular + "/" + CONTRACT_HOURS + " regular hours -> " + status;
    }
}

/** Actor: IT. Pretends to be SQL. */
class EmployeeRepository {

    String save(Employee employee) {
        return "INSERT INTO employees VALUES ('" + employee.getName() + "', "
                + employee.getHoursWorked() + ", " + employee.getHourlyRate() + ")";
    }
}
