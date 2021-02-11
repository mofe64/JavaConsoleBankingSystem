package database;

import account.Account;
import customer.Customer;
import transaction.Transactable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Database {
    private final List<Transactable> transactions;
    private final List<Account> accounts;
    private final List<Customer> customers;
    private static Database databaseInstance = null;

    private Database() {
        transactions = new ArrayList<>();
        accounts = new ArrayList<>();
        customers = new ArrayList<>();
    }

    public static Database getDatabaseInstance() {
        if (databaseInstance == null) {
            databaseInstance = new Database();
        }
        return databaseInstance;
    }

    public void addAccount(Account newAccount) {
        accounts.add(newAccount);
    }

    public void addTransaction(Transactable transaction) {
        transactions.add(transaction);
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public List<Transactable> getAllTransaction() {
        return transactions;
    }

    public Optional<Account> findAccountByAccountNumber(String accountNumber) {
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equalsIgnoreCase(accountNumber))
                .findFirst();
    }

    public void deleteAccount(String accountNumber) {
        accounts.removeIf(account -> account.getAccountNumber().equalsIgnoreCase(accountNumber));
        deleteTransactionsBelongingToAnAccount(accountNumber);
    }

    public Optional<Transactable> findTransactionById(String transactionId) {
        return transactions.stream()
                .filter(transactable -> transactable.getTransactionId().equalsIgnoreCase(transactionId))
                .findFirst();
    }

    private void deleteTransactionsBelongingToAnAccount(String accountNumber) {
        transactions.removeIf(transactable ->
                transactable.getSendersAccountNumber().equalsIgnoreCase(accountNumber) ||
                        transactable.getRecipientsAccountNumber().equalsIgnoreCase(accountNumber));
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Optional<Customer> getCustomerByAccountNumber(String accountNumber) {
        return customers.stream().filter(customer -> customer.getAccounts().stream()
                .anyMatch(account -> account.getAccountNumber().equalsIgnoreCase(accountNumber))).findFirst();
    }

    public Optional<Customer> getCustomerByCustomerId(String customerId) {
        return customers.stream().filter(customer -> customer.getCustomerId().equalsIgnoreCase(customerId)).findFirst();
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public void deleteCustomer(String accountNumber) {
    }

    public void nukeDatabase() {
        transactions.clear();
        accounts.clear();
        customers.clear();
    }
}
