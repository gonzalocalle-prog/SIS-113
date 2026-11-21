// L — Liskov Substitution Principle — BEFORE
//
// Account promises: "you can withdraw from me".
// FixedTermAccount extends Account... and breaks that promise.
//
// Bank.chargeMonthlyFee() is CORRECT code: it only uses what Account offers.
// It still crashes, because one of the "accounts" is not really substitutable.
// It compiles. The error appears at runtime, in the middle of the list.
//
//   java BeforeAccounts.java

import java.util.ArrayList;
import java.util.List;

public class BeforeAccounts {

    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new SavingsAccount("Ana", 500));
        accounts.add(new FixedTermAccount("Luis", 2000));
        accounts.add(new SavingsAccount("Rosa", 300));

        System.out.println("Charging the Bs 10 monthly fee to every account:");
        try {
            Bank.chargeMonthlyFee(accounts);
        } catch (UnsupportedOperationException e) {
            System.out.println("  CRASH: " + e.getMessage());
            System.out.println();
            System.out.println("Ana was charged. Luis broke the loop. Rosa was never charged.");
            System.out.println("The bank's code was right. The inheritance was wrong.");
        }
    }
}

class Account {

    private final String owner;
    private double balance;

    Account(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        balance += amount;
    }

    /** The promise: any amount between 0 and the balance can be withdrawn. */
    void withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            throw new IllegalArgumentException("invalid amount: " + amount);
        }
        balance -= amount;
    }

    String getOwner() {
        return owner;
    }

    double getBalance() {
        return balance;
    }
}

class SavingsAccount extends Account {

    SavingsAccount(String owner, double balance) {
        super(owner, balance);
    }
}

/** "A fixed-term deposit IS an account, so let's inherit." */
class FixedTermAccount extends Account {

    FixedTermAccount(String owner, double balance) {
        super(owner, balance);
    }

    /** Breaks the promise of the parent: the caller never expected this. */
    @Override
    void withdraw(double amount) {
        throw new UnsupportedOperationException("fixed-term account of " + getOwner() + ": no withdrawals");
    }
}

class Bank {

    /** Written against Account. It should work with ANY Account. */
    static void chargeMonthlyFee(List<Account> accounts) {
        for (Account account : accounts) {
            account.withdraw(10);
            System.out.println("  " + account.getOwner() + " charged, balance Bs " + account.getBalance());
        }
    }
}
