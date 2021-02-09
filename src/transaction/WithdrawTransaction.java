package transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WithdrawTransaction extends Transaction {
    final String accountNumberToDebit;
    final String accountNumberToCredit;
    final TransactionType transactionType;
    final BigDecimal transactionAmount;
    final String transactionDescription;
    final LocalDate transactionDate;
    TransactionStatus transactionStatus;

    public WithdrawTransaction(String accountNumberToDebit, BigDecimal transactionAmount) {
        this.accountNumberToDebit = accountNumberToDebit;
        this.accountNumberToCredit = null;
        this.transactionType = TransactionType.DEBIT;
        this.transactionAmount = transactionAmount;
        this.transactionDescription = "Withdrawal Transaction";
        transactionDate = LocalDate.now();
        transactionStatus = TransactionStatus.SUCCESS;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String getSendersAccountNumber() {
        return accountNumberToDebit;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public String getRecipientsAccountNumber() {
        return accountNumberToCredit;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionStatus(TransactionStatus newTransactionStatus) {
        transactionStatus = newTransactionStatus;
    }
}
