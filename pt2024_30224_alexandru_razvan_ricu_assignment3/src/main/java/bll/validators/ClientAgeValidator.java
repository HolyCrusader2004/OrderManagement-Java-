package bll.validators;

import model.Client;
/**
 * The ClientAgeValidator class implements the Validator interface for validating the age of a Client object.
 * It ensures that the age of the client meets a minimum age requirement.
 */
public class ClientAgeValidator implements Validator<Client> {
	private static final int MIN_AGE = 16;
	/**
	 * Validates the age of a Client object.
	 *
	 * @param t The client object to validate
	 * @throws IllegalArgumentException If the client's age is below the minimum age limit
	 */
	public void validate(Client t) {
		if (t.getAge() < MIN_AGE) {
			throw new IllegalArgumentException("The Client Age limit is not respected!");
		}
	}

}
