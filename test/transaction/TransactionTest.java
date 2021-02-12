package transaction;

import account.Account;
import account.SavingsAccount;
import exceptions.InsufficientFundsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    Account receivingAccount;
    Account sendingAccount;
    TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        receivingAccount = new SavingsAccount();
        sendingAccount = new SavingsAccount();
        transactionManager = new TransactionManager();
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
        assertNotNull(transaction.getTransactionId());
    }

    @Test
    void testWithdrawTransactionDetailsCanBeRetrieved() throws InsufficientFundsException {
        transactionManager.makeDeposit(sendingAccount, BigDecimal.valueOf(100.00));
        Transactable withdrawTransaction = transactionManager.makeWithdrawal(sendingAccount, BigDecimal.valueOf(100.0));
        assertEquals(BigDecimal.valueOf(100.0), withdrawTransaction.getTransactionAmount());
        assertEquals(TransactionStatus.SUCCESS, withdrawTransaction.getTransactionStatus());
        assertEquals(TransactionType.DEBIT, withdrawTransaction.getTransactionType());
        assertNotNull(withdrawTransaction.getTransactionId());
        assertEquals("Withdrawal Transaction", withdrawTransaction.getTransactionDescription());
        assertEquals(sendingAccount.getAccountNumber(), withdrawTransaction.getSendersAccountNumber());
        assertEquals("", withdrawTransaction.getRecipientsAccountNumber());
        assertNotNull(withdrawTransaction.getTransactionDate());
    }


    @Test
    void testDepositTransactionDetailsCanBeRetrieved() throws IllegalArgumentException {
        Transactable depositTransaction = transactionManager.makeDeposit(sendingAccount, BigDecimal.valueOf(100.0));
        assertEquals(BigDecimal.valueOf(100.0), depositTransaction.getTransactionAmount());
        assertEquals(TransactionStatus.SUCCESS, depositTransaction.getTransactionStatus());
        assertEquals(TransactionType.CREDIT, depositTransaction.getTransactionType());
        assertNotNull(depositTransaction.getTransactionId());
        assertEquals("Deposit Transaction", depositTransaction.getTransactionDescription());
        assertEquals("", depositTransaction.getSendersAccountNumber());
        assertEquals(sendingAccount.getAccountNumber() , depositTransaction.getRecipientsAccountNumber());
        assertNotNull(depositTransaction.getTransactionDate());
    }


    @Test
    void testTransactionStatusCanBeChanged() throws InsufficientFundsException {
        BigDecimal amount = BigDecimal.valueOf(150.60);
        Transactable transfer = new TransferTransaction(sendingAccount.getAccountNumber(), receivingAccount.getAccountNumber(),
                TransactionType.CREDIT, amount, "Payment");
        transfer.setTransactionStatus(TransactionStatus.SUCCESS);
        assertEquals(TransactionStatus.SUCCESS, transfer.getTransactionStatus());
        transfer.setTransactionStatus(TransactionStatus.FAILED);
        assertEquals(TransactionStatus.FAILED, transfer.getTransactionStatus());
        CreditTransaction creditTransaction = transactionManager.makeDeposit(sendingAccount, BigDecimal.valueOf(100.00));
        DebitTransaction debitTransaction = transactionManager.makeWithdrawal(sendingAccount, BigDecimal.valueOf(50.00));
        assertEquals(TransactionStatus.SUCCESS, debitTransaction.getTransactionStatus());
        assertEquals(TransactionStatus.SUCCESS, creditTransaction.getTransactionStatus());
        debitTransaction.setTransactionStatus(TransactionStatus.FAILED);
        creditTransaction.setTransactionStatus(TransactionStatus.FAILED);
        assertEquals(TransactionStatus.FAILED, debitTransaction.getTransactionStatus());
        assertEquals(TransactionStatus.FAILED, creditTransaction.getTransactionStatus());
    }

    @Test
    void testTransactionCanBeRolledBack() throws InsufficientFundsException {
        transactionManager.makeDeposit(sendingAccount, BigDecimal.valueOf(250_000.00));
        BigDecimal transferAmount = BigDecimal.valueOf(100_000.0);
        List<Transactable> transactions = transactionManager.makeTransfer(sendingAccount, receivingAccount, transferAmount, "Payment");
        assertEquals(transferAmount, receivingAccount.getBalance());
        assertEquals(BigDecimal.valueOf(150_000.00), sendingAccount.getBalance());
        transactionManager.rollBackTransaction(transactions.get(1), transactions.get(0));
        assertEquals(BigDecimal.valueOf(250_000.00), sendingAccount.getBalance());
        assertEquals(BigDecimal.valueOf(0.0), receivingAccount.getBalance());
    }

}