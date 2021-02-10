package transaction;

import account.Account;
import exceptions.InsufficientFundsException;

import java.math.BigDecimal;


public class TransactionManager {
    public void makeWithdrawal(Account withdrawingAccount, BigDecimal amountToWithdraw) throws InsufficientFundsException {
        BigDecimal currentAccountBalance = withdrawingAccount.getBalance();
        if (amountToWithdraw.doubleValue() > currentAccountBalance.doubleValue()) {
            throw new InsufficientFundsException();
        }
        withdrawingAccount.addTransaction(new WithdrawTransaction(withdrawingAccount.getAccountNumber(), amountToWithdraw));
    }

    public void makeDeposit(Account depositingAccount, BigDecimal depositAmount) throws IllegalArgumentException {
        if (depositAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("You can only deposit Positive Amounts");
        }
        depositingAccount.addTransaction(new DepositTransaction(depositingAccount.getAccountNumber(), depositAmount));
    }

    public void makeTransfer(Account sendersAccount, Account recipientsAccount, BigDecimal transactionAmount,
                             String transactionDescription) throws IllegalArgumentException,InsufficientFundsException {
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
    }
}


