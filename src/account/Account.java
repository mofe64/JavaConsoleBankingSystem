package account;

import transaction.Transaction;
import transaction.TransactionType;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.List;

public abstract class Account {
    String accountNumber;
    List<Transaction> transactions;
    AccountType accountType;


    public String getAccountNumber() {
        return accountNumber;
    }

    public abstract AccountType getAccountType();


    public void newTransaction(TransactionType type, BigDecimal transactionAmount, String accountNumber) {
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
