package card;

public enum CardType {
    CREDIT,
    DEBIT,
    GOLD,
    BLACK;


    @Override
    public String toString() {
        String type = "";
        switch (this) {
            case CREDIT -> type = "CREDIT";
            case DEBIT -> type = "DEBIT";
            case GOLD -> type = "GOLD";
            case BLACK -> type = "BLACK";
        }
        return type;
    }
}
