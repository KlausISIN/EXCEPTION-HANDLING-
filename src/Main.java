import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Run this file. It walks through the most common exception handling
 * patterns one at a time, and prints what's going on.
 */
public class Main {

    public static void main(String[] args) {
        demo1_basicTryCatch();
        demo2_multipleCatchBlocks();
        demo3_customException();
        demo4_checkedException();
        demo5_tryWithResources();

        System.out.println("\nAll done. The program survived every single one of those errors.");
    }

    // 1. The basics: try something risky, catch it if it blows up.
    @SuppressWarnings("divzero")
    private static void demo1_basicTryCatch() {
        printHeader("1. Basic try / catch / finally");

        try {
            int result = 10 / 0; // boom
            System.out.println("You will never see this: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Caught it: " + e.getMessage());
        } finally {
            // finally ALWAYS runs, whether there was an error or not.
            System.out.println("finally block says hi (I always run).");
        }
    }

    // 2. Different problems, different reactions.
    private static void demo2_multipleCatchBlocks() {
        printHeader("2. Multiple catch blocks");

        String[] inputs = {"42", "forty-two", null};

        for (String input : inputs) {
            try {
                int number = Integer.parseInt(input);
                System.out.println("Parsed fine: " + number);
            } catch (NumberFormatException e) {
                System.out.println("'" + input + "' is not a number.");
            } catch (Exception e) {
                // Keep the general catch LAST, otherwise it swallows the specific ones.
                System.out.println("Something else went wrong: " + e);
            }
        }
    }

    // 3. Using our own exception from BankAccount.
    private static void demo3_customException() {
        printHeader("3. Custom exception");

        BankAccount account = new BankAccount("Sam", 100);

        try {
            account.withdraw(30);
            System.out.println("Withdrew 30. Balance is now " + account.getBalance());

            account.withdraw(500); // too much
            System.out.println("Not reached.");
        } catch (InsufficientFundsException e) {
            System.out.println("Declined: " + e.getMessage());
            System.out.println("You are short by: " + e.getShortBy());
        }

        // Unchecked exception example
        try {
            account.deposit(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Bad deposit: " + e.getMessage());
        }
    }

    // 4. Checked exceptions: Java won't compile this without a try/catch (or a throws).
    private static void demo4_checkedException() {
        printHeader("4. Checked exception (missing file)");

        try (BufferedReader reader =
                     new BufferedReader(new FileReader("this-file-does-not-exist.txt"))) {
            String line = reader.readLine();
            System.out.println(line);
        } catch (IOException e) {
            System.out.println("Could not read the file: " + e.getMessage());
        }
    }

    // 5. try-with-resources: Java closes the resource for you, even on error.
    private static void demo5_tryWithResources() {
        printHeader("5. try-with-resources");

        try (FakeConnection connection = new FakeConnection()) {
            connection.send("hello");
            connection.send(null); // this one fails
        } catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        // Notice "Connection closed" got printed anyway. No finally needed.
    }

    private static void printHeader(String title) {
        System.out.println("\n--- " + title + " ---");
    }

    // A pretend resource. Anything that implements AutoCloseable works in try-with-resources.
    private static class FakeConnection implements AutoCloseable {

        FakeConnection() {
            System.out.println("Connection opened");
        }

        void send(String message) {
            if (message == null) {
                throw new IllegalStateException("Can't send an empty message");
            }
            System.out.println("Sent: " + message);
        }

        @Override
        public void close() {
            System.out.println("Connection closed");
        }
    }
}
