package transaction;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;

public class TransferTransaction implements Transactable {
    final String accountNumberToDebit;
    final String accountNumberToCredit;
    final TransactionType transactionType;
    final BigDecimal transactionAmount;
    final String transactionDescription;
    final LocalDate transactionDate;
    final String transactionId;
    TransactionStatus transactionStatus;

    public TransferTransaction(String sendersAccountNumber, String recipientsAccountNumber, TransactionType transactionType,
                               BigDecimal transactionAmount, String transactionDescription) {
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        String transactionIdPrefix = "TT";
        stringBuilder.append(transactionIdPrefix);
        for (int i = 0; i < 5; i++) {
            stringBuilder.append(secureRandom.nextInt(9));
        }
        this.transactionId = stringBuilder.toString();
        this.accountNumberToDebit = sendersAccountNumber;
        this.accountNumberToCredit = recipientsAccountNumber;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDescription = transactionDescription;
        transactionDate = LocalDate.now();
        transactionStatus = TransactionStatus.PENDING;
    }

    @Override
    public String getTransactionDescription() {
        return transactionDescription;
    }

    @Override
    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public String getSendersAccountNumber() {
        return accountNumberToDebit;
    }

    @Override
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    @Override
    public String getRecipientsAccountNumber() {
        return accountNumberToCredit;
    }

    @Override
    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    @Override
    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    @Override
    public void setTransactionStatus(TransactionStatus newTransactionStatus) {
        transactionStatus = newTransactionStatus;
    }

    @Override
    public void rollbackTransaction() {
        transactionStatus = TransactionStatus.ROLLBACK;
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }

}
