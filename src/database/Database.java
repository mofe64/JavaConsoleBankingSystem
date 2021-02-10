//package database;
//
//import account.Account;
//import transaction.Transactable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Database {
//    private List<Transactable> transactions;
//    private List<Account> accounts;
//    private static Database databaseInstance = null;
//
//    private Database() {
//        transactions = new ArrayList<>();
//        accounts = new ArrayList<>();
//    }
//
//    public static Database getDatabaseInstance() {
//        if (databaseInstance == null) {
//            databaseInstance = new Database();
//        }
//        return databaseInstance;
//    }
//
//    public void addTransaction(Transactable newTransaction) {
//        transactions.add(newTransaction);
//    }
//
//    public void addAccount(Account newAccount) {
//        accounts.add(newAccount);
//    }
//
//}
