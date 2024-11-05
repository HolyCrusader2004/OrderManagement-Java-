package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.ClientAgeValidator;
import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;
/**
 * The ClientBLL class handles business logic operations related to the Client entity.
 * It provides methods to interact with the ClientDAO for CRUD operations.
 */
public class ClientBLL {

	private final List<Validator<Client>> validators;
	private final ClientDAO clientDAO;
	/**
	 * Constructor for the ClientBLL class.
	 * Initializes the list of validators and the ClientDAO.
	 */
	public ClientBLL() {
		validators = new ArrayList<>();
		validators.add(new EmailValidator());
		validators.add(new ClientAgeValidator());
		clientDAO = new ClientDAO();
	}
	/**
	 * Finds a client by its id.
	 *
	 * @param id The id of the client to find
	 * @return The client object if found
	 * @throws NoSuchElementException If the client is not found
	 */
	public Client findClientById(int id) {
		Client client = clientDAO.findById(id);
		if (client == null) {
			throw new NoSuchElementException("The client with id = " + id + " was not found!");
		}
		return client;
	}
	/**
	 * Retrieves all clients from the database.
	 *
	 * @return A list of all clients
	 */
	public ArrayList<Client> findAllClients() {
        return (ArrayList<Client>) clientDAO.findAll();
	}
	/**
	 * Adds a new client to the database after validating it.
	 *
	 * @param client The client object to add
	 */
	public void addClient(Client client){
		for(Validator<Client> validator: validators) {
			validator.validate(client);
		}
		clientDAO.insert(client);
	}
	/**
	 * Deletes a client from the database by its id.
	 *
	 * @param id The id of the client to delete
	 * @throws NoSuchElementException If the client is not found
	 */
	public void deleteClient(int id){
		findClientById(id);
		clientDAO.delete(id);
	}
	/**
	 * Updates an existing client in the database after validating it.
	 *
	 * @param client The client object to update
	 * @throws NoSuchElementException If the client is not found
	 */
	public void updateClient(Client client){
		findClientById(client.getId());
		for(Validator<Client> validator: validators) {
			validator.validate(client);
		}
		clientDAO.update(client);
	}
}
