package account;

import transaction.Transaction;
import transaction.TransactionType;

import java.math.BigDecimal;


import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    String accountNumber;
    List<Transaction> transactions = new ArrayList<>();
    AccountType accountType;


    public String getAccountNumber() {
        return accountNumber;
    }

    public abstract AccountType getAccountType();


    public void newTransaction(TransactionType transactionType, BigDecimal transactionAmount, String sendersAccountNumber,
                               String recipientsAccountNumber, String transactionDescription) {
        Transaction newTransaction =
                new Transaction(sendersAccountNumber, recipientsAccountNumber, transactionType, transactionAmount,
                        transactionDescription);
        transactions.add(newTransaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }


}
