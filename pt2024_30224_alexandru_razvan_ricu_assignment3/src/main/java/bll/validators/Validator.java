package bll.validators;

/**
 * The Validator interface defines a contract for classes that perform validation on objects of type T.
 *
 * @param <T> The type of object to be validated.
 */
public interface Validator<T> {
	/**
	 * Validates an object of type T.
	 *
	 * @param t The object to be validated.
	 */
	void validate(T t);
}
