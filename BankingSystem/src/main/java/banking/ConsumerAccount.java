package banking;

public class ConsumerAccount  extends Account {
	
	
	
	public ConsumerAccount(Person person, Long accountNumber, int pin, double currentBalance) throws BankException {
		super(person, accountNumber, pin, currentBalance);
		
	}
}
