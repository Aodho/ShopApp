package beans;

import java.util.HashMap;
import javax.ejb.Local;

@Local
public interface ShoppingCartBeanLocal
{
    public void addItem(String id, int quantity);
    public void removeItem(String id, int quantity);
    public String checkout();
    public void cancel();
    public String getItemList(); 
    public HashMap<String, Integer> getCartItems();   
}
