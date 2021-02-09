package account;

import transaction.*;

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


    public void newTransferTransaction(TransactionType transactionType, BigDecimal transactionAmount, String sendersAccountNumber,
                                       String recipientsAccountNumber, String transactionDescription) {
        Transaction newTransaction =
                new TransferTransaction(sendersAccountNumber, recipientsAccountNumber, transactionType, transactionAmount,
                        transactionDescription);
        newTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transactions.add(newTransaction);
    }


    public void withdraw(String customerAccountNumber, BigDecimal withdrawAmount) {
        Transaction newTransaction = new WithdrawTransaction(customerAccountNumber, withdrawAmount);
        transactions.add(newTransaction);
    }

    public void deposit(String customerAccountNumber, BigDecimal depositAmount) {
        Transaction newTransaction = new DepositTransaction(customerAccountNumber, depositAmount);
        transactions.add(newTransaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }


    public BigDecimal getBalance() {
        BigDecimal balance = BigDecimal.valueOf(0.00);
        for (Transaction transactionRecord : transactions) {
            if (transactionRecord instanceof DepositTransaction) {
                balance = balance.add(transactionRecord.getTransactionAmount());
            } else if (transactionRecord.getTransactionStatus().equals(TransactionStatus.SUCCESS)) {
                if (transactionRecord instanceof WithdrawTransaction) {
                    balance = balance.subtract(transactionRecord.getTransactionAmount());
                } else {
                    if (transactionRecord.getTransactionType().equals(TransactionType.CREDIT)) {
                        balance = balance.add(transactionRecord.getTransactionAmount());
                    } else {
                        balance = balance.subtract(transactionRecord.getTransactionAmount());
                    }
                }
            } else if (transactionRecord.getTransactionStatus().equals(TransactionStatus.ROLLBACK)) {
                if (transactionRecord instanceof WithdrawTransaction) {
                    balance = balance.add(transactionRecord.getTransactionAmount());
                }
                if (transactionRecord instanceof TransferTransaction) {
                    if (transactionRecord.getTransactionType().equals(TransactionType.CREDIT)) {
                        balance = balance.subtract(transactionRecord.getTransactionAmount());
                    } else {
                        balance = balance.add(transactionRecord.getTransactionAmount());
                    }
                }
            }
        }
        return balance;
    }

}
