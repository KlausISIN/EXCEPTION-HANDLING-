# Exception Handling

A small Java console project demonstrating common exception-handling patterns:

- `try`/`catch`/`finally` and multiple catch blocks
- A custom checked exception for insufficient funds
- Handling checked I/O exceptions
- Try-with-resources and automatic cleanup

## Run

From the project directory, compile and run with a JDK:

```powershell
javac -d out src\Main.java src\BankAccount.java src\InsufficientFundsException.java
java -cp out Main
```
