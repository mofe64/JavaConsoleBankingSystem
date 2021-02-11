package database;

import account.Account;
import account.CurrentAccount;
import account.SavingsAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transaction.DepositTransaction;
import transaction.Transactable;
import transaction.WithdrawTransaction;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    Database database = Database.getDatabaseInstance();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        database.nukeDatabase();
    }

    @Test
    void testAccountsCanBeAddedToDatabase() {
        Account testAccount = new SavingsAccount();
        Account testAccount2 = new CurrentAccount();
        database.addAccount(testAccount);
        assertEquals(1, database.getAllAccounts().size());
        assertEquals(testAccount, database.getAllAccounts().get(0));
        database.addAccount(testAccount2);
        assertEquals(2, database.getAllAccounts().size());
        assertEquals(testAccount2, database.getAllAccounts().get(1));
    }

    @Test
    void testTransactionsCaneBeAddedToTheDatabase() {
        Transactable depositTransaction = new DepositTransaction(new SavingsAccount().getAccountNumber(), BigDecimal.TEN);
        database.addTransaction(depositTransaction);
        assertEquals(1, database.getAllTransaction().size());
        assertEquals(depositTransaction, database.getAllTransaction().get(0));
    }

    @Test
    void testWeCanFindAnAccountByItsAccountNumber() {
        Account account1 = new SavingsAccount();
        Account account2 = new SavingsAccount();
        database.addAccount(account1);
        database.addAccount(account2);
        Optional<Account> accountOptional = database.findAccountByAccountNumber(account1.getAccountNumber());
        assertTrue(accountOptional.isPresent());
        Optional<Account> accountOptional2 = database.findAccountByAccountNumber(account2.getAccountNumber());
        assertTrue(accountOptional2.isPresent());
    }

    @Test
    void testWeCanFindATransactionByItsTransactionId() {
        Account account = new SavingsAccount();
        Transactable depositTransaction = new DepositTransaction(account.getAccountNumber(), BigDecimal.valueOf(100_000));
        Transactable depositTransaction2 = new DepositTransaction(account.getAccountNumber(), BigDecimal.valueOf(200_000));
        Transactable withdrawTransaction = new WithdrawTransaction(account.getAccountNumber(), BigDecimal.valueOf(150_000));
        database.addTransaction(depositTransaction);
        database.addTransaction(depositTransaction2);
        database.addTransaction(withdrawTransaction);
        Optional<Transactable> transactionOptional = database.findTransactionById(withdrawTransaction.getTransactionId());
        assertTrue(transactionOptional.isPresent());
    }

    @Test
    void testDatabaseCanDeleteAnAccount() {
        Account account = new SavingsAccount();
        database.addAccount(account);
        assertEquals(1, database.getAllAccounts().size());
        database.deleteAccount(account.getAccountNumber());
        assertEquals(0, database.getAllAccounts().size());
    }

    @Test
    void testDeletingAnAccountDeletesAllAssociatedTransactions() {
        Account account = new SavingsAccount();
        Transactable depositTransaction = new DepositTransaction(account.getAccountNumber(), BigDecimal.valueOf(100_000));
        Transactable depositTransaction2 = new DepositTransaction(account.getAccountNumber(), BigDecimal.valueOf(200_000));
        Transactable withdrawTransaction = new WithdrawTransaction(account.getAccountNumber(), BigDecimal.valueOf(150_000));
        Account account2 = new SavingsAccount();
        Transactable depositTransaction3 = new DepositTransaction(account2.getAccountNumber(), BigDecimal.valueOf(100_000));
        Transactable depositTransaction4 = new DepositTransaction(account2.getAccountNumber(), BigDecimal.valueOf(200_000));
        database.addAccount(account);
        database.addAccount(account2);
        database.addTransaction(depositTransaction);
        database.addTransaction(depositTransaction2);
        database.addTransaction(depositTransaction3);
        database.addTransaction(depositTransaction4);
        assertEquals(4, database.getAllTransaction().size());
        assertEquals(2, database.getAllAccounts().size());
        database.deleteAccount(account.getAccountNumber());
        assertEquals(2,database.getAllTransaction().size());
    }


}