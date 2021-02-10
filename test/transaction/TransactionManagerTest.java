package transaction;

import account.Account;
import account.SavingsAccount;
import exceptions.InsufficientFundsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionManagerTest {
    Account account;
    TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        account = new SavingsAccount();
        transactionManager = new TransactionManager();
        transactionManager.makeDeposit(account, BigDecimal.valueOf(10_000));
    }

    @AfterEach
    void tearDown() {
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
    void testTransactionManagerCanMakeATransferTransaction() {

    }
}