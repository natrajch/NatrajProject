package banking;

import java.util.Objects;
import java.util.Optional;

public class Person extends AccountHolder {
	
	private String firstName;
	private String lastName;

	public Person(String firstName, String lastName, int idNumber) {
		
		super(idNumber);
		this.firstName = firstName;
		this.lastName = lastName;
		
	}

	public String getFirstName() {
		
        return firstName;
	}

	public String getLastName() {
		
        return lastName;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, getIdNumber());
	}

	@Override
	public boolean equals(Object person) {
		if (this == person)
			return true;
		if (person == null)
			return false;
		if (getClass() != person.getClass())
			return false;
		Person other = (Person) person;
		return (firstName!=null && !firstName.isBlank() && other.firstName!=null && !other.firstName.isBlank()
				&& firstName.equals(other.firstName)
				&& lastName!=null && !lastName.isBlank() && other.lastName!=null && !other.lastName.isBlank()
				&& lastName.equals(other.lastName)
				&& getIdNumber()==other.getIdNumber());
	}

	//I can also use StringUtils 
	public boolean isNotEmpty(){
		if(getFirstName()!=null && !getFirstName().isBlank() &&
				getLastName()!=null && !getLastName().isBlank() &&
						getIdNumber()> 0) {
			return true;
		}
		return false;
	}
}
