package beans;

import Ent.G13USERS;
import Ent.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class NewUserBean implements NewUserBeanLocal {
    @PersistenceContext(unitName = "OnlineShoppingApplication-ejbPU")
    private EntityManager em;
    public int createUser(String username,String password,String address,String usertype,String message)
    {
        G13USERS u = new G13USERS();
        persist(u);
        // set city and name and state
        u.setUsername(username);
        u.setPassword(password);
        u.setAddress(address);
        u.setUsertype(usertype);
        u.setMessage(message);

        // return id of new customer
        return 1;

    }
    
    public boolean isCustomerExist() {
        Query query = em.createNamedQuery("G13USERS.isUserExists").setParameter("username", "joe");
        if((long)query.getSingleResult() > 0)
        {
         return true;
        }
        return false;
    }
    
    public long getUserID(String user,String pwd)
    {
        Query query = em.createNamedQuery("G13USERS.getUserID");
        query.setParameter("username", user);
        query.setParameter("password", pwd);
        long id = (long)query.getSingleResult();
        return id;
    }
    
    public boolean validate(String user,String pwd)
    {
        // create named query and set parameter
        Query query = em.createNamedQuery("G13USERS.loginValidate");
        query.setParameter("username", user);
        query.setParameter("password", pwd);
        if((long)query.getSingleResult() > 0)
        {
         return true;
        }
        return false;
    }
    
    public void persist(Object object) {
        em.persist(object);
    }
    
    public List<G13USERS> getCurrentUserDetails(long id) {

        // create named query and set parameter
        Query query = em.createNamedQuery("G13USERS.findByUserId")
                .setParameter("uid", id);
        List<G13USERS> result = query.getResultList();
        return result;
    }
    
    public List<G13USERS> getCustomerListByName(String name)
    {
        // create named query and set parameter
        Query query = em.createNamedQuery("G13USERS.findByCustomerName");
        query.setParameter("username", name);
        query.setParameter("usertype", "customer");
        List<G13USERS> result = query.getResultList();
        return result;
    }
    
    public List<G13USERS> getCustomerListByID(long id)
    {
        // create named query and set parameter
        Query query = em.createNamedQuery("G13USERS.findByCustomerId");
        query.setParameter("uid", id);
        query.setParameter("usertype", "customer");
        List<G13USERS> result = query.getResultList();
        return result;
    }
    
    //id, username, address, message
    @Override
    public boolean update(long id, String username, String address, String message)
    {
        System.out.println("id : "+id+" username : "+username+" Address : "+address+" message : "+message);
        Query q= em.createNamedQuery("G13USERS.findByUserId");
        q.setParameter("uid", id);
        List <G13USERS> isin=q.getResultList();
        if(isin.isEmpty())
        {
            System.out.println("List is Empty");
         return false;
        }
         else
        {  
            System.out.println("List is not Empty");
            G13USERS u=isin.get(0);
            u.setUsername(username);
            u.setAddress(address);
            u.setMessage(message);
            em.persist(u);
          
          return true;
        }
    }
}
