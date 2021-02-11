package account;

import transaction.*;

import java.math.BigDecimal;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Account {
    String accountNumber;
    List<Transactable> transactions = new ArrayList<>();
    AccountType accountType;


    public String getAccountNumber() {
        return accountNumber;
    }

    public abstract AccountType getAccountType();


    public void newTransferTransaction(TransactionType transactionType, BigDecimal transactionAmount, String sendersAccountNumber,
                                       String recipientsAccountNumber, String transactionDescription) {
        Transactable newTransaction =
                new TransferTransaction(sendersAccountNumber, recipientsAccountNumber, transactionType, transactionAmount,
                        transactionDescription);
        newTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transactions.add(newTransaction);
    }


    public void withdraw(String customerAccountNumber, BigDecimal withdrawAmount) {
        Transactable newTransaction = new WithdrawTransaction(customerAccountNumber, withdrawAmount);
        transactions.add(newTransaction);
    }

    public void deposit(String customerAccountNumber, BigDecimal depositAmount) {
        Transactable newTransaction = new DepositTransaction(customerAccountNumber, depositAmount);
        transactions.add(newTransaction);
    }

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
                        transactionRecord.getTransactionStatus().equals(TransactionStatus.SUCCESS)).forEach(transactable -> {
            balanceValue[0] += transactable.getTransactionAmount().doubleValue();
        });

        transactions.stream()
                .filter(transactionRecord -> transactionRecord instanceof WithdrawTransaction &&
                        transactionRecord.getTransactionStatus().equals(TransactionStatus.SUCCESS)).forEach(transactable -> {
            balanceValue[0] -= transactable.getTransactionAmount().doubleValue();
        });

        transactions.stream()
                .filter(transactionRecord -> transactionRecord.getTransactionStatus().equals(TransactionStatus.ROLLBACK)).forEach(transactable -> {
            if (transactable.getTransactionType().equals(TransactionType.CREDIT)) {
                balanceValue[0] -= transactable.getTransactionAmount().doubleValue();
            } else {
                balanceValue[0] += transactable.getTransactionAmount().doubleValue();
            }
        });

        transactions.stream()
                .filter(transactionRecord -> transactionRecord instanceof TransferTransaction &&
                        transactionRecord.getTransactionType().equals(TransactionType.CREDIT)).forEach(transactable -> {
            balanceValue[0] += transactable.getTransactionAmount().doubleValue();
        });

        transactions.stream()
                .filter(transactionRecord -> transactionRecord instanceof TransferTransaction &&
                        transactionRecord.getTransactionType().equals(TransactionType.DEBIT)).forEach(transactable -> {
            balanceValue[0] -= transactable.getTransactionAmount().doubleValue();
        });

        balance = BigDecimal.valueOf(balanceValue[0]);
        return balance;
    }


}
