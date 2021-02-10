package customer;

import account.Account;
import account.CurrentAccount;
import account.SavingsAccount;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private final int bvn;
    private String address;
    private final List<Account> accounts;

    public Customer(String firstName, String lastName, int bvn, String phoneNumber, String address, String accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bvn = bvn;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.accounts = new ArrayList<>();
        if (accountType.equalsIgnoreCase("savings")) {
            accounts.add(new SavingsAccount());
        } else {
            accounts.add(new CurrentAccount());
        }
    }

    public Customer(String firstName, String lastName, String email, String phoneNumber, int bvn, String address,
                    String accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bvn = bvn;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.accounts = new ArrayList<>();
        if (accountType.equalsIgnoreCase("savings")) {
            accounts.add(new SavingsAccount());
        } else {
            accounts.add(new CurrentAccount());
        }
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBvn() {
        return bvn;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public String getEmail() {
        if (email != null) {
            return email;
        } else {
            return "No Email On Record";
        }

    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    public void setLastName(String newLastName) {
        this.lastName = newLastName;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    public void setAddress(String newAddress) {
        this.address = newAddress;
    }

    public void newAccount(String type) {
        if (type.equalsIgnoreCase("savings")) {
            accounts.add(new SavingsAccount());
        } else {
            accounts.add(new CurrentAccount());
        }
    }
}
