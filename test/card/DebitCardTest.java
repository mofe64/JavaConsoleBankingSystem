package card;

import account.Account;
import customer.Customer;
import exceptions.IllegalAccountAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transaction.TransactionManager;

import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class DebitCardTest {
    Customer customerOne;
    Account account;
    Card debitCard;
    TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        customerOne = new Customer("Max", "James", "testEmail", "09065360361", 1234,
                "semicolon", "savings");
        account = customerOne.getAccounts().get(0);
        String customerName = customerOne.getFirstName() + " " + customerOne.getLastName();
        debitCard = new DebitCard(customerName, account.getAccountNumber(), 1234);
        transactionManager = new TransactionManager();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testDebitCardFieldsProperlyInitializedWhenCreated() {
        assertEquals("Max James", debitCard.cardOwner);
        assertNotNull(debitCard.getCardNumber());
        assertEquals(account.getAccountNumber(), debitCard.getLinkedAccount());
        assertNotNull(debitCard.getCvv());
        assertEquals(CardType.DEBIT, debitCard.getCardType());
    }

    @Test
    void testDebitCardExpiryDateSetToFiveYearsAfterCardInitialization() {
        StringBuilder stringBuilder = new StringBuilder();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        String expectedExpiryDate = LocalDate.of(year + 5, month, 1).toString();
        assertEquals(expectedExpiryDate, debitCard.getExpiryDate());
    }

    @Test
    void testDebitCardNumberStartsWith65Prefix() {
        assertTrue(debitCard.getCardNumber().startsWith("65"));
    }

    @Test
    void testDebitCardHasNoLimit() {
        assertEquals(-1, debitCard.getCardLimit());
        assertFalse(debitCard.isCardOverLimit());
    }

    @Test
    void testDebitCardPinCanBeChanged() throws IllegalAccountAccessException {
        debitCard.setPin(1234, 5678);
        assertEquals(5678, debitCard.getPin());
    }

    @Test
    void testDebitCardThrowsIllegalAccountAccessExceptionWhenUserTriesToChangePinWithIncorrectPin() {
        assertThrows(IllegalAccountAccessException.class, () -> {
            debitCard.setPin(12, 4567);
        });
    }


}