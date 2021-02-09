package transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private String sendersAccountNumber;
    private String recipientsAccountNumber;
    private TransactionType transactionType;
    private BigDecimal transactionAmount;
    private String transactionDescription;
    private LocalDate transactionDate;
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
}
