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
    void testAccountCanMakeTransactions() {
        BigDecimal transactionAmount = BigDecimal.valueOf(5000.00);
        String sendersAccountNumber = savingsAccount.getAccountNumber();
        String recipientsAccountNumber = currentAccount.getAccountNumber();
        currentAccount.newTransaction(TransactionType.CREDIT, transactionAmount, sendersAccountNumber,
                recipientsAccountNumber, "Paying For The Laptop");
        savingsAccount.newTransaction(TransactionType.DEBIT, transactionAmount, sendersAccountNumber,
                recipientsAccountNumber, "Paying For The Laptop");
        assertEquals(1, currentAccount.getTransactions().size());
        assertEquals(1, savingsAccount.getTransactions().size());
        assertEquals(TransactionType.CREDIT, currentAccount.getTransactions().get(0).getTransactionType());
        assertEquals(transactionAmount, currentAccount.getTransactions().get(0).getTransactionAmount());
        assertEquals(TransactionType.DEBIT, savingsAccount.getTransactions().get(0).getTransactionType());
        assertEquals(transactionAmount, savingsAccount.getTransactions().get(0).getTransactionAmount());
    }

    @Test
    void testAccountBalanceCanBeCalculated() {
    }

}