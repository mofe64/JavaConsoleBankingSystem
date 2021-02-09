package transaction;

public enum TransactionType {
    DEBIT,
    CREDIT;


    @Override
    public String toString() {
        String type = null;
        switch (this) {
            case DEBIT -> type = "DEBIT";
            case CREDIT -> type = "CREDIT";
        }
        return type;
    }
}
