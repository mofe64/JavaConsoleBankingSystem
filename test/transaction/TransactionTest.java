package transaction;

import account.Account;
import account.CurrentAccount;
import account.SavingsAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    Transaction transaction;
    Account receivingAccount;
    Account sendingAccount;

    @BeforeEach
    void setUp() {
        receivingAccount = new SavingsAccount();
        sendingAccount = new CurrentAccount();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testTransactionFieldsAreInitializedWhenCreated() {
        BigDecimal amount = BigDecimal.valueOf(150.60);
        Transaction transaction = new Transaction(sendingAccount.getAccountNumber(), receivingAccount.getAccountNumber(),
                TransactionType.CREDIT, amount, "Payment");
        assertEquals("Payment", transaction.getTransactionDescription());
        assertEquals(TransactionType.CREDIT, transaction.getTransactionType());
        assertEquals(sendingAccount.getAccountNumber(), transaction.getSendersAccountNumber());
        assertEquals(receivingAccount.getAccountNumber(), transaction.getRecipientsAccountNumber());
        assertEquals(TransactionStatus.PENDING, transaction.getTransactionStatus());
        assertEquals(amount, transaction.getTransactionAmount());
        assertNotNull(transaction.getTransactionDate());
    }

    @Test
    void testTransactionStatusCanBeChanged() {
        BigDecimal amount = BigDecimal.valueOf(150.60);
        Transaction transaction = new Transaction(sendingAccount.getAccountNumber(), receivingAccount.getAccountNumber(),
                TransactionType.CREDIT, amount, "Payment");
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        assertEquals(TransactionStatus.SUCCESS, transaction.getTransactionStatus());
        transaction.setTransactionStatus(TransactionStatus.FAILED);
        assertEquals(TransactionStatus.FAILED, transaction.getTransactionStatus());
    }

}