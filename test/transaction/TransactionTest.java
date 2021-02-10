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
        Transactable transaction = new TransferTransaction(sendingAccount.getAccountNumber(), receivingAccount.getAccountNumber(),
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
        Transactable transfer = new TransferTransaction(sendingAccount.getAccountNumber(), receivingAccount.getAccountNumber(),
                TransactionType.CREDIT, amount, "Payment");
        transfer.setTransactionStatus(TransactionStatus.SUCCESS);
        assertEquals(TransactionStatus.SUCCESS, transfer.getTransactionStatus());
        transfer.setTransactionStatus(TransactionStatus.FAILED);
        assertEquals(TransactionStatus.FAILED, transfer.getTransactionStatus());
    }

    @Test
    void testTransactionCanBeRolledBack() {
        BigDecimal transferAmount = BigDecimal.valueOf(100_000.0);
        sendingAccount.deposit(sendingAccount.getAccountNumber(), BigDecimal.valueOf(250_000.00));
        sendingAccount.newTransferTransaction(TransactionType.DEBIT, transferAmount, sendingAccount.getAccountNumber(),
                receivingAccount.getAccountNumber(), "Payment");
        receivingAccount.newTransferTransaction(TransactionType.CREDIT, transferAmount, sendingAccount.getAccountNumber(),
                receivingAccount.getAccountNumber(), "Payment");
        assertEquals(transferAmount, receivingAccount.getBalance());
        assertEquals(BigDecimal.valueOf(150_000.00), sendingAccount.getBalance());

    }

}