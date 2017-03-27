package myManagedBean;


import Ent.Product;
import beans.productBeanLocal;
import beans.NewUserBeanLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
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
    
    @EJB
    private productBeanLocal productBean;
    
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
    
    //Method to get product by name
    public List<Product> getProductByName() {
      List<Product> listProduct= productBean.getProductByName(productName);
      return listProduct;
    }

    //Method to get product by Id
    public List<Product> getProductByID() {
    List<Product> listProduct= productBean.getProductByID(productId);
      return listProduct;
    }
    
    //Method to get All product
    public List<Product> getAllProducts() {
    List<Product> listProduct= productBean.getAllProducts();
      return listProduct;
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

   
    


