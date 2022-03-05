package banking;

/**
 * Abstract bank account class.<br>
 * <br>
 *
 * Private Variables:<br>
 * {@link #accountHolder}: AccountHolder<br>
 * {@link #accountNumber}: Long<br>
 * {@link #pin}: int<br>
 * {@link #balance}: double
 */
public abstract class Account implements AccountInterface{
	private AccountHolder accountHolder;
	private Long accountNumber;
	private int pin;
	private double balance;

	protected Account(AccountHolder accountHolder, Long accountNumber, int pin, double startingDeposit) throws BankException {
		
		if(!isAccountStateValid(accountHolder, accountNumber, pin, startingDeposit)){
			throw new BankException("Failed during Account creation. Reason : Account state received is invalid", "ACCOUNT_CREATION_ERROR");
		}
		this.accountHolder = accountHolder;
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = startingDeposit;
	}

	public AccountHolder getAccountHolder() {
		
        return accountHolder;
	}

	public boolean validatePin(int attemptedPin) {
		
		boolean isSuccess = false;
		if(pin==attemptedPin)
			isSuccess =  true;
        return isSuccess;
	}

	public double getBalance() {
		
        return balance;
	}

	public Long getAccountNumber() {
		
        return accountNumber;
	}

	public void creditAccount(double amount) {
		  if(amount > 0) {
		    	 balance +=  amount;
		  }
	}

	public boolean debitAccount(double amount) {
		boolean isDebited =  false;
		 if(balance  >=  amount) {
	    	 balance -=  amount;
	    	 isDebited = true;
	     }
       return isDebited;
	}
	
	
	//we can check here for minmum deposit if needed during account creation
	boolean isAccountStateValid(AccountHolder accountHolder, Long accountNumber, int pin, double startingDeposit){
		if(accountHolder!=null && accountHolder.getIdNumber()>0 && accountNumber>0 && pin>0) {
			return true;
		}
		return false;
	}
	
	
	
}
