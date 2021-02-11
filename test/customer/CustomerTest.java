package customer;

import account.Account;
import account.AccountType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    Customer customerOne;
    Customer customerTwo;

    @BeforeEach
    void setUp() {
        customerOne = new Customer("Max", "James", "testEmail", "09065360361", 1234,
                "semicolon", "savings");
        customerTwo = new Customer("Max", "James", "testEmail", "09065360361",
                1234, "address", "current");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCustomerValuesInitializedOnCreation() {
        assertEquals("Max", customerOne.getFirstName());
        assertEquals("James", customerOne.getLastName());
        assertEquals(1234, customerOne.getBvn());
        assertEquals("09065360361", customerOne.getPhoneNumber());
        assertEquals("semicolon", customerOne.getAddress());
        assertNotNull(customerOne.getAccounts());
        assertEquals("testEmail", customerTwo.getEmail());
        assertEquals(AccountType.SAVINGS, customerOne.getAccounts().get(0).getAccountType());
        assertEquals(AccountType.CURRENT, customerTwo.getAccounts().get(0).getAccountType());
    }

    @Test
    void testCustomerCanChangeHisDetails() {
        customerOne.setEmail("newEmail");
        customerOne.setFirstName("newFirstName");
        customerOne.setLastName("newLastName");
        customerOne.setPhoneNumber("newPhoneNumber");
        customerOne.setAddress("newAddress");
        assertEquals("newEmail", customerOne.getEmail());
        assertEquals("newFirstName", customerOne.getFirstName());
        assertEquals("newLastName", customerOne.getLastName());
        assertEquals("newPhoneNumber", customerOne.getPhoneNumber());
        assertEquals("newAddress", customerOne.getAddress());
    }

    @Test
    void testCustomerCanAddANewAccount() {
        customerOne.newAccount("current");
        assertEquals(2, customerOne.getAccounts().size());
        assertEquals(AccountType.CURRENT, customerOne.getAccounts().get(1).getAccountType());
    }

    @Test
    void testCustomerIdIsGeneratedWhenACustomerObjectIsCreated() {
        assertNotNull(customerOne.getCustomerId());
    }

    @Test
    void testCustomerIdIsPrefixedWithDefaultCustomerAccountNumber() {
        Account defaultAccount = customerOne.getAccounts().get(0);
        assertTrue(customerOne.getCustomerId().startsWith(defaultAccount.getAccountNumber()));
    }

}