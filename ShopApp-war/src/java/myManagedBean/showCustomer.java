package myManagedBean;


import Ent.Customer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named(value = "showCustomer")
@RequestScoped
public class showCustomer {

    @PersistenceContext(unitName = "OnlineShoppingApplication-warPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    

    
    
    private String custName;
   
    private int custID;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }
    
    public List<Customer> getCustomerByName() {

        // create named query and set parameter
        Query query = em.createNamedQuery("Customer.findByName")
                .setParameter("name", custName);
        // return query result
        return query.getResultList();
    }
    
     public List<Customer> getCustomerByID() {

        // create named query and set parameter
        Query query = em.createNamedQuery("Customer.findByCustomerId")
                .setParameter("customerId", custID);
        // return query result
        return query.getResultList();
    }
     
     public List<Customer> getAllCustomers() {

        // create named query and set parameter
        Query query = em.createNamedQuery("Customer.findAll");
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
