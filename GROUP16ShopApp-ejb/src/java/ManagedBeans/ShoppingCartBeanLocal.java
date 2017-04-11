/*
 *
 * Aaron Dias              16068963 
 * Conor McDonnell   16120604
 * Hugh Mullins           16106989
 * Sunday Jimoh         16090462
 * Samaa Alsafwani   16087941
 *
 */
package ManagedBeans;

import java.util.HashMap;
import javax.ejb.Local;

@Local
public interface ShoppingCartBeanLocal
{
    // adds a number items from the shopping cart. 
    public void addItem(String id, int quantity);
    // Removes a number items from the shopping cart. If quantity exceeds the number of present items, number is set to 0.
    public void removeItem(String id, int quantity);
	// Proceed with the checkout by asking for billing information. Checkout will terminate the current session for the EJB. returns message to user for check out
    public String checkout();
    // Cancels the current ordering process. Cancel will terminate the current session for the EJB.
    public void cancel();
    // Returns a string representing content of shopping cart
    public String getItemList();  
    // Returns a Hash-map of the Checkout cart items
    public HashMap<String, Integer> getCartItems();
    // Runs the checkout functionality returns whether check out was successful
    public boolean runCheckOut();
    // Writes to log output
    public void writeToLogFile(String user, String status);
    // Returns a boolean if the order is valid or not
    public boolean checkIfValidOrder();
    // Updates the Database with the new order
    public void updateDB();
    // Creates a new Purchase order entry
    public void createPOrder(String desc, int qty); 
    // Decrements the product table
    public boolean decrement(String title, String amount);
    // Method to clear items
    public void clearItems(); 
    // Method to create new purchase order entry
    public void createPurchaseOrder(long cID, int pID, int qty); 
}
