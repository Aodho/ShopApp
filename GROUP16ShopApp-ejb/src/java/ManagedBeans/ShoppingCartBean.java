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

import Ent.Customer;
import Ent.Product;
import Ent.PurchaseOrder;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateful;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateful
public class ShoppingCartBean implements ShoppingCartBeanLocal {
    @Resource(mappedName = "java:app/MyMsgQueue")
    private Queue java_appMyMsgQueue;
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    private HashMap<String, Integer> items = new HashMap<>();
    
    @PersistenceContext(unitName = "Group16-ejbPU")
    private EntityManager eManager;
    private static final Logger LOGGER = Logger.getLogger(ShoppingCartBean.class.getName());
    private String userName;
    
    @Override
    public HashMap<String, Integer> getCartItems() {
        return items;
    }
    
    public void persist(Object object) {
        eManager.persist(object);
    }
    
    @Override
    public String checkout() 
    {
        NewUserBean nb = new NewUserBean();
        this.userName = nb.getUserName();
        
        if(runCheckOut())
        {
            String message = getItemList();
            items.clear();
            return message;
        }
        else
        {
            items.clear();
            return getItemList();
        }
    }
    
    private void sendJMSMsgToCustomMsgQueue(String messageData) {
        context.createProducer().send(java_appMyMsgQueue, messageData);
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
        
        LOGGER.info("Logger Name:" + LOGGER.getName());
        String temp = String.format("%1$-20s %2$-20s %3$-20s", id, quantity, "Added into the cart");
        
        //message driven bean
        sendJMSMsgToCustomMsgQueue(temp);
        //command line server log file
        LOGGER.info(temp);
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
            LOGGER.info("Logger Name:" + LOGGER.getName());
            String temp = String.format("%1$-20s %2$-20s %3$-20s", id, quantity, "Remove Item From Cart");
            
            //message driven bean
            sendJMSMsgToCustomMsgQueue(temp);
            //command line server log file
            LOGGER.info(temp);
        } 
        else 
        {
            // final quantity > 0 - adjust quantity
            items.put(id, orderQuantity);
            LOGGER.info("Logger Name:" + LOGGER.getName());
            String temp = String.format("%1$-20s %2$-20s %3$-20s", id, quantity, "Remove from cart");
            
            //message driven bean
            sendJMSMsgToCustomMsgQueue(temp);
            //command line server log file
            LOGGER.info(temp); }
        
         }

    @Override
    public void cancel() 
    {
        // no action required - annotation @Remove indicates
        // that calling this method should remove the EJB which will
        // automatically destroy instance variables
        // empty storage
        writeToLogFile(this.userName, "Cancelled");
        items.clear();
    }
    
    @Override
    public void clearItems(){
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
                message += "Product: " + k + ", Quantity: " + items.get(k);
            }
            return message;
        }
        else
            return "No items selected or quantity execeeded!";
    }
    
    @Override
    public boolean runCheckOut()
    {
        if(checkIfValidOrder())
        {
            //valid order so update the DB
            updateDB();
            
            writeToLogFile(this.userName, "Confirmed");
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean checkIfValidOrder()
    {        
        Iterator it = items.entrySet().iterator();
        while (it.hasNext()) 
        {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            Query qry = eManager.createNamedQuery("Product.findByDescription");
            qry.setParameter("description", pair.getKey());
            List<Product> isin = qry.getResultList();
            if(isin.get(0).getDescription().equals(pair.getKey()))
            {
                int orderQty = Integer.parseInt(pair.getValue().toString());
                if(isin.get(0).getQuantityOnHand() < orderQty)
                {
                    return false;
                }
            }  
        }
        return true;
    }
    
    @Override
    public void updateDB()
    {
        //update the DB with the new order(decrement the quantity of the ordered products)
        Iterator it = items.entrySet().iterator();
        while (it.hasNext()) 
        {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            decrement(pair.getKey().toString(), pair.getValue().toString());
            createPOrder(pair.getKey().toString(), (int)pair.getValue());
        }
    }

    @Override
    public boolean decrement(String title, String amount)
    {      
        Query qry = eManager.createNamedQuery("Product.findByDescription");
        qry.setParameter("description", title);
        List <Product> isin = qry.getResultList();
        Product prod = isin.get(0);
        int am = Integer.parseInt(amount);
        if(isin.isEmpty())
        {
            return false;
        }
        else
        { 
            if(prod.getQuantityOnHand() <= am)
            {
                prod.setQuantityOnHand(0);
                eManager.persist(prod);
                return true;
            }
            if(prod.getQuantityOnHand() > 0)
            {
                prod.setQuantityOnHand(prod.getQuantityOnHand() - am);
                eManager.persist(prod);
                return true;
            }
            return true;
        }
    }
    @Override
    public void createPurchaseOrder(long cID, int pID, int qty) 
    {
         try
        {
         Query qry =eManager.createNamedQuery("PurchaseOrder.getHighestPurchaseOrderID");
        int id=(int) qry.getSingleResult()+1;
        
        short value = qty > Short.MAX_VALUE ? Short.MAX_VALUE : qty < Short.MIN_VALUE ? Short.MIN_VALUE : (short)qty;
        
        PurchaseOrder por=new PurchaseOrder();
        Product pdt=new Product(pID);
        
        Customer ctm = new Customer(25);
        
        Date dt = new Date();
        
        BigDecimal bValue = new BigDecimal(200);
        
        por.setCustomerId(ctm);
        por.setFreightCompany("Fedex");
        por.setOrderNum((Integer)id);
        por.setProductId(pdt);
        por.setQuantity(value);
        por.setSalesDate(dt);
        por.setShippingDate(dt);
        por.setShippingCost(bValue);
        
        eManager.persist(por);     
        
        System.out.println("purchase order added");
        }
        catch(Exception ex)
        {
         System.out.println(ex.toString());
         System.out.println("purchase order not added");
        }
    }
    
    @Override
    public void createPOrder(String desc, int qty)
    {
        NewUserBean nub = new NewUserBean();
        Query qry = eManager.createNamedQuery("Product.findByDescription");
        qry.setParameter("description", desc);
        List <Product> isin = qry.getResultList();
        Product pro = isin.get(0);
        long temp = 101;
        int pid = pro.getProductId();
        eManager.persist(pro);       
        createPurchaseOrder(temp, pid, qty);
        
    }
        
    @Override
    public void writeToLogFile(String user, String status)
    {
        LOGGER.info("Logger Name:" + LOGGER.getName());
        String value = String.format("%1$-10s %2$-50s %3$-10s %4$-10s", "User", "|Product", "|Quantity", "|Status");
        //message driven bean
        sendJMSMsgToCustomMsgQueue(value);
        
        //command line server log file
        LOGGER.info(value);
        
        Iterator it = items.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            String temp = String.format("%1$-10s %2$-50s %3$-10s %4$-10s", user, "|" + pair.getKey(), "|" + pair.getValue(), "|" + status);
      
            //message driven bean
            sendJMSMsgToCustomMsgQueue(temp);
            
            //command line server log file
            LOGGER.info(temp);
        }
    }
    
}
