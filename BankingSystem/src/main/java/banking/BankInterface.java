package banking;

/**
 * The public methods for the {@link Bank} class.
 */
public interface BankInterface {

	/**
	 * Creates a new account and adds it to {@link Bank#accounts}.
	 *
	 * @param company
	 * @param pin
	 * @param startingDeposit
	 * @return The account number for the newly created account.
	 */
	public Long openCommercialAccount(Company company, int pin, double startingDeposit)  throws BankException;

	/**
	 * Creates a new account and adds it to {@link Bank#accounts}.
	 *
	 * @param person
	 * @param pin
	 * @param startingDeposit
	 * @return The account number for the newly created account.
	 */
	public Long openConsumerAccount(Person person, int pin, double startingDeposit)  throws BankException;

	/**
	 * @param accountNumber The account number for the transaction.
	 * @param pin
	 * @return true if authentication was successful.
	 */
	public boolean authenticateUser(Long accountNumber, int pin) throws BankException ;

	/**
	 * @param accountNumber The account number for the transaction.
	 * @return the balance of the account.
	 */
	public double getBalance(Long accountNumber) throws BankException ;

	/**
	 * @param accountNumber The account number for the transaction.
	 * @param amount The amount of money being deposited.
	 */
	public void credit(Long accountNumber, double amount) throws BankException ;

	/**
	 * @param accountNumber The account number for the transaction.
	 * @param amount
	 * @return true if amount could be withdrawn; otherwise, return false.
	 */
	public boolean debit(Long accountNumber, double amount) throws BankException ;
}
