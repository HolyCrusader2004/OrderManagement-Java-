package model;
/**
 * The Client class represents a client entity with properties such as id, name, email, and age.
 *
 * This class provides constructors to initialize a client object with or without an id.
 * It also provides getter and setter methods for accessing and modifying the client's properties.
 */
public class Client {
	private int id;
	private String name;
	private String email;
	private int age;
	/**
	 * Default constructor for the Client class.
	 * Initializes an empty client object.
	 */
	public Client() {}
	/**
	 * Parameterized constructor for the Client class.
	 * Initializes a client object with the provided id, name, email, and age.
	 *
	 * @param id The unique identifier for the client
	 * @param name The name of the client
	 * @param email The email address of the client
	 * @param age The age of the client
	 */
	public Client(int id, String name, String email, int age) {
		//super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.age = age;
	}
	/**
	 * Parameterized constructor for the Client class.
	 * Initializes a client object without an id.
	 *
	 * @param name The name of the client
	 * @param email The email address of the client
	 * @param age The age of the client
	 */
	public Client(String name, String email, int age) {
		super();
		this.name = name;
		this.email = email;
		this.age = age;
	}
	/**
	 * Getter method to retrieve the id of the client.
	 *
	 * @return The id of the client
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter method to set the id of the client.
	 *
	 * @param id The id to set for the client
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter method to retrieve the name of the client.
	 *
	 * @return The name of the client
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method to set the name of the client.
	 *
	 * @param name The name to set for the client
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method to retrieve the age of the client.
	 *
	 * @return The age of the client
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Setter method to set the age of the client.
	 *
	 * @param age The age to set for the client
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Getter method to retrieve the email address of the client.
	 *
	 * @return The email address of the client
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter method to set the email address of the client.
	 *
	 * @param email The email address to set for the client
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Overrides the default toString method to provide a string representation of the Client object.
	 *
	 * @return A string representation of the Client object
	 */
	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + ", email=" + email + ", age=" + age + "]";
	}

}
