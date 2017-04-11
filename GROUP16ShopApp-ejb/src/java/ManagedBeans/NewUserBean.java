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

import Ent.USERTABLE;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;




@Stateless
public class NewUserBean implements NewUserBeanLocal {
    @PersistenceContext(unitName = "Group16-ejbPU")
    private EntityManager em;
    //User name 
    private String userName;
    //user ID
    private long id;
    //Getter for id
    public long getId() {
        return this.id;
    }
    
    //validate user exists
    @Override
    public boolean validate(String user,String pwd)
    {
        // create named query and set parameter
        Query query = em.createNamedQuery("USERTABLE.loginValidate");
        query.setParameter("username", user);
        query.setParameter("password", pwd);
        if((long)query.getSingleResult() > 0)
        {
         return true;
        }
        return false;
    } 

    //Adds a new customer to the database with given name, password, address, usertype, message
    @Override
    public int createUser(String username,String password,String address,String usertype,String message)
    {
        //user table object
        USERTABLE usr = new USERTABLE();
     
        //making it persistence to autogenerate id before adding details
        persist(usr);
        // setting username, password, address, usertype, message
        usr.setUsername(username);
        usr.setPassword(password);
        usr.setAddress(address);
        usr.setUsertype(usertype);
        usr.setMessage(message);

        // return id of new customer
        return 1;
    }
    
    //setting username
    @Override
    public void setUserName(String username)
    {
        this.userName = username;
    }
    
    //getting username
    @Override
    public String getUserName()
    {
        return this.userName;
    }

    //Returns list of customer with a given name
    @Override
    public boolean isCustomerExist() {
        // create named query and set parameter
        Query query = em.createNamedQuery("USERTABLE.isUserExists").setParameter("username", "Joe");
        // return query result
        if((long)query.getSingleResult() > 0)
        {
         return true;
        }
        return false;
    }
    
    //getting userID by passing username and password
    @Override
    public long getUserID(String user,String pwd)
    {
        //create Query
        Query query = em.createNamedQuery("USERTABLE.getUserID");
        query.setParameter("username", user);
        query.setParameter("password", pwd);
        //Execute Query
        long id = (long)query.getSingleResult();
        //setting current id 
        this.id = id;
        return id;
    }
	
    //Check the users rights	
    @Override
    public boolean checkRights(String user,String pwd)
    {
        // create named query and set parameter
        Query query = em.createNamedQuery("USERTABLE.loginValidate2");
        query.setParameter("username", user);
		query.setParameter("password", pwd);
        query.setParameter("usertype", "admin");
        // return query result
        if((long)query.getSingleResult() > 0)
        {
         return true;
        }
        return false;
    } 
	
    //used to persist objects
    public void persist(Object object) {
        em.persist(object);
    }
    	//return entire list of customers
    @Override
    public List<USERTABLE> getAllCustomer()
    {
        // create named query and set parameter
        Query query = em.createNamedQuery("USERTABLE.findAllCustomer");
        query.setParameter("usertype", "customer");
        //execute query
        List<USERTABLE> result = query.getResultList();
        return result;
    }
    
    //getting user details by passing id
    @Override
    public List<USERTABLE> getCurrentUserDetails(long id) {

        // create named query and set parameter
        Query query = em.createNamedQuery("USERTABLE.findByUserId")
                .setParameter("uid", id);
        //Query execute
        List<USERTABLE> result = query.getResultList();
        return result;
    }
    
    //getting customer list by name
    @Override
    public List<USERTABLE> getCustomerListByName(String name)
    {
        // create named query and set parameter
        Query query = em.createNamedQuery("USERTABLE.findByCustomerName");
        query.setParameter("username", name);
        query.setParameter("usertype", "customer");
        //Execute query
        List<USERTABLE> result = query.getResultList();
        return result;
    }
	
    //get customer by id
    @Override
    public List<USERTABLE> getCustomerListByID(long id)
    {
        // create named query and set parameter
        Query query = em.createNamedQuery("USERTABLE.findByCustomerId");
        query.setParameter("uid", id);
        query.setParameter("usertype", "customer");
        //execute query
        List<USERTABLE> result = query.getResultList();
        return result;
    }
    //update user with id, username, address, message
        @Override
    public boolean update(long id, String username, String address, String message)
    {
        //create named query and set parameter
        Query qry= em.createNamedQuery("USERTABLE.findByUserId");
        qry.setParameter("uid", id);
        //execute query
        List <USERTABLE> isin=qry.getResultList();
        //checking if user in the list
        if(isin.isEmpty())
        {
         return false;
        }
         else
        {  
            //Setting user details
            USERTABLE usr=isin.get(0);
            usr.setUsername(username);
            usr.setAddress(address);
            usr.setMessage(message);
            em.persist(usr);
          
          return true;
        }
    }
}
