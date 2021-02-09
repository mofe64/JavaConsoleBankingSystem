package transaction;

public enum TransactionStatus {
    SUCCESS,
    FAILED,
    PENDING,
    ROLLBACK;


    @Override
    public String toString() {
        String status = null;
        switch (this) {
            case FAILED -> status = "FAILED";
            case PENDING -> status = "PENDING";
            case SUCCESS -> status = "SUCCESS";
            case ROLLBACK -> status = "ROLLBACK";
        }
        return status;
    }
}
