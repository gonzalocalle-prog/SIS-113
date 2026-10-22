// Bug 3 — it compiles, then dies. Read the exception message: Java tells you
// exactly which variable is the problem.
//   java Bug3MissingNew.java

public class Bug3MissingNew {

    public static void main(String[] args) {
        Dashboard dashboard = new Dashboard();
        dashboard.receive("line 1");
        dashboard.receive("line 2");
        System.out.println("lines received: " + dashboard.getReceived());   // expected: 2
    }
}

class Counter {

    private int count = 0;

    void increment() {
        count++;
    }

    int getCount() {
        return count;
    }
}

class Dashboard {

    private Counter counter;

    void receive(String line) {
        counter.increment();
    }

    int getReceived() {
        return counter.getCount();
    }
}
