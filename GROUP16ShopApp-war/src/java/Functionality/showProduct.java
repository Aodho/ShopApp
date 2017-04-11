/*
 *
 * Aaron Dias              16068963 
 * Conor McDonnell   16120604
 * Hugh Mullins           16106989
 * Sunday Jimoh         16090462
 * Samaa Alsafwani   16087941
 *
 */
package Functionality;


import Ent.Product;
import ManagedBeans.productBeanLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import Login.XssFilter;

@Named(value = "showProduct")
@RequestScoped
public class showProduct {
   
    //persistence for showing product
    @PersistenceContext(unitName = "Group16-warPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    
    //attributes
    private String productName;
    private int productId;
    
    //object of ejb product bean
    @EJB
    private productBeanLocal productBean;
        
    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    //getter for product id
    public int getProductId() {
        return productId;
    }

    //setter for product id
    public void setProductId(int productId) {
        this.productId = productId;
    }
        
    //getter for product name
    public String getProductName() {
        return productName;
    }

    //setter for product name
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    //Method to get product by Id
    public List<Product> getProductByID() {
        List<Product> listProduct= productBean.getProductByID(productId);
        return listProduct;
    }
    
    //Method to get product by name
    public List<Product> getProductByName() {
        XssFilter.clean(productName);
        List<Product> listProduct= productBean.getProductByName(productName);
        return listProduct;
    }
    
    //Method to get All product
    public List<Product> getAllProducts() {
        List<Product> listProduct= productBean.getAllProducts();
        return listProduct;
    }
}

   
    


