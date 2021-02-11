package transaction;

import account.Account;
import database.Database;
import exceptions.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class TransactionManager {

    public WithdrawTransaction makeWithdrawal(Account withdrawingAccount, BigDecimal amountToWithdraw) throws InsufficientFundsException {
        BigDecimal currentAccountBalance = withdrawingAccount.getBalance();
        if (amountToWithdraw.doubleValue() > currentAccountBalance.doubleValue()) {
            throw new InsufficientFundsException();
        }
        WithdrawTransaction transaction = new WithdrawTransaction(withdrawingAccount.getAccountNumber(), amountToWithdraw);
        withdrawingAccount.addTransaction(transaction);
        return transaction;
    }

    public DepositTransaction makeDeposit(Account depositingAccount, BigDecimal depositAmount) throws IllegalArgumentException {
        if (depositAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("You can only deposit Positive Amounts");
        }
        DepositTransaction transaction = new DepositTransaction(depositingAccount.getAccountNumber(), depositAmount);
        depositingAccount.addTransaction(transaction);
        return transaction;
    }

    public List<Transactable> makeTransfer(Account sendersAccount, Account recipientsAccount, BigDecimal transactionAmount,
                                           String transactionDescription) throws IllegalArgumentException, InsufficientFundsException {
        BigDecimal currentAccountBalance = sendersAccount.getBalance();
        if (transactionAmount.doubleValue() > currentAccountBalance.doubleValue()) {
            throw new InsufficientFundsException();
        }
        if (transactionAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("You can only transfer Positive Amounts");
        }
        Transactable debitTransferTransaction = new TransferTransaction(sendersAccount.getAccountNumber(), recipientsAccount.getAccountNumber(),
                TransactionType.DEBIT, transactionAmount, transactionDescription);
        debitTransferTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
        sendersAccount.addTransaction(debitTransferTransaction);
        Transactable creditTransferTransaction = new TransferTransaction(sendersAccount.getAccountNumber(), recipientsAccount.getAccountNumber(),
                TransactionType.CREDIT, transactionAmount, transactionDescription);
        creditTransferTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
        recipientsAccount.addTransaction(creditTransferTransaction);
        //
        List<Transactable> transactions = new ArrayList<>();
        transactions.add(debitTransferTransaction);
        transactions.add(creditTransferTransaction);
        return transactions;
    }

    public void rollBackTransaction(Transactable transaction) {
        transaction.rollbackTransaction();
    }

    public void rollBackTransaction(Transactable creditTransaction, Transactable debitTransaction) {
        creditTransaction.rollbackTransaction();
        debitTransaction.rollbackTransaction();
    }
}


