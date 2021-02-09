package account;

public enum AccountType {
    SAVINGS,
    CURRENT;

    @Override
    public String toString() {
        String type = null;
        switch (this) {
            case CURRENT -> {
                type = "CURRENT";
            }
            case SAVINGS -> {
                type = "SAVINGS";
            }
        }
        return type;
    }
}
