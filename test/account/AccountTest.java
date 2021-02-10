package account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transaction.TransactionType;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account sendersAccount;
    Account recipientsAccount;


    @BeforeEach
    void setUp() {
        sendersAccount = new SavingsAccount();
        recipientsAccount = new CurrentAccount();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAccountHasAnAccountNumberUponCreation() {
        assertNotNull(sendersAccount.getAccountNumber());
        assertNotNull(recipientsAccount.getAccountNumber());
    }

    @Test
    void testSavingsAccountNumberHasAnSAPrefix() {
        String accountNumber = sendersAccount.getAccountNumber();
        assertTrue(accountNumber.startsWith("SA"));
    }

    @Test
    void testCurrentAccountNumberHasACAPrefix() {
        String accountNumber = recipientsAccount.getAccountNumber();
        assertTrue(accountNumber.startsWith("CA"));
    }


    @Test
    void testAccountHasAnAccountType() {
        assertNotNull(sendersAccount.getAccountType());
        assertNotNull(recipientsAccount.getAccountNumber());
    }

    @Test
    void testUserCanCreateSavingsAccount() {
        assertEquals(AccountType.SAVINGS, sendersAccount.getAccountType());
    }

    @Test
    void testUserCanCreateCurrentAccount() {
        assertEquals(AccountType.CURRENT, recipientsAccount.getAccountType());
    }

    @Test
    void testAccountCanMakeTransactions() {
        BigDecimal transactionAmount = BigDecimal.valueOf(5000.00);
        String sendersAccountNumber = sendersAccount.getAccountNumber();
        String recipientsAccountNumber = recipientsAccount.getAccountNumber();
        recipientsAccount.newTransferTransaction(TransactionType.CREDIT, transactionAmount, sendersAccountNumber,
                recipientsAccountNumber, "Paying For The Laptop");
        sendersAccount.newTransferTransaction(TransactionType.DEBIT, transactionAmount, sendersAccountNumber,
                recipientsAccountNumber, "Paying For The Laptop");
        assertEquals(1, recipientsAccount.getTransactions().size());
        assertEquals(1, sendersAccount.getTransactions().size());
        assertEquals(TransactionType.CREDIT, recipientsAccount.getTransactions().get(0).getTransactionType());
        assertEquals(transactionAmount, recipientsAccount.getTransactions().get(0).getTransactionAmount());
        assertEquals(TransactionType.DEBIT, sendersAccount.getTransactions().get(0).getTransactionType());
        assertEquals(transactionAmount, sendersAccount.getTransactions().get(0).getTransactionAmount());
    }

    @Test
    void testAccountBalanceCanBeCalculated() {
        String sendersAccountNumber = sendersAccount.getAccountNumber();
        String recipientsAccountNumber = recipientsAccount.getAccountNumber();
        assertEquals(BigDecimal.valueOf(0.00), recipientsAccount.getBalance());
        assertEquals(BigDecimal.valueOf(0.00), sendersAccount.getBalance());
        BigDecimal depositAmount = BigDecimal.valueOf(50_000.00);
        //Test Deposit
        sendersAccount.deposit(sendersAccountNumber, depositAmount);
        assertEquals(depositAmount, sendersAccount.getBalance());
        //Test Transfer
        BigDecimal transactionAmount = BigDecimal.valueOf(50_000.00);
        recipientsAccount.newTransferTransaction(TransactionType.CREDIT, transactionAmount, sendersAccountNumber,
                recipientsAccountNumber, "Paying For The Laptop");
        sendersAccount.newTransferTransaction(TransactionType.DEBIT, transactionAmount, sendersAccountNumber,
                recipientsAccountNumber, "Paying For The Laptop");
        assertEquals(transactionAmount, recipientsAccount.getBalance());
        assertEquals(BigDecimal.valueOf(0.00), sendersAccount.getBalance());
        //Test Withdraw
        BigDecimal newDepositAmount = BigDecimal.valueOf(500_000.00);
        recipientsAccount.deposit(recipientsAccountNumber, newDepositAmount);
        assertEquals(BigDecimal.valueOf(550_000.00), recipientsAccount.getBalance());
        BigDecimal withdrawAmount = BigDecimal.valueOf(50_000.00);
        recipientsAccount.withdraw(recipientsAccountNumber, withdrawAmount);
        assertEquals(BigDecimal.valueOf(500_000.00), recipientsAccount.getBalance());

    }

}