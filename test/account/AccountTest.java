package account;

import database.Database;
import exceptions.InsufficientFundsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transaction.Transactable;
import transaction.TransactionManager;
import transaction.TransactionStatus;
import transaction.TransactionType;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account sendersAccount;
    Account recipientsAccount;
    TransactionManager transactionManager;


    @BeforeEach
    void setUp() {
        sendersAccount = new SavingsAccount();
        recipientsAccount = new CurrentAccount();
        transactionManager = new TransactionManager();
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
    void testAccountBalanceCanBeCalculated() throws InsufficientFundsException {
        assertEquals(BigDecimal.valueOf(0.00), recipientsAccount.getBalance());
        assertEquals(BigDecimal.valueOf(0.00), sendersAccount.getBalance());
        BigDecimal depositAmount = BigDecimal.valueOf(50_000.00);
        //Test Deposit
        transactionManager.makeDeposit(sendersAccount, depositAmount);
        assertEquals(depositAmount, sendersAccount.getBalance());
        //Test Transfer
        BigDecimal transferAmount = BigDecimal.valueOf(50_000.00);
        transactionManager.makeTransfer(sendersAccount, recipientsAccount, transferAmount, "Payment For Laptop");
        assertEquals(transferAmount, recipientsAccount.getBalance());
        assertEquals(BigDecimal.valueOf(0.00), sendersAccount.getBalance());
        //Test Withdraw
        BigDecimal newDepositAmount = BigDecimal.valueOf(500_000.00);
        transactionManager.makeDeposit(recipientsAccount, newDepositAmount);
        assertEquals(BigDecimal.valueOf(550_000.00), recipientsAccount.getBalance());
        BigDecimal withdrawAmount = BigDecimal.valueOf(50_000.00);
        transactionManager.makeWithdrawal(recipientsAccount, withdrawAmount);
        assertEquals(BigDecimal.valueOf(500_000.00), recipientsAccount.getBalance());
    }

    @Test
    void testAccountBalanceProperlyRecalculatedWhenATransactionIsRolledBack() {
        Account accountToTest = new SavingsAccount();
        Transactable depositTransaction = transactionManager.makeDeposit(accountToTest, BigDecimal.valueOf(100_000.0));
        assertEquals(BigDecimal.valueOf(100_000.0), accountToTest.getBalance());
        transactionManager.rollBackTransaction(depositTransaction);
        assertEquals(TransactionStatus.ROLLBACK, depositTransaction.getTransactionStatus());
        assertEquals(BigDecimal.valueOf(0.0), accountToTest.getBalance());

    }

    @Test
    void testWithdrawTransactionCanBeRolledBack() throws InsufficientFundsException {
        Account testAccount = new SavingsAccount();
        transactionManager.makeDeposit(testAccount, BigDecimal.valueOf(100_000.00));
        assertEquals(BigDecimal.valueOf(100_000.00), testAccount.getBalance());
        Transactable withdrawTransaction = transactionManager.makeWithdrawal(testAccount, BigDecimal.valueOf(50_000.00));
        System.out.println("Type is " + withdrawTransaction.getTransactionType());
        assertEquals(BigDecimal.valueOf(50_000.00), testAccount.getBalance());
        transactionManager.rollBackTransaction(withdrawTransaction);
        assertEquals(BigDecimal.valueOf(100_000.00), testAccount.getBalance());

    }


}