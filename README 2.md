# Offline Banking System (Console)

**Project title:** Offline Banking System

**Overview:**
This is a simple, console-based Java application that manages offline bank accounts using in-memory collections and CSV file persistence. It demonstrates object-oriented design (models, service layer), input validation, simple authentication, and file I/O. The app is intended for learning and as a student project submission.

**Features:**
- Create new bank accounts (Savings or Checking)
- Deposit and withdraw funds with validations
- Balance inquiry and per-account transaction history
- Close/delete accounts
- Simple authentication with Account Number + PIN (PINs are stored as SHA-256 hashes)
- CSV-based persistence stored in `data/accounts.csv`

**Technologies / Tools used:**
- Java (JDK 11+ recommended)
- Java Standard Library: `java.nio.file`, `java.security`, `java.time`
- No external libraries — self-contained project

**Project structure (important files):**
- `src/Account.java` — Account model (stores hashed PIN, balance, transactions)
- `src/Transaction.java` — Simple transaction record
- `src/AccountType.java` — Enum for account types
- `src/BankingService.java` — Business logic / service layer
- `src/FileHandler.java` — CSV load/save utilities
- `src/MainApp.java` — Console UI / menu loop
- `src/TestBankingService.java` — Simple test harness (no JUnit required)
- `data/accounts.csv` — Persistent accounts data (created automatically)

**Steps to install & run (Windows PowerShell):**

1. Open PowerShell and navigate to the project `src` folder:

```powershell
cd "C:/Users/SUSMIT ROY/OneDrive/Desktop/JAVA/JAVA PROJ ANJ/src"
```

2. Compile all Java sources:

```powershell
javac *.java
```

3. Run the application:

```powershell
java MainApp
```

Data will be read from/written to `data/accounts.csv` relative to the project root.

**Instructions for testing:**

1. Run the provided test harness (simple integration-style tests) which exercises account creation, deposit, withdrawal and basic assertions:

```powershell
cd "C:/Users/SUSMIT ROY/OneDrive/Desktop/JAVA/JAVA PROJ ANJ/src"
javac *.java
java TestBankingService
```

2. Expected output for the harness is `PASS: Basic operations` when everything works.

3. Manual testing: run `MainApp` and use the numbered menu. Try the following sequence:
- Create an account (enter a 4-digit PIN and initial deposit)
- Deposit to the created account
- Withdraw (test insufficient funds case)
- View transactions
- Save & Exit

**Notes & recommendations:**
- PINs are hashed with SHA-256. The loader accepts legacy plain-text PINs and will continue to work; saved data will contain hashed PINs on next save.
- For production-level security, consider adding per-account salts and using a password-based key derivation function (PBKDF2, bcrypt, Argon2).
- If you want automated unit tests with JUnit, I can add a `pom.xml` (Maven) or `build.gradle` (Gradle) and convert the harness to JUnit tests.

If you'd like, I can now:
- Add salted PBKDF2 hashing for PINs
- Convert the test harness to JUnit and add a Maven `pom.xml`
- Generate Javadoc HTML for all classes

---
Created for a student project demonstrating Java OOP and file I/O.
# Offline Banking System (Console)

Simple Java console application for managing offline bank accounts. Supports account creation, deposit, withdrawal, balance inquiry, viewing transaction history, and simple CSV-based persistence.

How to compile and run (Windows PowerShell):

```powershell
cd "C:/Users/SUSMIT ROY/OneDrive/Desktop/JAVA/JAVA PROJ ANJ/src"
javac *.java
java MainApp
```

Run the simple test harness:

```powershell
cd "C:/Users/SUSMIT ROY/OneDrive/Desktop/JAVA/JAVA PROJ ANJ/src"
javac *.java
java TestBankingService
```

Files created:
- `src/Account.java`
- `src/Transaction.java`
- `src/AccountType.java`
- `src/BankingService.java`
- `src/FileHandler.java`
- `src/MainApp.java`
- `src/InsufficientFundsException.java`
- `data/accounts.csv` (data file)

Notes:
- This is a minimal console-based implementation intended for learning and project submission. PINs are stored in plain text for simplicity; do not use this approach in production.
