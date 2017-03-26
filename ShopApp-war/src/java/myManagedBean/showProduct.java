package myManagedBean;


import Ent.Product;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;


@Named(value = "showProduct")
@RequestScoped
public class showProduct {
   
    
    @PersistenceContext(unitName = "OnlineShoppingApplication-warPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    
    private String productName;
   
    private int productId;
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public List<Product> getProductByName() {
        // create named query and set parameter
        Query query = em.createNamedQuery("Product.findByDescription")
                .setParameter("description", productName);
        // return query result
        return query.getResultList();
    }

    public List<Product> getProductByID() {

        // create named query and set parameter
        Query query = em.createNamedQuery("Product.findByProductId")
                .setParameter("productId", productId);
        // return query result
        return query.getResultList();
    }
    
   
    public List<Product> getAllProducts() {

        // create named query and set parameter
        Query query = em.createNamedQuery("Product.findAll");
        // return query result
        return query.getResultList();
    }

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
    
    
    

   
    }

   
    


