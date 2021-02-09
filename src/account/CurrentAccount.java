package account;

import java.security.SecureRandom;

public class CurrentAccount extends Account {
    public CurrentAccount() {
        this.accountType = AccountType.CURRENT;
        String savingsAccountNumberPrefix = "CA";
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
