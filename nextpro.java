import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

// BankingOperations Interface
interface BankingOperations {
    void createAccount(String accountHolder, double initialDeposit);
    void deposit(double amount);
    void withdraw(double amount);
    double checkBalance();
}

// Account Abstract Class
abstract class Account implements BankingOperations {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;

    public Account(String accountHolder, double initialDeposit) {
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;
        this.accountNumber = generateAccountNumber();
    }

    protected abstract String generateAccountNumber();
}

// BankAccount Class
class BankAccount extends Account {
    private static final Random random = new Random();
    private static final Map<String, BankAccount> accounts = new HashMap<>();

    public BankAccount(String accountHolder, double initialDeposit) {
        super(accountHolder, initialDeposit);
        accounts.put(accountNumber, this);
    }

    @Override
    protected String generateAccountNumber() {
        return String.format("%05d", random.nextInt(100000)); // Generate a random 5-digit number
    }

    @Override
    public void createAccount(String accountHolder, double initialDeposit) {
        new BankAccount(accountHolder, initialDeposit);
        System.out.println("Account created successfully! Account Number: " + accountNumber);
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    @Override
    public double checkBalance() {
        return balance;
    }

    public static BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}

// Main Class
public class BankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount account = null;

        while (true) {
            System.out.println("\n--- Banking System Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter account holder name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter initial deposit: ");
                    double initialDeposit = scanner.nextDouble();
                    account = new BankAccount(name, initialDeposit);
                    break;

                case 2:
                    if (account != null) {
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                    } else {
                        System.out.println("No account found. Please create an account first.");
                    }
                    break;

                case 3:
                    if (account != null) {
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        account.withdraw(withdrawAmount);
                    } else {
                        System.out.println("No account found. Please create an account first.");
                    }
                    break;

                case 4:
                    if (account != null) {
                        System.out.println("Current balance: " + account.checkBalance());
                    } else {
                        System.out.println("No account found. Please create an account first.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting the banking system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}