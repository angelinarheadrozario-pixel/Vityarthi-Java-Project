import java.time.LocalDateTime;

/**
 * Simple transaction record (type, amount, timestamp).
 */
public class Transaction {
    private String type; // "DEPOSIT" or "WITHDRAW"
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(String type, double amount, LocalDateTime timestamp) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return timestamp.toString() + " - " + type + " - " + amount;
    }
}
