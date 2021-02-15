package application;

import account.Account;
import customer.Customer;
import database.Database;
import exceptions.InsufficientFundsException;
import transaction.Transactable;
import transaction.TransactionManager;


import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static TransactionManager transactionManager = new TransactionManager();
    static Customer currentCustomer;
    static int sentinel = 1;
    static Database database = Database.getDatabaseInstance();

    public static void main(String[] args) {
        while (sentinel != -1) {
            System.out.println("Welcome to Pentax Bank");
            System.out.println("Enter 1 to register as new customer\n" +
                    "Enter 2 to Make a transaction\n" +
                    "Enter 3 to Request for a Card\n" +
                    "Enter 4 to Speak with Customer Care regarding a failed transaction\n" +
                    "Enter 5 to View your transaction history\n" +
                    "Enter 6 to Add a new Account\n" +
                    "Enter 7 to check your account balance\n" +
                    "Enter 8 to Exit ");
            int userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1 -> newCustomer();
                case 2 -> newTransaction();
                case 3 -> newCard();
                case 4 -> newIssue();
                case 5 -> newTransactionQuery();
                case 6 -> newAccount();
                case 7 -> checkAccountBalance();
                case 8 -> {
                    System.out.println("Goodbye .....");
                    sentinel = -1;
                }
            }
        }
    }

    private static void newCustomer() {
        System.out.println("We're happy to have you join our ever expanding family");
        System.out.println("Just a couple of details and your account will be set up ");
        scanner.nextLine();
        System.out.println("Enter your Firstname ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your lastName ");
        String lastName = scanner.nextLine();
        System.out.println("Enter your email address ");
        String email = scanner.nextLine();
        System.out.println("Enter your phone number ");
        String phoneNumber = String.valueOf(scanner.nextInt());
        System.out.println("Enter your bvn ");
        int bvn = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter your address ");
        String address = scanner.nextLine();
        System.out.println("Enter 1 for a savings account or 2 for a current account ");
        String accountType = null;
        int userAccountType = scanner.nextInt();
        if (userAccountType == 1) {
            accountType = "savings";
        } else {
            accountType = "current";
        }
        Customer customer = new Customer(firstName, lastName, email, phoneNumber, bvn, address, accountType);
        currentCustomer = customer;
        database.addCustomer(customer);
        database.addAccount(currentCustomer.getAccounts().get(0));
        List<Account> customerAccounts = currentCustomer.getAccounts();

        System.out.println();
        System.out.println("Account created successfully\n\nHere are all the details for your new " + accountType + " accounts ");
        for (Account customerAccount : customerAccounts) {
            System.out.println("Account Number: " + customerAccount.getAccountNumber());
            System.out.println("Account Type: " + customerAccount.getAccountType());
            System.out.println("Account Balance: " + customerAccount.getBalance());
            System.out.println();
        }

    }

    private static void newTransaction() {
        System.out.println("Enter 1 to withdraw\n" +
                "Enter 2 to deposit\n" +
                "Enter 3 to transfer\n" +
                "Enter 4 to go back to the main menu");
        int userInput = scanner.nextInt();
        switch (userInput) {
            case 1 -> newWithdrawal();
            case 2 -> newDeposit();
            case 3 -> newTransfer();
            case 4 -> {
            }
        }
    }

    private static void newCard() {
        System.out.println("Enter one for a debit card or two for a credit card");
        int userInput = scanner.nextInt();
        if(userInput == 1){

        } else {

        }
    }

    private static void newIssue() {
        scanner.nextLine();
        Account accountInQuestion = null;
        System.out.println("Welcome to Pentax bank Customer Care ....\n" +
                "Please Enter the account Number of the account on which the transaction occurred ");
        String accountNumber = scanner.nextLine();
        for (Account account : currentCustomer.getAccounts()) {
            if (account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                accountInQuestion = account;
                break;
            }
        }
        if (accountInQuestion != null) {

            System.out.println("Listed below are the list of recent transactions from the account in question ");
            displayTransactions(accountInQuestion);
            System.out.println("Enter the transactionId for the disputed transaction ");
            String transactionId = scanner.nextLine();
            System.out.print("Reviewing Records .");
            try {
                Thread.sleep(1000);
                System.out.print(".");
                Thread.sleep(1000);
                System.out.print(".");
                Thread.sleep(1000);
                System.out.print(".");
                Thread.sleep(1000);
                System.out.print(".");
                Thread.sleep(1000);
                System.out.print(".");
                System.out.println();
            } catch (Exception exception) {
                System.out.println("Something went wrong processing your records please visit our nearest branch");
                System.out.println();
                return;
            }

            int chanceFactor = 1 + new SecureRandom().nextInt(3);
            if (chanceFactor == 1) {
                for (Transactable transactionRecord : accountInQuestion.getTransactions()) {
                    if (transactionRecord.getTransactionId().equalsIgnoreCase(transactionId)) {
                        transactionRecord.rollbackTransaction();
                        System.out.println("Transaction rolled back successfully");
                        System.out.println("We are sorry for all inconveniences caused, Thank you for banking with Pentax bank");
                        System.out.println();
                        System.out.println();
                        break;
                    }
                }
            } else {
                System.out.println("Sorry we could not resolve that transaction, please visit on of our branches...");
                System.out.println();
                System.out.println();
            }
        } else {
            System.out.println("Couldn't find the account with account number specified please check the account number and try again");
            System.out.println();
            System.out.println();
        }
    }

    private static void displayTransactions(Account accountInQuestion) {
        for (Transactable transactionRecord : accountInQuestion.getTransactions()) {
            System.out.println("Transaction Id: " + transactionRecord.getTransactionId());
            System.out.println("Transaction Date: " + transactionRecord.getTransactionDate().toString());
            System.out.println("Transaction Amount: " + transactionRecord.getTransactionAmount().toString());
            System.out.println("Transaction Description " + transactionRecord.getTransactionDescription());
            System.out.println("Transaction Initiated by: " + transactionRecord.getSendersAccountNumber());
            System.out.println("Transaction Received by: " + transactionRecord.getRecipientsAccountNumber());
            System.out.println("Transaction Status: " + transactionRecord.getTransactionStatus());
            System.out.println("Transaction Type: " + transactionRecord.getTransactionType());
            System.out.println();
            System.out.println();

        }
    }

    private static void newAccount() {
        System.out.println("Enter 1 to create a Savings Account\n" +
                "Enter 2 to create a current Account");
        int userInput = scanner.nextInt();
        if (userInput == 1) {
            Account newSavingsAccount = currentCustomer.newAccount("savings");
            database.addAccount(newSavingsAccount);
        } else {
            Account newCurrentAccount = currentCustomer.newAccount("current");
            database.addAccount(newCurrentAccount);
        }
        System.out.println("Account created successfully ....");
        displayAccounts();
    }

    private static void newTransactionQuery() {
        displayAccounts();
        scanner.nextLine();
        System.out.println("Enter the account number for the account you would like to get your transaction records from ");
        String accountNumber = scanner.nextLine();
        Optional<Account> accountOptional = database.getAllAccounts().stream()
                .filter(account -> account.getAccountNumber().equalsIgnoreCase(accountNumber))
                .findFirst();
        if (accountOptional.isPresent()) {
            displayTransactions(accountOptional.get());
        } else {
            System.out.println("Account number incorrect please recheck the provided account number ");
            System.out.println();
        }

    }

    private static void displayAccounts() {
        currentCustomer.getAccounts().forEach(account -> {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println();
            System.out.println();
        });
    }

    private static void newWithdrawal() {
        if (loggedInUserDoesNotHaveAccount()) return;
        displayAccounts();
        scanner.nextLine();
        System.out.println("Enter the account number for the account you would like to withdraw from ");
        String accountNumber = scanner.nextLine();
        Optional<Account> accountOptional = database.getAllAccounts().stream()
                .filter(account -> account.getAccountNumber().equalsIgnoreCase(accountNumber)).findFirst();
        if (accountOptional.isPresent()) {
            System.out.println("Enter withdrawalAmount ");
            BigDecimal withdrawAmount = BigDecimal.valueOf(scanner.nextDouble());
            try {
                database.addTransaction(transactionManager.makeWithdrawal(accountOptional.get(), withdrawAmount));
                System.out.println("withdrawal successful....");
                System.out.println();
            } catch (InsufficientFundsException insufficientFundsException) {
                System.out.println(insufficientFundsException.getMessage());
                System.out.println();
            }
        } else {
            System.out.println("Account not found please check the account Number and try again");
        }
    }

    private static boolean loggedInUserDoesNotHaveAccount() {
        boolean loggedInUserDoesNotHaveAnAccount = currentCustomer == null || currentCustomer.getAccounts().size() == 0;
        if (loggedInUserDoesNotHaveAnAccount) {
            System.out.println("Sorry you do not have an account with us\n" +
                    "Would you like to open a new account, Enter 1 for yes or 2 for no ");
            int userChoice = scanner.nextInt();
            if (userChoice == 1) {
                newCustomer();
            } else {
                sentinel = -1;
                System.out.println("Goodbye ....");
                return true;
            }
        }
        return false;
    }

    private static void newDeposit() {
        if (loggedInUserDoesNotHaveAccount()) return;
        displayAccounts();
        scanner.nextLine();
        System.out.println("Enter the account number for the account you would like to Deposit to ");
        String accountNumber = scanner.nextLine();
        Optional<Account> accountOptional = database.getAllAccounts().stream()
                .filter(account -> account.getAccountNumber().equalsIgnoreCase(accountNumber)).findFirst();
        if (accountOptional.isPresent()) {
            System.out.println("Enter Deposit Amount ");
            BigDecimal DecimalAmount = BigDecimal.valueOf(scanner.nextDouble());
            try {
                database.addTransaction(transactionManager.makeDeposit(accountOptional.get(), DecimalAmount));
                System.out.println("Deposit successful....");
                System.out.println();
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println(illegalArgumentException.getMessage());
                System.out.println();
            }
        } else {
            System.out.println("Account not found please check the account Number and try again");
        }
    }

    private static void newTransfer() {
        Account fromAccount = null;
        Account toAccount = null;
        if (loggedInUserDoesNotHaveAccount()) return;
        displayAccounts();
        System.out.println("Enter your account number to transfer from  ");
        String accountNumberToSendFrom = scanner.nextLine();
        Optional<Account> accountOptional = database.getAllAccounts().stream()
                .filter(account -> account.getAccountNumber().equalsIgnoreCase(accountNumberToSendFrom))
                .findFirst();
        if (accountOptional.isPresent()) {
            fromAccount = accountOptional.get();
        } else {
            System.out.println("Account not found please check the account number and try again");
            return;
        }
        System.out.println("Enter the account number for the account you would like to transfer to ");
        String accountNumberToSendTo = scanner.nextLine();
        Optional<Account> accountOptional2 = database.getAllAccounts().stream()
                .filter(account -> account.getAccountNumber().equalsIgnoreCase(accountNumberToSendTo))
                .findFirst();
        if (accountOptional2.isPresent()) {
            toAccount = accountOptional2.get();
        } else {
            System.out.println("Account not found please check the account and try again");
            return;
        }
        System.out.println("Please enter the amount you wish to transfer ");
        BigDecimal transferAmount = BigDecimal.valueOf(scanner.nextDouble());
        System.out.println("Please enter the transaction description ");
        scanner.nextLine();
        String transactionDescription = scanner.nextLine();
        try {
            List<Transactable> completedTransfers = transactionManager.makeTransfer(fromAccount, toAccount, transferAmount, transactionDescription);
            completedTransfers.forEach(transactable -> database.addTransaction(transactable));
            System.out.println("Transfer successful");
            System.out.println();
            System.out.println();
        } catch (IllegalArgumentException | InsufficientFundsException exception) {
            System.out.println(exception.getMessage());
            System.out.println();
        }
    }

    private static void checkAccountBalance() {
        displayAccounts();
        System.out.println("Enter the account number for the account you would like to check ");
        scanner.nextLine();
        String accountNumber = scanner.nextLine();
        Optional<Account> accountOptional = database.getAllAccounts().stream()
                .filter(account -> account.getAccountNumber().equalsIgnoreCase(accountNumber))
                .findFirst();
        if (accountOptional.isPresent()) {
            System.out.println("Account balance : " + accountOptional.get().getBalance());
        } else {
            System.out.println("Account not found please recheck account number and try again ");
        }
        System.out.println();
    }

    private static Account findAccount(String accountNumber) {
        Optional<Account> accountOptional = database.getAllAccounts().stream()
                .filter(account -> account.getAccountNumber().equalsIgnoreCase(accountNumber))
                .findFirst();
        return accountOptional.orElse(null);
    }
}
