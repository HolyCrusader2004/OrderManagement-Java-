package bll;

import dao.BillDAO;
import model.Bill;

/**
 * The BillBLL class handles business logic operations related to the Bill entity.
 * It provides methods to interact with the BillDAO for CRUD operations.
 */
public class BillBLL {
    private final BillDAO billDAO;
    /**
     * Constructor for the BillBLL class.
     * Initializes the BillDAO.
     */
    public BillBLL() {
        this.billDAO = new BillDAO();
    }
    /**
     * Adds a bill to the database.
     *
     * @param bill The bill object to add
     */
    public void addBill(Bill bill){
        billDAO.insert(bill);
    }

}
