package transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Transaction {


    public abstract String getTransactionDescription();

    public abstract TransactionType getTransactionType();

    public abstract String getSendersAccountNumber();


    public abstract BigDecimal getTransactionAmount();

    public abstract String getRecipientsAccountNumber();

    public abstract TransactionStatus getTransactionStatus();

    public abstract LocalDate getTransactionDate();

    public abstract void setTransactionStatus(TransactionStatus newTransactionStatus);

}
