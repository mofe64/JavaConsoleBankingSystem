package card;

import java.time.LocalDate;
import java.util.Calendar;

public class DebitCard extends Card {
    public DebitCard(String customerName, String customerAccountNumber, int userPin) {
        this.cardOwner = customerName;
        this.linkedAccount = customerAccountNumber;
        this.cardLimit = Long.MAX_VALUE;
        this.cvv = generateCvv();
        this.cardNumber = generateCardNumber("65");
        this.cardType = CardType.DEBIT;
        this.pin = userPin;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        this.expiryDate = LocalDate.of(year + 5, month, 1);
    }

    @Override
    boolean isCardOverLimit() {
        return false;
    }

    @Override
    CardType getCardType() {
        return cardType;
    }

    @Override
    public long getCardLimit() {
        return -1;
    }
}
