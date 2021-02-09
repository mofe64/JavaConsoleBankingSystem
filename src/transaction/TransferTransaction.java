package transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferTransaction extends Transaction {
    final String accountNumberToDebit;
    final String accountNumberToCredit;
    final TransactionType transactionType;
    final BigDecimal transactionAmount;
    final String transactionDescription;
    final LocalDate transactionDate;
    TransactionStatus transactionStatus;

    public TransferTransaction(String sendersAccountNumber, String recipientsAccountNumber, TransactionType transactionType,
                               BigDecimal transactionAmount, String transactionDescription) {
        this.accountNumberToDebit = sendersAccountNumber;
        this.accountNumberToCredit = recipientsAccountNumber;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDescription = transactionDescription;
        transactionDate = LocalDate.now();
        transactionStatus = TransactionStatus.PENDING;
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
