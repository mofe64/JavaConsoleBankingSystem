package account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transaction.TransactionType;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account sendersSavingsAccount;
    Account recipientsCurrentAccount;


    @BeforeEach
    void setUp() {
        sendersSavingsAccount = new SavingsAccount();
        recipientsCurrentAccount = new CurrentAccount();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAccountHasAnAccountNumberUponCreation() {
        assertNotNull(sendersSavingsAccount.getAccountNumber());
        assertNotNull(recipientsCurrentAccount.getAccountNumber());
    }

    @Test
    void testSavingsAccountNumberHasAnSAPrefix() {
        String accountNumber = sendersSavingsAccount.getAccountNumber();
        assertTrue(accountNumber.startsWith("SA"));
    }

    @Test
    void testCurrentAccountNumberHasACAPrefix() {
        String accountNumber = recipientsCurrentAccount.getAccountNumber();
        assertTrue(accountNumber.startsWith("CA"));
    }


    @Test
    void testAccountHasAnAccountType() {
        assertNotNull(sendersSavingsAccount.getAccountType());
        assertNotNull(recipientsCurrentAccount.getAccountNumber());
    }

    @Test
    void testUserCanCreateSavingsAccount() {
        assertEquals(AccountType.SAVINGS, sendersSavingsAccount.getAccountType());
    }

    @Test
    void testUserCanCreateCurrentAccount() {
        assertEquals(AccountType.CURRENT, recipientsCurrentAccount.getAccountType());
    }

    @Test
    void testAccountCanMakeTransactions() {
        BigDecimal transactionAmount = BigDecimal.valueOf(5000.00);
        String sendersAccountNumber = sendersSavingsAccount.getAccountNumber();
        String recipientsAccountNumber = recipientsCurrentAccount.getAccountNumber();
        recipientsCurrentAccount.newTransferTransaction(TransactionType.CREDIT, transactionAmount, sendersAccountNumber,
                recipientsAccountNumber, "Paying For The Laptop");
        sendersSavingsAccount.newTransferTransaction(TransactionType.DEBIT, transactionAmount, sendersAccountNumber,
                recipientsAccountNumber, "Paying For The Laptop");
        assertEquals(1, recipientsCurrentAccount.getTransactions().size());
        assertEquals(1, sendersSavingsAccount.getTransactions().size());
        assertEquals(TransactionType.CREDIT, recipientsCurrentAccount.getTransactions().get(0).getTransactionType());
        assertEquals(transactionAmount, recipientsCurrentAccount.getTransactions().get(0).getTransactionAmount());
        assertEquals(TransactionType.DEBIT, sendersSavingsAccount.getTransactions().get(0).getTransactionType());
        assertEquals(transactionAmount, sendersSavingsAccount.getTransactions().get(0).getTransactionAmount());
    }

    @Test
    void testAccountBalanceCanBeCalculated() {
        String sendersAccountNumber = sendersSavingsAccount.getAccountNumber();
        String recipientsAccountNumber = recipientsCurrentAccount.getAccountNumber();
        assertEquals(BigDecimal.valueOf(0.00), recipientsCurrentAccount.getBalance());
        assertEquals(BigDecimal.valueOf(0.00), sendersSavingsAccount.getBalance());
        BigDecimal depositAmount = BigDecimal.valueOf(50000.00);
        sendersSavingsAccount.deposit(sendersAccountNumber, depositAmount);
        assertEquals(depositAmount, sendersSavingsAccount.getBalance());
        BigDecimal transactionAmount = BigDecimal.valueOf(50000.00);
        recipientsCurrentAccount.newTransferTransaction(TransactionType.CREDIT, transactionAmount, sendersAccountNumber,
                recipientsAccountNumber, "Paying For The Laptop");
        sendersSavingsAccount.newTransferTransaction(TransactionType.DEBIT, transactionAmount, sendersAccountNumber,
                recipientsAccountNumber, "Paying For The Laptop");
        assertEquals(transactionAmount, recipientsCurrentAccount.getBalance());
        assertEquals(BigDecimal.valueOf(0.00), sendersSavingsAccount.getBalance());
    }

}