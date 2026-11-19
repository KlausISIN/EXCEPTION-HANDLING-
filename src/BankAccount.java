/**
 * A tiny bank account, just so we have something that can go wrong.
 */
public class BankAccount {

    private final String owner;
    private double balance;

    public BankAccount(String owner, double startingBalance) {
        if (owner == null || owner.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner must not be empty.");
        }
        if (!Double.isFinite(startingBalance) || startingBalance < 0) {
            throw new IllegalArgumentException(
                    "Starting balance must be finite and non-negative, got: " + startingBalance);
        }
        this.owner = owner;
        this.balance = startingBalance;
    }

    public void deposit(double amount) {
        // Unchecked exception: bad input is a programmer mistake,
        // so we don't force callers to catch it.
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException(
                    "Deposit must be finite and more than zero, got: " + amount);
        }
        double newBalance = balance + amount;
        if (!Double.isFinite(newBalance)) {
            throw new IllegalArgumentException("Deposit would make the balance invalid.");
        }
        balance = newBalance;
    }

    // "throws" tells callers: heads up, this method can fail in this way.
    public void withdraw(double amount) throws InsufficientFundsException {
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException(
                    "Withdrawal must be finite and more than zero, got: " + amount);
        }
        if (amount > balance) {
            throw new InsufficientFundsException(
                    owner + " tried to withdraw " + amount + " but only has " + balance,
                    amount - balance);
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
