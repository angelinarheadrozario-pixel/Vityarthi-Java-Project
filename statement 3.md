# Project Statement: Offline Banking System

## Problem statement

Many learners and small-scale users need a simple, reliable way to manage basic banking operations locally without a production database or network dependency. The problem is to provide a lightweight, offline application that demonstrates account management, transaction processing, basic authentication, and data persistence using only standard Java libraries.

The application should be easy to run on any machine with a JDK, safely persist data to a local file, and clearly illustrate core programming concepts such as OOP, collections, exception handling, and file I/O.

## Scope of the project

- Console-based application (no GUI) to keep the scope focused and portable.
- Core account management: create, read (view), update (simple fields), and delete/close accounts.
- Transaction processing: deposit and withdraw operations with validation and transaction history logging.
- Local persistence using a CSV-formatted file (`data/accounts.csv`) loaded at startup and saved on demand or at exit.
- Basic authentication using Account Number + PIN (PINs stored as hashed values for improved safety).
- No external database or third-party libraries—project relies on Java standard library only.

## Target users

- Students learning Java and object-oriented programming who need a practical project to demonstrate concepts.
- Instructors and examiners who require a simple, reproducible offline project submission.
- Hobbyists who want a small, local personal finance prototype (not intended for real financial use).

## High-level features

- Account Management
  - Create accounts (Savings / Checking) with holder name, PIN, and initial deposit.
  - View account details and balance.
  - Update account holder name or PIN.
  - Close/delete an account.

- Transaction Processing
  - Deposit funds (validated positive amounts).
  - Withdraw funds with sufficient-balance check and custom `InsufficientFundsException`.
  - Record transaction history (timestamped) per account.

- Data Persistence
  - Load accounts and their transaction histories from `data/accounts.csv` at startup.
  - Save accounts back to CSV on explicit save or on program exit.

- Authentication & Security
  - Authenticate actions (deposit, withdraw, view) using account number + PIN.
  - PINs stored as SHA-256 hashes in saved data to avoid plain-text storage.

- Usability & Validation
  - Console menu with numbered options for ease of use.
  - Input validation for PIN format and numeric amount fields.

---
This statement describes the functional boundaries and audience for the Offline Banking System prepared as a student-friendly Java project.
