import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service layer containing core banking operations.
 */
public class BankingService {
    private Map<String, Account> accounts = new HashMap<>();
    private FileHandler fileHandler;

    public BankingService(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.accounts = fileHandler.loadAccounts();
    }

    public Account createAccount(String name, String pin, AccountType type, double initialDeposit) {
        String accNo = generateAccountNumber();
        Account acc = new Account(accNo, name, pin, type, initialDeposit);
        if (initialDeposit > 0) acc.deposit(initialDeposit); // records transaction
        accounts.put(accNo, acc);
        return acc;
    }

    private String generateAccountNumber() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public Account getAccount(String accNo) {
        return accounts.get(accNo);
    }

    public boolean authenticate(String accNo, String pin) {
        Account a = accounts.get(accNo);
        return a != null && a.checkPin(pin);
    }

    public void deposit(String accNo, double amount) {
        Account a = accounts.get(accNo);
        if (a != null) {
            a.deposit(amount);
        }
    }

    public void withdraw(String accNo, double amount) throws InsufficientFundsException {
        Account a = accounts.get(accNo);
        if (a != null) {
            a.withdraw(amount);
        }
    }

    public void closeAccount(String accNo) {
        accounts.remove(accNo);
    }

    public void saveAll() {
        fileHandler.saveAccounts(accounts);
    }
}
