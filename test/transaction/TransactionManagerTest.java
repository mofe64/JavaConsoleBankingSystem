package transaction;

import account.Account;
import account.SavingsAccount;
import database.Database;
import exceptions.InsufficientFundsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionManagerTest {
    Account accountOne;
    Account accountTwo;
    TransactionManager transactionManager;


    @BeforeEach
    void setUp() {
        accountOne = new SavingsAccount();
        accountTwo = new SavingsAccount();
        transactionManager = new TransactionManager();
        transactionManager.makeDeposit(accountOne, BigDecimal.valueOf(10_000));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testTransactionManagerCanMakeAWithdrawalTransaction() throws InsufficientFundsException {
        transactionManager.makeWithdrawal(accountOne, BigDecimal.valueOf(5_000));
        assertEquals(BigDecimal.valueOf(5_000.0), accountOne.getBalance());
    }

    @Test
    void testTransactionManagerCanMakeADepositTransaction() {
        transactionManager.makeDeposit(accountOne, BigDecimal.valueOf(10_000));
        assertEquals(BigDecimal.valueOf(20_000.0), accountOne.getBalance());

    }

    @Test
    void testTransactionManagerThrowsInsufficientFundsExceptionWhenUserTriesToWithdrawMoreThanBalance() {
        assertThrows(InsufficientFundsException.class, () -> transactionManager.makeWithdrawal(accountOne, BigDecimal.valueOf(100_000)));
    }

    @Test
    void testTransactionManagerThrowsIllegalArgumentExceptionWhenUserTriesToPerformAnyTransactionWithNegativeValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            transactionManager.makeWithdrawal(accountOne, BigDecimal.valueOf(-100_00.0));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            transactionManager.makeDeposit(accountOne, BigDecimal.valueOf(-100_000.0));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            transactionManager.makeTransfer(accountOne, accountTwo, BigDecimal.valueOf(-100_000.00), "Test");
        });
    }

    @Test
    void testTransactionManagerCanMakeATransferTransaction() throws InsufficientFundsException {
        Account sendingAccount = new SavingsAccount();
        Account receivingAccount = new SavingsAccount();
        assertEquals(BigDecimal.valueOf(0.0), sendingAccount.getBalance());
        transactionManager.makeDeposit(sendingAccount, BigDecimal.valueOf(1000.0));
        assertEquals(BigDecimal.valueOf(1000.0), sendingAccount.getBalance());
        transactionManager.makeTransfer(sendingAccount, receivingAccount, BigDecimal.valueOf(500.0), "test");
        assertEquals(BigDecimal.valueOf(500.0), receivingAccount.getBalance());
        assertEquals(BigDecimal.valueOf(500.0), sendingAccount.getBalance());
    }
}