package myManagedBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import joshsPackage.Logging;

@Stateless
public class checkOutSessionBean {

    private Map productOrders; 
    private ArrayList<String> orders;
    
    public boolean runCheckOut(HashMap<String, Integer> cartItems)
    {
        Logging logFile;
        productOrders = cartItems;
        if(checkIfValidOrder())
        {
            updateDB();
            logFile = new Logging(orders, false, true);
            return true;
        }
        else
        {
            logFile = new Logging(orders, false, false);
            return false;
        }
    }
    
    public void populateProductOrders()
    {
        String [] tempOrder;
        
        for(int i = 0; i < orders.size(); i++)
        {
            tempOrder = orders.get(i).split(",");
            productOrders.put(tempOrder[0], tempOrder[1]);
        }
    }
    
    public ArrayList<String> getOrders()
    {
        return orders;
    }
    
    public void setOrders(ArrayList<String> orders)
    {
        this.orders = orders;
    }
    
    public boolean checkIfValidOrder()
    {
        boolean validOrder;
        
        validOrder = true;
        return validOrder;
    }
    
    public void updateDB()
    {
        createPOEntry();
    }
    
    public void createPOEntry()
    {
        
    }
}
