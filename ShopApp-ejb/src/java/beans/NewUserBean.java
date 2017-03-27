
package beans;

import Ent.G13USERS;
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
        u.setUsername(username);
        u.setPassword(password);
        u.setAddress(address);
        u.setUsertype(usertype);
        u.setMessage(message);

        return 1;

    }
    
    public boolean isCustomerExist() {
        Query query = em.createNamedQuery("G13USERS.isUserExists").setParameter("username", "joe");
        if((long)query.getSingleResult() > 0)
        {
            System.out.println("User is exists");
         return true;
        }
        System.out.println("User Not exists");
        return false;
    }
    
    public boolean validate(String user,String pwd)
    {
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
}
