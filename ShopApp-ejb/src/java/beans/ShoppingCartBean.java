package beans;

import Ent.Product;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateful
public class ShoppingCartBean implements ShoppingCartBeanLocal {

    private HashMap<String, Integer> items = new HashMap<>();
    @PersistenceContext(unitName = "OnlineShoppingApplication-ejbPU")
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(ShoppingCartBean.class.getName());
    
    @Override
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
        } 
        else 
        {
            // final quantity > 0 - adjust quantity
            items.put(id, orderQuantity);
        }
        
    }

    @Override
    //@Remove
    public void checkout() 
    {
        runCheckOut();
        //items.clear();
    }
    
    @Override
    public void clearItems()
    {
        items.clear();
    }

    @Override
    //@Remove
    public void cancel() {
        writeToLogFile("Joe", "Cancelled");
        clearItems();
    }

    @Override
    public String getItemList() 
    {
        if(!items.isEmpty())
        {
            System.out.println("Items not empty");
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
        else
            return "No items selected or quantity execeeded!";
    }
    
    @Override
    public void runCheckOut()
    {
        System.out.println("ShoppingCartBean: runCheckOut()");
        //Logging logFile;
        if(checkIfValidOrder())
        {
            //valid order so update the DB
            updateDB();
            writeToLogFile("Joe", "Confirmed");
        }
        else
        {
            //display error to customer that order was not successful
            clearItems();
            System.out.println("Order invalid");
        }
    }
    
    @Override
    public void writeToLogFile(String user, String status)
    {
        System.out.println("ShoppingCartBean: writeToLogFile()");
        LOGGER.info("Logger Name:" + LOGGER.getName());
        String value = String.format("%1$-10s %2$-50s %3$-10s %4$-10s", "User", "|Product", "|Quantity", "|Status");
        LOGGER.info(value);
        Iterator it = items.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            String temp = String.format("%1$-10s %2$-50s %3$-10s %4$-10s", user, "|" + pair.getKey(), "|" + pair.getValue(), "|" + status);
            LOGGER.info(temp);
        }
    }
    
    @Override
    public boolean checkIfValidOrder()
    {        
        System.out.println("ShoppingCartBean: checkIfValidOrder()");
        Iterator it = items.entrySet().iterator();
        while (it.hasNext()) 
        {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            Query q = em.createNamedQuery("Product.findByDescription");
            q.setParameter("description", pair.getKey());
            List<Product> isin = q.getResultList();
            if(isin.get(0).getDescription().equals(pair.getKey()))
            {
                int orderQty = Integer.parseInt(pair.getValue().toString());
                if(isin.get(0).getQuantityOnHand() < orderQty)
                {
                    System.out.println("Inside loop to check orders, invalid order!");
                    return false;
                }
            }  
        }
        return true;
    }
    
    @Override
    public void updateDB()
    {
        Iterator it = items.entrySet().iterator();
        while (it.hasNext()) 
        {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            decrement(pair.getKey().toString(), pair.getValue().toString());
        }
        createPOEntry();
    }
    
    @Override
    public void createPOEntry()
    {
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
    @Override
    public boolean decrement(String title, String amount)
    {      
        Query q = em.createNamedQuery("Product.findByDescription");
        q.setParameter("description", title);
        List <Product> isin = q.getResultList();
        Product p = isin.get(0);
        int am = Integer.parseInt(amount);
        if(isin.isEmpty())
        {
            return false;
        }
        else
        { 
            if(p.getQuantityOnHand() <= am)
            {
                p.setQuantityOnHand(0);
                em.persist(p);
                return true;
            }
            if(p.getQuantityOnHand() > 0)
            {
                p.setQuantityOnHand(p.getQuantityOnHand() - am);
                em.persist(p);
                return true;
            }
            return true;
        }
    }
}
