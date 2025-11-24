import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Simple test harness for BankingService. Run with:
 * javac *.java
 * java TestBankingService
 */
public class TestBankingService {
    public static void main(String[] args) throws Exception {
        String testData = "data/test_accounts.csv";
        // ensure test data directory
        Path p = Path.of(testData);
        if (Files.exists(p)) Files.delete(p);

        FileHandler fh = new FileHandler(testData);
        BankingService svc = new BankingService(fh);

        Account acc = svc.createAccount("Alice", "1234", AccountType.SAVINGS, 100.0);
        boolean authOk = svc.authenticate(acc.getAccountNumber(), "1234");
        if (!authOk) { System.out.println("FAIL: Authentication failed"); System.exit(1); }

        svc.deposit(acc.getAccountNumber(), 50.0);
        try { svc.withdraw(acc.getAccountNumber(), 30.0); } catch (Exception e) { System.out.println("FAIL: withdraw"); System.exit(1); }

        Account loaded = svc.getAccount(acc.getAccountNumber());
        double expected = 120.0; // 100 +50 -30 (deposit records initial deposit too)
        if (Math.abs(loaded.getBalance() - expected) > 0.0001) {
            System.out.println("FAIL: Expected balance " + expected + " but was " + loaded.getBalance());
            System.exit(1);
        }

        svc.saveAll();

        System.out.println("PASS: Basic operations");

        // cleanup
        if (Files.exists(p)) Files.delete(p);
    }
}
