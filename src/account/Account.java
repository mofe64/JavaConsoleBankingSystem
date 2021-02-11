package account;

import transaction.*;

import java.math.BigDecimal;


import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    String accountNumber;
    List<Transactable> transactions = new ArrayList<>();
    AccountType accountType;


    public String getAccountNumber() {
        return accountNumber;
    }

    public abstract AccountType getAccountType();


    public void addTransaction(Transactable transaction) {
        transactions.add(transaction);
    }

    public List<Transactable> getTransactions() {
        return transactions;
    }


    public BigDecimal getBalance() {
        BigDecimal balance;
        final double[] balanceValue = {0.0};
        transactions.stream()
                .filter(transactionRecord -> transactionRecord instanceof DepositTransaction &&
                        transactionRecord.getTransactionStatus().equals(TransactionStatus.SUCCESS))
                .forEach(transactable -> balanceValue[0] = balanceValue[0] + transactable.getTransactionAmount().doubleValue());

        transactions.stream()
                .filter(transactionRecord -> transactionRecord instanceof WithdrawTransaction &&
                        transactionRecord.getTransactionStatus().equals(TransactionStatus.SUCCESS))
                .forEach(transactable -> balanceValue[0] = balanceValue[0] - transactable.getTransactionAmount().doubleValue());

        transactions.stream()
                .filter(transactionRecord -> transactionRecord instanceof TransferTransaction &&
                        transactionRecord.getTransactionType().equals(TransactionType.DEBIT) &&
                        !transactionRecord.getTransactionStatus().equals(TransactionStatus.ROLLBACK))
                .forEach(transactable -> balanceValue[0] = balanceValue[0] - transactable.getTransactionAmount().doubleValue());

        transactions.stream()
                .filter(transactionRecord -> transactionRecord instanceof TransferTransaction &&
                        transactionRecord.getTransactionType().equals(TransactionType.CREDIT) &&
                        !transactionRecord.getTransactionStatus().equals(TransactionStatus.ROLLBACK))
                .forEach(transactable -> balanceValue[0] = balanceValue[0] + transactable.getTransactionAmount().doubleValue());

        balance = BigDecimal.valueOf(balanceValue[0]);
        return balance;
    }


}
