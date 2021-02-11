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
    Account account;
    TransactionManager transactionManager;
    Database database = Database.getDatabaseInstance();

    @BeforeEach
    void setUp() {
        database.nukeDatabase();
        account = new SavingsAccount();
        transactionManager = new TransactionManager();
        transactionManager.makeDeposit(account, BigDecimal.valueOf(10_000));
    }

    @AfterEach
    void tearDown() {
        database.nukeDatabase();
    }

    @Test
    void testTransactionManagerCanMakeAWithdrawalTransaction() throws InsufficientFundsException {
        transactionManager.makeWithdrawal(account, BigDecimal.valueOf(5_000));
        assertEquals(BigDecimal.valueOf(5_000.0), account.getBalance());
    }

    @Test
    void testTransactionManagerCanMakeADepositTransaction() {
        transactionManager.makeDeposit(account, BigDecimal.valueOf(10_000));
        assertEquals(BigDecimal.valueOf(20_000.0), account.getBalance());

    }

    @Test
    void testTransactionManagerThrowsInsufficientFundsExceptionWhenUserTriesToWithdrawMoreThanBalance() {
        assertThrows(InsufficientFundsException.class, () -> transactionManager.makeWithdrawal(account, BigDecimal.valueOf(100_000)));
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