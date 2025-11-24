import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String dataPath = "data/accounts.csv";
        FileHandler fh = new FileHandler(dataPath);
        BankingService service = new BankingService(fh);

        while (true) {
            System.out.println("\n=== Offline Banking System ===");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Balance Inquiry");
            System.out.println("5. View Transactions");
            System.out.println("6. Close Account");
            System.out.println("7. Save & Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Holder name: ");
                        String name = sc.nextLine().trim();
                        System.out.print("PIN (4 digits): ");
                        String pin = sc.nextLine().trim();
                        if (!isValidPin(pin)) { System.out.println("Invalid PIN format (use 4 digits)"); break; }
                        System.out.print("Account type (SAVINGS/CHECKING): ");
                        String typeStr = sc.nextLine().toUpperCase().trim();
                        AccountType type;
                        try { type = AccountType.valueOf(typeStr); }
                        catch (IllegalArgumentException e) { System.out.println("Invalid account type"); break; }
                        System.out.print("Initial deposit: ");
                        double init = 0;
                        try { init = Double.parseDouble(sc.nextLine()); if (init < 0) { System.out.println("Initial deposit cannot be negative"); break; } }
                        catch (NumberFormatException nfe) { System.out.println("Invalid number"); break; }
                        Account acc = service.createAccount(name, pin, type, init);
                        System.out.println("Created account: " + acc.getAccountNumber());
                        break;
                    case "2":
                        System.out.print("Account number: ");
                        String aNo = sc.nextLine();
                        System.out.print("PIN: ");
                        String p = sc.nextLine().trim();
                        if (!service.authenticate(aNo, p)) { System.out.println("Auth failed"); break; }
                        System.out.print("Amount to deposit: ");
                        double damt = 0;
                        try { damt = Double.parseDouble(sc.nextLine()); if (damt <= 0) { System.out.println("Amount must be positive"); break; } }
                        catch (NumberFormatException nfe) { System.out.println("Invalid number"); break; }
                        service.deposit(aNo, damt);
                        System.out.println("Deposit successful");
                        break;
                    case "3":
                        System.out.print("Account number: ");
                        String waNo = sc.nextLine();
                        System.out.print("PIN: ");
                        String wp = sc.nextLine().trim();
                        if (!service.authenticate(waNo, wp)) { System.out.println("Auth failed"); break; }
                        System.out.print("Amount to withdraw: ");
                        double wamt = 0;
                        try { wamt = Double.parseDouble(sc.nextLine()); if (wamt <= 0) { System.out.println("Amount must be positive"); break; } }
                        catch (NumberFormatException nfe) { System.out.println("Invalid number"); break; }
                        try { service.withdraw(waNo, wamt); System.out.println("Withdrawn"); }
                        catch (InsufficientFundsException ex) { System.out.println("Failed: " + ex.getMessage()); }
                        break;
                    case "4":
                        System.out.print("Account number: ");
                        String bNo = sc.nextLine();
                        System.out.print("PIN: ");
                        String bp = sc.nextLine().trim();
                        if (!service.authenticate(bNo, bp)) { System.out.println("Auth failed"); break; }
                        Account a = service.getAccount(bNo);
                        if (a == null) { System.out.println("Account not found"); break; }
                        System.out.println("Balance: " + a.getBalance());
                        break;
                    case "5":
                        System.out.print("Account number: ");
                        String tNo = sc.nextLine();
                        System.out.print("PIN: ");
                        String tp = sc.nextLine().trim();
                        if (!service.authenticate(tNo, tp)) { System.out.println("Auth failed"); break; }
                        Account ta = service.getAccount(tNo);
                        if (ta == null) { System.out.println("Account not found"); break; }
                        System.out.println("Transactions:");
                        for (Transaction tx : ta.getTransactions()) System.out.println(tx);
                        break;
                    case "6":
                        System.out.print("Account number to close: ");
                        String cNo = sc.nextLine();
                        System.out.print("PIN: ");
                        String cp = sc.nextLine().trim();
                        if (!service.authenticate(cNo, cp)) { System.out.println("Auth failed"); break; }
                        service.closeAccount(cNo);
                        System.out.println("Account closed");
                        break;
                    case "7":
                        service.saveAll();
                        System.out.println("Saved data. Goodbye.");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private static boolean isValidPin(String pin) {
        return pin != null && pin.matches("^\\d{4}$");
    }
}
