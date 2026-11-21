// L — Liskov Substitution Principle — AFTER
//
// The base class now promises only what is true for ALL its children:
//
//                  Account            deposit, getBalance
//            ┌────────┴──────────┐
//   WithdrawableAccount    FixedTermAccount
//    (adds withdraw)        (never had withdraw)
//            │
//      SavingsAccount
//
// Bank.chargeMonthlyFee() asks for WithdrawableAccount. Passing it a
// fixed-term account is no longer a crash at runtime: it does not compile.
//
//   java AfterAccounts.java

import java.util.ArrayList;
import java.util.List;

public class AfterAccounts {

    public static void main(String[] args) {
        SavingsAccount ana = new SavingsAccount("Ana", 500);
        FixedTermAccount luis = new FixedTermAccount("Luis", 2000);
        SavingsAccount rosa = new SavingsAccount("Rosa", 300);

        List<WithdrawableAccount> withFee = new ArrayList<>();
        withFee.add(ana);
        withFee.add(rosa);
        // withFee.add(luis);   // does not compile: a FixedTermAccount is not a WithdrawableAccount

        List<Account> all = new ArrayList<>();
        all.add(ana);
        all.add(luis);
        all.add(rosa);

        System.out.println("Charging the Bs 10 monthly fee:");
        Bank.chargeMonthlyFee(withFee);

        System.out.println("Paying a Bs 5 loyalty bonus to EVERY account:");
        Bank.payBonus(all);

        System.out.println();
        System.out.println("No crash, no instanceof. Every child keeps every promise of its parent.");
    }
}

/** Only what every account can do. */
abstract class Account {

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

    /** protected: only the children that allow withdrawals use it. */
    protected void debit(double amount) {
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

/** The promise "you can withdraw" lives here, one level down. */
abstract class WithdrawableAccount extends Account {

    WithdrawableAccount(String owner, double balance) {
        super(owner, balance);
    }

    void withdraw(double amount) {
        debit(amount);
    }
}

class SavingsAccount extends WithdrawableAccount {

    SavingsAccount(String owner, double balance) {
        super(owner, balance);
    }
}

/** Still an Account. It never promised withdrawals, so it breaks nothing. */
class FixedTermAccount extends Account {

    FixedTermAccount(String owner, double balance) {
        super(owner, balance);
    }
}

class Bank {

    static void chargeMonthlyFee(List<WithdrawableAccount> accounts) {
        for (WithdrawableAccount account : accounts) {
            account.withdraw(10);
            System.out.println("  " + account.getOwner() + " charged, balance Bs " + account.getBalance());
        }
    }

    /** Works with any Account, today's and next year's. */
    static void payBonus(List<Account> accounts) {
        for (Account account : accounts) {
            account.deposit(5);
            System.out.println("  " + account.getOwner() + " balance Bs " + account.getBalance());
        }
    }
}
