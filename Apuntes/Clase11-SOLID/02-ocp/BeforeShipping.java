// O — Open/Closed Principle — BEFORE
//
// Every shipping method lives inside if/else chains, in TWO methods.
// To add a method you must OPEN and EDIT both chains, in code that already
// worked. "pickup" was added to cost() last week... and forgotten in days().
// The compiler said nothing: an if/else chain cannot know it is incomplete.
//
//   java BeforeShipping.java

public class BeforeShipping {

    public static void main(String[] args) {
        ShippingCalculator calculator = new ShippingCalculator();
        String[] methods = {"standard", "express", "pickup"};

        for (String method : methods) {
            System.out.println(method + ": Bs " + calculator.cost(method, 4.0)
                    + ", arrives in " + calculator.days(method) + " day(s)");
        }

        System.out.println();
        System.out.println("pickup arrives in -1 days: it was added in one chain and forgotten in the other.");
        System.out.println("With 10 methods and 5 chains, that is 50 places to keep in sync by hand.");
    }
}

class ShippingCalculator {

    double cost(String method, double kilos) {
        if (method.equals("standard")) {
            return 10 + 2 * kilos;
        } else if (method.equals("express")) {
            return 25 + 4 * kilos;
        } else if (method.equals("pickup")) {   // added last week
            return 0;
        }
        throw new IllegalArgumentException("unknown method: " + method);
    }

    int days(String method) {
        if (method.equals("standard")) {
            return 5;
        } else if (method.equals("express")) {
            return 1;
        }
        return -1;   // "pickup" should be here. Nobody remembered.
    }
}
