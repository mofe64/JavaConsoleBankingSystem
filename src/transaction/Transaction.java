package transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private final String sendersAccountNumber;
    private final String recipientsAccountNumber;
    private final TransactionType transactionType;
    private final BigDecimal transactionAmount;
    private final String transactionDescription;
    private final LocalDate transactionDate;
    private TransactionStatus transactionStatus;

    public Transaction(String sendersAccountNumber, String recipientsAccountNumber, TransactionType transactionType,
                       BigDecimal transactionAmount, String transactionDescription) {
        this.sendersAccountNumber = sendersAccountNumber;
        this.recipientsAccountNumber = recipientsAccountNumber;
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
        return sendersAccountNumber;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public String getRecipientsAccountNumber() {
        return recipientsAccountNumber;
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
