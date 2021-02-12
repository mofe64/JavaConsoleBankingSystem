package card;

import java.time.LocalDate;
import java.util.Calendar;

public class CreditCard extends Card {
    public CreditCard(String customerName, String customerAccountNumber, int pin) {
        this.cardOwner = customerName;
        this.linkedAccount = customerAccountNumber;
        this.cardLimit = 500_000L;
        this.cvv = generateCvv();
        this.cardNumber = generateCardNumber("45");
        this.cardType = CardType.CREDIT;
        this.pin = pin;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        this.expiryDate = LocalDate.of(year + 5, month, 1);
    }


    @Override
    boolean isCardOverLimit() {
        final double[] debits = {0.0};
        boolean isOverLimit = true;
        debitTransactionsOnCard.forEach(transactable -> {
            debits[0] = debits[0] + transactable.getTransactionAmount().doubleValue();
        });
        if (debits[0] < cardLimit) {
            isOverLimit = false;
        }
        return isOverLimit;
    }

    @Override
    CardType getCardType() {
        return cardType;
    }

    @Override
    public long getCardLimit() {
        return cardLimit;
    }


}
