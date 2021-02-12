package card;

import account.Account;
import customer.Customer;
import exceptions.IllegalAccountAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transaction.DebitTransaction;
import transaction.Transactable;
import transaction.TransactionManager;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {
    Customer customerOne;
    Account account;
    Card creditCard;
    TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        customerOne = new Customer("Max", "James", "testEmail", "09065360361", 1234,
                "semicolon", "savings");
        account = customerOne.getAccounts().get(0);
        String customerName = customerOne.getFirstName() + " " + customerOne.getLastName();
        creditCard = new CreditCard(customerName, account.getAccountNumber(), 1234);
        transactionManager = new TransactionManager();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreditCardFieldsProperlyInitializedWhenCreated() {
        assertEquals("Max James", creditCard.cardOwner);
        assertNotNull(creditCard.getCardNumber());
        assertEquals(account.getAccountNumber(), creditCard.getLinkedAccount());
        assertNotNull(creditCard.getCvv());
        assertEquals(CardType.CREDIT, creditCard.getCardType());
        assertEquals(500_000L, creditCard.getCardLimit());
    }

    @Test
    void testCreditCardExpiryDateSetToFiveYearsAfterCardInitialization() {
        assertEquals("2026-02-01", creditCard.getExpiryDate());
    }

    @Test
    void testCreditCardNumberBeginsWith45() {
        assertTrue(creditCard.getCardNumber().startsWith("45"));
    }

    @Test
    void testCreditCardReturnsFalseIfCardTransactionsUnderLimit() {
        assertFalse(creditCard.isCardOverLimit());
    }

    @Test
    void testCreditCardReturnsTrueIfCardTransactionsOverLimit() {
        creditCard.setCardLimit(100L);
        DebitTransaction transaction = new DebitTransaction(account.getAccountNumber(), BigDecimal.valueOf(100_00));
        creditCard.addTransactionToCard(transaction);
        assertTrue(creditCard.isCardOverLimit());
    }
    @Test
    void testCreditCardPinCanBeChanged() throws IllegalAccountAccessException {
        creditCard.setPin(1234, 5678);
        assertEquals(5678, creditCard.getPin());
    }

    @Test
    void testCreditCardThrowsIllegalAccountAccessExceptionWhenUserTriesToChangePinWithIncorrectPin(){
        assertThrows(IllegalAccountAccessException.class, ()-> {
            creditCard.setPin(12, 4567);
        });
    }
}
