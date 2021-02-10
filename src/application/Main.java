package application;

import account.Account;
import customer.Customer;
import exceptions.InsufficientFundsException;
import transaction.Transactable;
import transaction.TransactionManager;
import transaction.TransactionType;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<Account> accounts = new ArrayList<>();
    static TransactionManager transactionManager = new TransactionManager();
    static Customer currentCustomer;
    static int sentinel = 1;

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
        List<Account> customerAccounts = customer.getAccounts();

        System.out.println("Account created successfully\nhere are all the details for all accounts you currently " +
                "own with Pentax Bank");
        int numberOfAccounts = 0;
        for (Account customerAccount : customerAccounts) {
            numberOfAccounts++;
            System.out.println("Account no:  " + numberOfAccounts);
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
                break;
            }
        }
    }

    private static void newCard() {
        System.out.println("Cards are not available right now ");
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
            currentCustomer.newAccount("savings");

        } else {
            currentCustomer.newAccount("current");
        }
        System.out.println("Account created successfully ....");
        displayAccounts();
    }

    private static void newTransactionQuery() {
        Account accountToQueryFrom = null;
        displayAccounts();
        scanner.nextLine();
        System.out.println("Enter the account number for the account you would like to get your transaction records from ");
        String accountNumber = scanner.nextLine();
        for (Account account : currentCustomer.getAccounts()) {
            if (account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                accountToQueryFrom = account;
                break;
            }
        }
        if (accountToQueryFrom != null) {
            displayTransactions(accountToQueryFrom);
        } else {
            System.out.println("Account number incorrect please recheck the provided account number ");
            System.out.println();
        }

    }

    private static void displayAccounts() {
        for (Account account : currentCustomer.getAccounts()) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println();
            System.out.println();
        }
    }

    private static void newWithdrawal() {
        Account customerAccount = null;
        if (currentCustomer != null && currentCustomer.getAccounts().size() == 0) {
            System.out.println("Sorry you do not have an account with us\n" +
                    "Would you like to open a new account, Enter 1 for yes or 2 for no ");
            int userChoice = scanner.nextInt();
            if (userChoice == 1) {
                newCustomer();
            } else {
                sentinel = -1;
                System.out.println("Goodbye ....");
                return;
            }
        } else {
            if (currentCustomer != null) {
                if (currentCustomer.getAccounts().size() == 1) {
                    customerAccount = currentCustomer.getAccounts().get(0);
                } else {
                    displayAccounts();
                    scanner.nextLine();
                    System.out.println("Enter the account number for the account you would like to withdraw from ");
                    String accountNumber = scanner.nextLine();
                    for (Account account : currentCustomer.getAccounts()) {
                        if (account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                            customerAccount = account;
                        }
                    }

                }
            }
        }
        System.out.println("Enter withdrawalAmount ");
        BigDecimal withdrawAmount = BigDecimal.valueOf(scanner.nextDouble());
        System.out.println(withdrawAmount.toString());
        if (customerAccount != null) {
            try {
                transactionManager.makeWithdrawal(customerAccount, withdrawAmount);
                System.out.println("withdrawal successful....");
                System.out.println();
            } catch (InsufficientFundsException insufficientFundsException) {
                System.out.println(insufficientFundsException.getMessage());
                System.out.println();
            }

        }
    }

    private static void newDeposit() {
        Account customerAccount = null;
        if (currentCustomer == null || currentCustomer.getAccounts().size() == 0) {
            System.out.println("Sorry you do not have an account with us\n" +
                    "Would you like to open a new account, Enter 1 for yes or 2 for no ");
            int userChoice = scanner.nextInt();
            if (userChoice == 1) {
                newCustomer();
            } else {
                System.out.println("Goodbye...");
                sentinel = -1;
                return;
            }
        } else {
            if (currentCustomer.getAccounts().size() == 1) {
                customerAccount = currentCustomer.getAccounts().get(0);
//                System.out.println("acn " + customerAccount.getAccountNumber());
            } else {
                displayAccounts();
                scanner.nextLine();
                System.out.println("Enter the account number for the account you would like to deposit to ");
                String accountNumber = scanner.nextLine();
                for (Account account : currentCustomer.getAccounts()) {
                    if (account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                        customerAccount = account;
                    }
                }
            }
        }
        System.out.println("Enter Deposit Amount ");
        BigDecimal withdrawAmount = BigDecimal.valueOf(scanner.nextDouble());
//        System.out.println(withdrawAmount.toString());
        if (customerAccount != null) {
//            System.out.println("acn 2" + customerAccount.getAccountNumber());
            try {
                transactionManager.makeDeposit(customerAccount, withdrawAmount);
                System.out.println("Deposit successful ....");
                System.out.println();
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println(illegalArgumentException.getMessage());
                System.out.println();
            }
        }
    }

    private static void newTransfer() {
        Account fromAccount = null;
        Account toAccount = null;
        if (currentCustomer != null && currentCustomer.getAccounts().size() == 0) {
            System.out.println("Sorry you do not have an account with us\n" +
                    "Would you like to open a new account, Enter 1 for yes or 2 for no ");
            int userChoice = scanner.nextInt();
            if (userChoice == 1) {
                newCustomer();
            } else {
                System.out.println("Goodbye...");
                sentinel = -1;
                return;
            }
        } else {
            if (currentCustomer != null) {
                if (currentCustomer.getAccounts().size() == 1) {
                    fromAccount = currentCustomer.getAccounts().get(0);
                } else {
                    displayAccounts();
                    scanner.nextLine();
                    System.out.println("Enter the account number for the account you would like to transfer from  ");
                    String accountNumber = scanner.nextLine();
                    for (Account account : currentCustomer.getAccounts()) {
                        if (account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                            System.out.println("Match found from");
                            fromAccount = account;
                        }
                    }
                }
            }
        }
        System.out.println("Enter the account number for the account you would like to transfer to ");
        String accountNumberToSendTo = scanner.nextLine();
        accounts.addAll(currentCustomer.getAccounts());
        for (Account account : accounts) {
            if (account.getAccountNumber().equalsIgnoreCase(accountNumberToSendTo)) {
                System.out.println("Match found to");
                toAccount = account;
            }
        }
        System.out.println("Please enter the amount you wish to transfer ");
        BigDecimal transferAmount = BigDecimal.valueOf(scanner.nextDouble());
        System.out.println("Please enter the transaction description ");
        scanner.nextLine();
        String transactionDescription = scanner.nextLine();
        if (fromAccount != null && toAccount != null) {
            try {
                transactionManager.makeTransfer(fromAccount, toAccount, transferAmount, transactionDescription);
                System.out.println("Transfer successful");
                System.out.println();
                System.out.println();
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println(illegalArgumentException.getMessage());
                System.out.println();
            } catch (InsufficientFundsException insufficientFundsException) {
                System.out.println(insufficientFundsException.getMessage());
                System.out.println();
                System.out.println();
            }

        }
    }

    private static void checkAccountBalance() {
        displayAccounts();
        System.out.println("Enter the account number for the account you would like to check ");
        Account customerAccount = null;
        scanner.nextLine();
        String accountNumber = scanner.nextLine();
        for (Account account : currentCustomer.getAccounts()) {
            if (account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                System.out.println("I executed ");
                System.out.println("Account acn : " + account.getAccountNumber());
                customerAccount = account;
                break;
            }
        }
        if (customerAccount != null) {
            System.out.println("Account balance : " + customerAccount.getBalance());
            System.out.println();
        }
    }

    private static Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}
