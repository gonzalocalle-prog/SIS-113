// Bug 4 — it compiles and runs, but the count never moves.
//   java Bug4LocalHidesField.java

public class Bug4LocalHidesField {

    public static void main(String[] args) {
        AlertCounter alerts = new AlertCounter();
        alerts.register();
        alerts.register();
        alerts.register();
        System.out.println("alerts: " + alerts.getCount());   // expected: alerts: 3
    }
}

class AlertCounter {

    private int count = 0;

    void register() {
        int count = 0;
        count++;
    }

    int getCount() {
        return count;
    }
}
