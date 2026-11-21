// I — Interface Segregation Principle — BEFORE
//
// One "fat" interface: print, scan and fax.
// BasicPrinter only prints, but the interface FORCES it to write scan() and
// fax() anyway. It has nothing honest to put there, so it throws.
//
// Whoever receives an OfficeMachine believes it can scan. Sometimes it can't.
//
//   java BeforePrinter.java

import java.util.ArrayList;
import java.util.List;

public class BeforePrinter {

    public static void main(String[] args) {
        List<OfficeMachine> machines = new ArrayList<>();
        machines.add(new OfficeCenter());
        machines.add(new BasicPrinter());

        System.out.println("Printing the contract on every machine:");
        for (OfficeMachine machine : machines) {
            machine.print("contract.pdf");
        }

        System.out.println("Scanning the signed contract on every machine:");
        try {
            for (OfficeMachine machine : machines) {
                machine.scan("contract-signed");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("  CRASH: " + e.getMessage());
            System.out.println();
            System.out.println("BasicPrinter was forced to promise three things and can do one.");
        }
    }
}

/** Too much for one contract: not every machine does all three. */
interface OfficeMachine {

    void print(String document);

    void scan(String document);

    void fax(String document, String number);
}

class OfficeCenter implements OfficeMachine {

    @Override
    public void print(String document) {
        System.out.println("  [center]  printing " + document);
    }

    @Override
    public void scan(String document) {
        System.out.println("  [center]  scanning " + document);
    }

    @Override
    public void fax(String document, String number) {
        System.out.println("  [center]  faxing " + document + " to " + number);
    }
}

class BasicPrinter implements OfficeMachine {

    @Override
    public void print(String document) {
        System.out.println("  [basic]   printing " + document);
    }

    /** Forced by the interface. There is nothing true to write here. */
    @Override
    public void scan(String document) {
        throw new UnsupportedOperationException("BasicPrinter cannot scan");
    }

    /** Same. */
    @Override
    public void fax(String document, String number) {
        throw new UnsupportedOperationException("BasicPrinter cannot fax");
    }
}
