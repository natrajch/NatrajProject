package banking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.sun.tools.javac.util.StringUtils;

/**
 * Account implementation for commercial (business) customers.<br><br>
 *
 * Private Variables:<br>
 * {@link #authorizedUsers}: List&lt;Person&gt;<br>
 */
public class CommercialAccount  extends Account {
	private List<Person> authorizedUsers;

	public CommercialAccount(Company company, Long accountNumber, int pin, double startingDeposit) throws  BankException{
		super(company, accountNumber, pin, startingDeposit);
	}

	/**
	 * @param person The authorized user to add to the account.
	 */
	protected void addAuthorizedUser(Person person) {
		if(person!=null && person.isNotEmpty()) {
			if(authorizedUsers==null) {
				authorizedUsers =  new ArrayList();  //  dont need to worry this warning for java newer versions
			}
			authorizedUsers.add(person);
	     }
	}

    
	/**
	 * @param person
	 * @return true if person matches an authorized user in {@link #authorizedUsers}; otherwise, false.
	 */
	public boolean isAuthorizedUser(Person person) {
		
		if(authorizedUsers!=null && authorizedUsers.size()>0) {
			for(Person authorisedUser : authorizedUsers) {
				if(authorisedUser.equals(person)) {
					return true;
				}
			}
		}
        return false;
	}
}
