package beans;

import java.util.HashMap;
import javax.ejb.Local;

@Local
public interface ShoppingCartBeanLocal
{
    public void addItem(String id, int quantity);
    public void removeItem(String id, int quantity);
    public void checkout();
    public void cancel();
    public String getItemList(); 
    public HashMap<String, Integer> getCartItems();
    public void runCheckOut();
    public void writeToLogFile(String user, String status);
    public boolean checkIfValidOrder();
    public void updateDB();
    public void createPOEntry();
    public boolean decrement(String title, String amount);
    public void clearItems();
    
}
