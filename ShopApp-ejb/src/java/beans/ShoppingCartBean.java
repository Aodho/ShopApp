package beans;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
public class ShoppingCartBean implements ShoppingCartBeanLocal
{

    private HashMap<String, Integer> items = new HashMap<>();

    public HashMap<String, Integer> getCartItems() {
        return items;
    }
    
    @Override
    public void addItem(String id, int quantity) {
        // obtain current number of items in cart
        Integer orderQuantity = items.get(id);
        if (orderQuantity == null) {
            orderQuantity = 0;
        }
        // adjust quantity and put back to cart
        orderQuantity += quantity;
        items.put(id, orderQuantity);
    }

    @Override
    public void removeItem(String id, int quantity) {
        // obtain current number of items in cart
        Integer orderQuantity = items.get(id);
        if (orderQuantity == null) {
            orderQuantity = 0;
        }
        // adjust quantity and put back to cart
        orderQuantity -= quantity;
        if (orderQuantity <= 0) {
            // final quantity less equal 0 - remove from cart
            items.remove(id);
        } else {
            // final quantity > 0 - adjust quantity
            items.put(id, orderQuantity);
        }
        
    }

    @Override
    @Remove
    public String checkout() {
        // dummy checkout method that just returns message for successful 
        // checkout
        String message = "You checked out the following items:\n<br>" + getItemList();
        if(items.isEmpty())
        {
            message = "No items were selected!";
            
        }
        // empty storage
        items.clear();
        return message;
    }

    @Override
    @Remove
    public void cancel() {
        // no action required - annotation @Remove indicates
        // that calling this method should remove the EJB which will
        // automatically destroy instance variables
        // empty storage
        items.clear();
    }

    @Override
    public String getItemList() {
        String message = "";
        Set<String> keys = items.keySet();
        Iterator<String> it = keys.iterator();
        String k;
        while (it.hasNext()) {
            k = it.next();
            message += k + ", quantity: " + items.get(k) + "; \n<br>";
        }
        return message;
    }
    
}
