package banking;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * Private Variables:<br>
 * {@link #accounts}: List&lt;Long, Account&gt;
 */
public class Bank implements BankInterface {
	private LinkedHashMap<Long, Account> accounts;
	static long counter;

	public Bank() {
		if(accounts==null) {
			accounts =  new LinkedHashMap<Long, Account>();
		}
	}

	private Account getAccount(Long accountNumber) throws BankException {
		Account targetAccount  = null;
		if(accountNumber>0 && accounts.containsKey(accountNumber)) {
				
			targetAccount =  accounts.get(accountNumber);
		}else {
			throw new BankException(String.format("Account not found for account number %s", accountNumber), "ACCOUNT_NOT_FOUND");
		}
        
	    return targetAccount;
    }
	

	public Long openCommercialAccount(Company company, int pin, double startingDeposit) throws BankException{
		
		long accountNum  = getUniqueNumber();
		CommercialAccount newCommercialAccount =  new CommercialAccount(company, accountNum, pin, startingDeposit);
		accounts.put(accountNum, newCommercialAccount);
        return accountNum;
	}
	

	public Long openConsumerAccount(Person person, int pin, double startingDeposit) throws BankException {
		
		long accountNum  = getUniqueNumber();
		ConsumerAccount newConsumerAccount =  new ConsumerAccount(person, accountNum, pin, startingDeposit);
		accounts.put(accountNum, newConsumerAccount);
        return accountNum;
	}
	
	
	long getUniqueNumber(){
		counter = counter+1;
		return counter;
	}

	public boolean authenticateUser(Long accountNumber, int pin) throws BankException {
		Account account =  getAccount(accountNumber);
		if(account.validatePin(pin)) {
			return true;
		}
        return false;
	}

	public double getBalance(Long accountNumber) throws BankException{
		Account account =  getAccount(accountNumber);
		return account.getBalance();
	}

	public void credit(Long accountNumber, double amount) throws BankException {
		Account account =  getAccount(accountNumber);
		account.creditAccount(amount);
	}

	public boolean debit(Long accountNumber, double amount) throws BankException  {
		Account account =  getAccount(accountNumber);
		return account.debitAccount(amount);
	}
}
