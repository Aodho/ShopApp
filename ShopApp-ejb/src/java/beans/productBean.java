package beans;

import beans.NewUserBean;
import Ent.Manufacturer;
import Ent.Product;
import Ent.ProductCode;
import static Ent.Product_.available;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class productBean implements productBeanLocal
{

    @PersistenceContext(unitName = "OnlineShoppingApplication-ejbPU")
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(productBean.class.getName());
    private String uName;
    
    //Admin Add Product Functionality
    @Override
    public void addProduct(String title, String amount,String cost)
    {
        Query q= em.createNamedQuery("Product.getHighestProductId");
        int id=(int) q.getSingleResult()+1;
        Product pr=new Product();
        Manufacturer m=new Manufacturer();
       
        m.setManufacturerId(19985590);
        BigDecimal bigDecimalValue= new BigDecimal(cost);
        
        ProductCode pc=new ProductCode();
        pc.setProdCode("HW");
        pr.setDescription(title);
        pr.setProductId(id);
        pr.setQuantityOnHand(Integer.parseInt(amount));
        pr.setMarkup(BigDecimal.ZERO);
        pr.setManufacturerId(m);
        pr.setProductCode(pc);
        pr.setPurchaseCost(bigDecimalValue);
        pr.setAvailable("true");
        
        NewUserBean nb = new NewUserBean();
        this.uName = nb.getUserName();
        writeToLogFile("admin", "Added", title, amount);
        em.persist(pr); 
        
    }
    ////Admin Remove Product Functionality
    @Override
    public boolean removeProduct(String title) 
    {
        Query q= em.createNamedQuery("Product.findByDescription");
        q.setParameter("description", title);

        List <Product> isin=q.getResultList();
        if(isin.isEmpty())
        {
            return false;
        }
        else
        {  
            writeToLogFile(this.uName, "Removed", title, "ALL");
            em.remove(isin.get(0));
            return true;
        }
    }
    
    //Admin Increment Product Quantity Functionality
     @Override
    public boolean increment(String title,String amount) 
    {
        Query q= em.createNamedQuery("Product.findByDescription");
        q.setParameter("description", title);
        List <Product> isin=q.getResultList();
        if(isin.isEmpty())
        {
           return false;
        }
        else
        {  
            int am=Integer.parseInt(amount);
            int currentAm=isin.get(0).getQuantityOnHand();
            Product p=isin.get(0);
            p.setQuantityOnHand(currentAm + am);
            em.persist(p);
          
          return true;
        }
        
    }
    
    //Admin Decrement Product Quantity Functionality
    @Override
    public boolean decrement(String title,String amount)
    {      
        Query q= em.createNamedQuery("Product.findByDescription");
        q.setParameter("description", title);
        List <Product> isin=q.getResultList();
        Product p=isin.get(0);
        int am=Integer.parseInt(amount);
        if(isin.isEmpty())
        {
            return false;
        }
        else
        { 
            if(p.getQuantityOnHand()<= am)
            {
                p.setQuantityOnHand(0);
                em.persist(p);
                return true;
            }
            if(p.getQuantityOnHand()>0)
            {
                p.setQuantityOnHand(p.getQuantityOnHand()- am);
                em.persist(p);
                return true;
            }
            return true;
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }

    public void persist1(Object object) {
        em.persist(object);
    }
    
    @Override
    public void writeToLogFile(String user, String status, String productName, String quantity)
    {
        String value = String.format("%1$-10s %2$-50s %3$-10s %4$-10s", "User", "|Product", "|Quantity", "|Status");
        LOGGER.info(value);
        String temp = String.format("%1$-10s %2$-50s %3$-10s %4$-10s", user, "|" + productName, "|" + quantity, "|" + status);
        LOGGER.info(temp);
    }
    
    //Method to get product by name
    @Override
    public List<Product> getProductByName(String productname) {
        // create named query and set parameter
        Query query = em.createNamedQuery("Product.findByDescription")
                .setParameter("description", productname);
        // return query result
        return query.getResultList();
    }

    //Method to get product by Id
    @Override
    public List<Product> getProductByID(int productId) {

        // create named query and set parameter
        Query query = em.createNamedQuery("Product.findByProductId")
                .setParameter("productId", productId);
        // return query result
        return query.getResultList();
    }
    
    //Method to get All product
    @Override
    public List<Product> getAllProducts() {

        // create named query and set parameter
        Query query = em.createNamedQuery("Product.findAll");
        // return query result
        return query.getResultList();
    }

}
