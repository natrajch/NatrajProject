package banking;

/**
 *
 * Private Variables:<br>
 * {@link #accountNumber}: Long<br>
 * {@link #bank}: Bank<br>
 */
public class Transaction implements TransactionInterface {
	private Long accountNumber;
	private Bank bank;

	/**
	 *
	 * @param bank
	 *            The bank where the account is housed.
	 * @param accountNumber
	 *            The customer's account number.
	 * @param attemptedPin
	 *            The PIN entered by the customer.
	 * @throws Exception
	 *             Account validation failed.
	 */
	public Transaction(Bank bank, Long accountNumber, int attemptedPin) throws BankException {
		 
		if(bank!=null) {
			 if(!bank.authenticateUser(accountNumber, attemptedPin)) {
				 throw new BankException(String.format("Incorrect Pin entered for account number %s", accountNumber), "INVALID_PIN");
			 }
			 this.accountNumber = accountNumber;
			 this.bank = bank;
		}
	}

	public double getBalance() throws BankException {
		
        return bank.getBalance(accountNumber);
	}

	public void credit(double amount) throws BankException {
		 bank.credit(accountNumber, amount);
	}

	public boolean debit(double amount) throws BankException{
		
		return  bank.debit(accountNumber, amount);
	}
}
