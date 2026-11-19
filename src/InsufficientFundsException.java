/**
 * Our own custom exception.
 * It extends Exception, which makes it a "checked" exception, so Java
 * forces anyone calling withdraw() to either catch it or declare it.
 */
public class InsufficientFundsException extends Exception {

    private static final long serialVersionUID = 1L;
    private final double shortBy;

    public InsufficientFundsException(String message, double shortBy) {
        super(message);
        this.shortBy = shortBy;
    }

    public double getShortBy() {
        return shortBy;
    }
}
