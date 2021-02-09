package account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transaction.TransactionType;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account savingsAccount;
    Account currentAccount;

    @BeforeEach
    void setUp() {
        savingsAccount = new SavingsAccount();
        currentAccount = new CurrentAccount();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAccountHasAnAccountNumberUponCreation() {
        assertNotNull(savingsAccount.getAccountNumber());
        assertNotNull(currentAccount.getAccountNumber());
    }

    @Test
    void testSavingsAccountNumberHasAnSAPrefix() {
        String accountNumber = savingsAccount.getAccountNumber();
        assertTrue(accountNumber.startsWith("SA"));
    }

    @Test
    void testCurrentAccountNumberHasACAPrefix() {
        String accountNumber = currentAccount.getAccountNumber();
        assertTrue(accountNumber.startsWith("CA"));
    }


    @Test
    void testAccountHasAnAccountType() {
        assertNotNull(savingsAccount.getAccountType());
        assertNotNull(currentAccount.getAccountNumber());
    }

    @Test
    void testUserCanCreateSavingsAccount() {
        assertEquals(AccountType.SAVINGS, savingsAccount.getAccountType());
    }

    @Test
    void testUserCanCreateCurrentAccount() {
        assertEquals(AccountType.CURRENT, currentAccount.getAccountType());
    }

    @Test
    void testAccountCanMakeCreditTransaction() {
        BigDecimal transactionAmount = BigDecimal.valueOf(5000.00);
        savingsAccount.newTransaction(TransactionType.CREDIT, transactionAmount, savingsAccount.getAccountNumber());
        assertEquals(1, savingsAccount.getTransactions().size());
        currentAccount.newTransaction(TransactionType.CREDIT, transactionAmount, currentAccount.getAccountNumber());
        assertEquals(1, currentAccount.getTransactions().size());
    }

    @Test
    void testAccountCanMakeDebitTransaction() {
        BigDecimal transactionAmount = BigDecimal.valueOf(5000.00);
        Account receivingAccount = new SavingsAccount();
        savingsAccount.newTransaction(TransactionType.DEBIT, transactionAmount, receivingAccount.getAccountNumber());
        assertEquals(1, savingsAccount.getTransactions().size());
    }


}