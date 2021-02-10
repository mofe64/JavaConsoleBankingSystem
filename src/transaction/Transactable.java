package transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Transactable {


    String getTransactionDescription();

    TransactionType getTransactionType();

    String getSendersAccountNumber();


    BigDecimal getTransactionAmount();

    String getRecipientsAccountNumber();

    TransactionStatus getTransactionStatus();

    LocalDate getTransactionDate();

    void setTransactionStatus(TransactionStatus newTransactionStatus);

    void rollbackTransaction();

    String getTransactionId();
}
