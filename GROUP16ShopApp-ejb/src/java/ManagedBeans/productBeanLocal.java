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

import Ent.Product;
import java.util.List;
import javax.ejb.Local;

@Local
public interface productBeanLocal {
    //method to add products
    public void addProduct(String title,String amount,String cost);
	//method to remove products
    public boolean removeProduct(String title);
	//method to increase the number of products
    public boolean increment(String title,String amount);
	//method to decrease the number of products
    public boolean decrement(String title,String amount);
	//write information out to the log file
    public void writeToLogFile(String user, String status, String productName, String quantity);
	//get a  product by its name
    public List<Product> getProductByName(String name);
	//get a product by its id number
    public List<Product> getProductByID(int id);
	//get all products in the database
    public List<Product> getAllProducts();
}
