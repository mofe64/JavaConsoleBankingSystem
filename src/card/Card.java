package card;

import exceptions.IllegalAccountAccessException;
import transaction.Transactable;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Card {
    String cardNumber;
    String cardOwner;
    String cvv;
    int pin;
    LocalDate expiryDate;
    CardType cardType;
    Long cardLimit;
    String linkedAccount;
    List<Transactable> debitTransactionsOnCard = new ArrayList<>();

    static SecureRandom secureRandom = new SecureRandom();

    static String generateCvv() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            stringBuilder.append(1 + secureRandom.nextInt(9));
        }
        return stringBuilder.toString();
    }

    static String generateCardNumber(String cardNumberPrefix) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cardNumberPrefix);
        for (int i = 0; i < 14; i++) {
            stringBuilder.append(secureRandom.nextInt(10));
        }
        return stringBuilder.toString();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public String getExpiryDate() {
        return expiryDate.toString();
    }

    public int getPin() {
        return pin;
    }

    public String getLinkedAccount() {
        return linkedAccount;
    }

    public String getCvv() {
        return cvv;
    }

    public void setPin(int oldPin, int newPin) throws IllegalAccountAccessException {
        if (this.pin != oldPin) {
            throw new IllegalAccountAccessException();
        }
        pin = newPin;
    }

    public void setCardLimit(Long newCardLimit) {
        if (newCardLimit < 0) {
            throw new IllegalArgumentException();
        }
        this.cardLimit = newCardLimit;
    }

    public void addTransactionToCard(Transactable debitTransaction) {
        debitTransactionsOnCard.add(debitTransaction);
    }


    abstract boolean isCardOverLimit();

    abstract CardType getCardType();

    public abstract long getCardLimit();
}
