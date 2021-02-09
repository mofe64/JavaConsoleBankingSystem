package account;

import java.security.SecureRandom;

public class SavingsAccount extends Account {
    public SavingsAccount() {
        this.accountType = AccountType.SAVINGS;
        String savingsAccountNumberPrefix = "SA";
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        stringBuilder.append(savingsAccountNumberPrefix);
        for (int i = 0; i < 5; i++) {
            stringBuilder.append(secureRandom.nextInt(9));
        }
        this.accountNumber = stringBuilder.toString();
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }
}
