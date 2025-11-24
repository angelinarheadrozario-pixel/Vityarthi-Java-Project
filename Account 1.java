import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Represents a bank account. PINs are stored as SHA-256 hex hashes for improved security.
 */
public class Account {
    private String accountNumber;
    private String accountHolderName;
    private String hashedPin;
    private double balance;
    private AccountType accountType;
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String accountNumber, String accountHolderName, String pin, AccountType accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        setPin(pin);
        this.accountType = accountType;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolderName() { return accountHolderName; }
    public void setAccountHolderName(String name) { this.accountHolderName = name; }
    /**
     * Returns the stored hashed PIN (hex). Use {@link #checkPin(String)} to verify.
     */
    public String getHashedPin() { return hashedPin; }

    /**
     * Set the account PIN; the value is hashed with SHA-256 before storing.
     */
    public void setPin(String pin) { this.hashedPin = hashPin(pin); }

    /**
     * Check a plain-text PIN against the stored hash.
     */
    public boolean checkPin(String plainPin) {
        if (plainPin == null) return false;
        String h = hashPin(plainPin);
        return h.equalsIgnoreCase(this.hashedPin);
    }
    public double getBalance() { return balance; }
    public AccountType getAccountType() { return accountType; }

    public void deposit(double amount) {
        if (amount <= 0) return;
        this.balance += amount;
        transactions.add(new Transaction("DEPOSIT", amount, LocalDateTime.now()));
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) return;
        if (amount > balance) throw new InsufficientFundsException("Insufficient balance");
        this.balance -= amount;
        transactions.add(new Transaction("WITHDRAW", amount, LocalDateTime.now()));
    }

    public List<Transaction> getTransactions() { return transactions; }

    // CSV serialization: accountNumber;holderName;pin;balance;accountType;txnSerialized
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
                sb.append(accountNumber).append(";")
                    .append(escapeField(accountHolderName)).append(";")
                    .append(hashedPin).append(";")
          .append(balance).append(";")
          .append(accountType.name()).append(";");

        for (Transaction t : transactions) {
            sb.append(t.getType()).append("|")
              .append(t.getAmount()).append("|")
              .append(t.getTimestamp().toString()).append("^");
        }

        return sb.toString();
    }

    private String escapeField(String s) {
        if (s == null) return "";
        return s.replace(";", ",");
    }

    public static Account fromCSV(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(";", 6);
        if (parts.length < 5) return null;
        String accNo = parts[0];
        String name = parts[1];
        String pinOrHash = parts[2];
        double balance = 0;
        try { balance = Double.parseDouble(parts[3]); } catch (Exception e) { }
        AccountType type = AccountType.SAVINGS;
        try { type = AccountType.valueOf(parts[4]); } catch (Exception e) { }

        // If the stored value looks like a SHA-256 hex (64 hex chars), treat it as hashed; otherwise
        // assume it's a plain PIN and pass it to Account constructor which will hash it.
        Account acc;
        if (pinOrHash != null && pinOrHash.matches("(?i)^[0-9a-f]{64}$")) {
            acc = new Account(accNo, name, "0000", type, balance);
            // overwrite hashedPin directly (avoid re-hashing)
            acc.hashedPin = pinOrHash;
        } else {
            acc = new Account(accNo, name, pinOrHash, type, balance);
        }

        if (parts.length == 6 && parts[5] != null && !parts[5].isEmpty()) {
            String txns = parts[5];
            String[] items = txns.split("\\^");
            for (String item : items) {
                if (item.trim().isEmpty()) continue;
                String[] tparts = item.split("\\|");
                if (tparts.length >= 3) {
                    String ttype = tparts[0];
                    double tamt = 0;
                    try { tamt = Double.parseDouble(tparts[1]); } catch (Exception ex) {}
                    java.time.LocalDateTime time = java.time.LocalDateTime.parse(tparts[2]);
                    acc.getTransactions().add(new Transaction(ttype, tamt, time));
                }
            }
        }

        return acc;
    }

    private static String hashPin(String pin) {
        if (pin == null) return "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(pin.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
