import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles loading and saving accounts to a CSV file.
 */
public class FileHandler {
    private Path filePath;

    public FileHandler(String path) {
        this.filePath = Path.of(path);
    }

    public Map<String, Account> loadAccounts() {
        Map<String, Account> map = new HashMap<>();
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
                return map;
            }

            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            for (String line : lines) {
                Account acc = Account.fromCSV(line);
                if (acc != null) map.put(acc.getAccountNumber(), acc);
            }
        } catch (IOException e) {
            System.err.println("Failed to load accounts: " + e.getMessage());
        }
        return map;
    }

    public void saveAccounts(Map<String, Account> accounts) {
        StringBuilder sb = new StringBuilder();
        for (Account acc : accounts.values()) {
            sb.append(acc.toCSV()).append(System.lineSeparator());
        }
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            }
            Files.writeString(filePath, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Failed to save accounts: " + e.getMessage());
        }
    }
}
