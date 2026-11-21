// O — Open/Closed Principle — AFTER
//
// OPEN for extension: a new shipping method is a new class.
// CLOSED for modification: Checkout and the existing methods never change.
//
// And the forgotten-branch bug is now impossible: a class that implements
// ShippingMethod without days() does not compile.
//
//   java AfterShipping.java

import java.util.ArrayList;
import java.util.List;

public class AfterShipping {

    public static void main(String[] args) {
        List<ShippingMethod> methods = new ArrayList<>();
        methods.add(new StandardShipping());
        methods.add(new ExpressShipping());
        methods.add(new PickupShipping());

        // Added today, as a NEW class. Not one line above or below was edited.
        methods.add(new DroneShipping());

        Checkout checkout = new Checkout();
        for (ShippingMethod method : methods) {
            System.out.println(checkout.quote(method, 4.0));
        }

        System.out.println();
        System.out.println("Checkout has no if, and does not know how many methods exist.");
    }
}

/** The contract: everything a shipping method must answer. */
interface ShippingMethod {

    String name();

    double cost(double kilos);

    int days();
}

/** Closed: this class was written once and has not been opened since. */
class Checkout {

    String quote(ShippingMethod method, double kilos) {
        return method.name() + ": Bs " + method.cost(kilos) + ", arrives in " + method.days() + " day(s)";
    }
}

class StandardShipping implements ShippingMethod {

    @Override
    public String name() {
        return "standard";
    }

    @Override
    public double cost(double kilos) {
        return 10 + 2 * kilos;
    }

    @Override
    public int days() {
        return 5;
    }
}

class ExpressShipping implements ShippingMethod {

    @Override
    public String name() {
        return "express";
    }

    @Override
    public double cost(double kilos) {
        return 25 + 4 * kilos;
    }

    @Override
    public int days() {
        return 1;
    }
}

class PickupShipping implements ShippingMethod {

    @Override
    public String name() {
        return "pickup";
    }

    @Override
    public double cost(double kilos) {
        return 0;
    }

    @Override
    public int days() {
        return 0;   // the compiler would not let us forget this one
    }
}

class DroneShipping implements ShippingMethod {

    @Override
    public String name() {
        return "drone";
    }

    @Override
    public double cost(double kilos) {
        return 40 + 10 * kilos;
    }

    @Override
    public int days() {
        return 0;
    }
}
