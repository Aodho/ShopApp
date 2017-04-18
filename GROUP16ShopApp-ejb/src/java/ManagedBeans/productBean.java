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

import Ent.Manufacturer;
import Ent.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class productBean implements productBeanLocal
{
    @Resource(mappedName = "java:app/MyMsgQueue")
    private Queue java_appMyMsgQueue;
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    
    @PersistenceContext(unitName = "Group16-ejbPU")
    private EntityManager eManager;
    private static final Logger LOGGER = Logger.getLogger(productBean.class.getName());
          
    //used to persist objects
    public void persist(Object object) {
        eManager.persist(object);
    }

    public void persist1(Object object) {
        eManager.persist(object);
    }      
    //Admin Increment Product Quantity Functionality
     @Override
    public boolean increment(String title,String amount) 
    {
        Query qry = eManager.createNamedQuery("Product.findByDescription");
        qry.setParameter("description", title);
        List <Product> isin=qry.getResultList();
	//return false if it doesn't exist
        if(isin.isEmpty())
        {
           return false;
        }
	//increment the product with the desired amount
        else
        {  
            int am=Integer.parseInt(amount);
            int currentAm=isin.get(0).getQuantityOnHand();
            Product prod = isin.get(0);
            prod.setQuantityOnHand(currentAm + am);
            eManager.persist(prod);
          
          return true;
        }
        
    }
    
    //Admin Decrement Product Quantity Functionality
    @Override
    public boolean decrement(String title,String amount)
    {      
        Query qry = eManager.createNamedQuery("Product.findByDescription");
        qry.setParameter("description", title);
        List <Product> isin=qry.getResultList();
        Product prod =isin.get(0);
        int am=Integer.parseInt(amount);
	//return false if it doesn't exist
        if(isin.isEmpty())
        {
            return false;
        }
        else
        { 
             //decrement amount  is greater than the amount of product set the amount of product to 0
            if(prod.getQuantityOnHand()<= am)
            {
                prod.setQuantityOnHand(0);
                eManager.persist(prod);
                return true;
            }		
            //decrement amount  is less than the amount of product take the decrement amount from  the amount  of product
            if(prod.getQuantityOnHand()>0)
            {
                prod.setQuantityOnHand(prod.getQuantityOnHand()- am);
                eManager.persist(prod);
                return true;
            }
            return true;
        }
    }
      
    //Admin Add Product Functionality
    @Override
    public void addProduct(String title, String amount, String cost)
    {
        Query qry= eManager.createNamedQuery("Product.getHighestProductId");
	//add 1 to the highest id
        int id=(int) qry.getSingleResult()+1;
        Product prod=new Product();
        Manufacturer m=new Manufacturer();   
        m.setManufacturerId(19986982);
        BigDecimal bigDecimalValue= new BigDecimal(cost);
        
        prod.setDescription(title);
        prod.setProductId(id);
        prod.setQuantityOnHand(Integer.parseInt(amount));
        prod.setMarkup(BigDecimal.ZERO);
        prod.setManufacturerId(m);
        prod.setPurchaseCost(bigDecimalValue);
        prod.setAvailable("true");
        writeToLogFile("admin", "Added", title, amount);
        eManager.persist(prod); 
        
    }
    
    //Admin Remove Product Functionality
    @Override
    public boolean removeProduct(String title) 
    {
        Query qry= eManager.createNamedQuery("Product.findByDescription");
	//query by the description of the product
        qry.setParameter("description", title);		
	//return false if it doesn't exist
        List <Product> isin=qry.getResultList();
        if(isin.isEmpty()){
            return false;
        }
	//remove the item
        else{  
            writeToLogFile("admin", "Removed", title, "ALL");
            eManager.remove(isin.get(0));
            return true;
        }
    }

    @Override
    public void writeToLogFile(String user, String status, String productName, String quantity)
    {
        String value = String.format("%1$-10s %2$-50s %3$-10s %4$-10s", "User", "|Product", "|Quantity", "|Status");
        //message driven bean
        sendJMSMsgToCustomMsgQueue(value);
        
        //command line server log file
        LOGGER.info(value);
        
        String temp = String.format("%1$-10s %2$-50s %3$-10s %4$-10s", user, "|" + productName, "|" + quantity, "|" + status);
        //message driven bean
        sendJMSMsgToCustomMsgQueue(temp);
        
        //command line server log file
        LOGGER.info(temp);
    }
    
    private void sendJMSMsgToCustomMsgQueue(String messageData) {
        context.createProducer().send(java_appMyMsgQueue, messageData);
    }
   
    //Method to get product by name
    @Override
    public List<Product> getProductByName(String productname) {
        // create named query and set parameter
        Query query = eManager.createNamedQuery("Product.findByDescription")
                .setParameter("description", productname);
        // return query result
        return query.getResultList();
    }

    //Method to get product by Id
    @Override
    public List<Product> getProductByID(int productId) {

        // create named query and set parameter
        Query query = eManager.createNamedQuery("Product.findByProductId")
                .setParameter("productId", productId);
        // return query result
        return query.getResultList();
    }
    
    //Method to get All product
    @Override
    public List<Product> getAllProducts() {
        System.out.println("GOT TO 3");
        // create named query and set parameter
        Query query = eManager.createNamedQuery("Product.findAll");
        // return query result
        return query.getResultList();
    }

}
