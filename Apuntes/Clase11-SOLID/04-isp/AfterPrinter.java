// I — Interface Segregation Principle — AFTER
//
// Three small interfaces, one per capability. Each class implements only
// what it really does, and each client asks only for what it really needs.
//
//   BasicPrinter   implements Printer
//   OfficeCenter   implements Printer, DocumentScanner, Fax
//   PhoneCamera    implements DocumentScanner     <- new, and it fits
//
// No empty methods, no UnsupportedOperationException.
//
//   java AfterPrinter.java

import java.util.ArrayList;
import java.util.List;

public class AfterPrinter {

    public static void main(String[] args) {
        OfficeCenter center = new OfficeCenter();
        BasicPrinter basic = new BasicPrinter();
        PhoneCamera phone = new PhoneCamera();

        List<Printer> printers = new ArrayList<>();
        printers.add(center);
        printers.add(basic);
        // printers.add(phone);   // does not compile: a phone camera is not a Printer

        List<DocumentScanner> scanners = new ArrayList<>();
        scanners.add(center);
        scanners.add(phone);
        // scanners.add(basic);   // does not compile: the mistake of the BEFORE version, caught early

        System.out.println("Printing the contract on every printer:");
        for (Printer printer : printers) {
            printer.print("contract.pdf");
        }

        System.out.println("Scanning the signed contract on every scanner:");
        for (DocumentScanner scanner : scanners) {
            scanner.scan("contract-signed");
        }

        System.out.println("Faxing it:");
        Office.sendByFax(center, "contract-signed", "2-2782222");

        System.out.println();
        System.out.println("Each list holds only machines that can do the job. Nothing to crash.");
    }
}

interface Printer {

    void print(String document);
}

interface DocumentScanner {

    void scan(String document);
}

interface Fax {

    void fax(String document, String number);
}

class Office {

    /** This client needs a Fax. It does not care what else the machine does. */
    static void sendByFax(Fax fax, String document, String number) {
        fax.fax(document, number);
    }
}

class BasicPrinter implements Printer {

    @Override
    public void print(String document) {
        System.out.println("  [basic]   printing " + document);
    }
}

/** A class can sign several small contracts. */
class OfficeCenter implements Printer, DocumentScanner, Fax {

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

/** With the fat interface, this class would have needed two fake methods. */
class PhoneCamera implements DocumentScanner {

    @Override
    public void scan(String document) {
        System.out.println("  [phone]   photographing " + document);
    }
}
