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
import javax.ejb.Local;

@Local
public interface NewUserBeanLocal {
    
    //method to add users
    public int createUser(String username,String password,String address,String usertype,String message);
    //checking if customer exist
    public boolean isCustomerExist();
    //check is username and password validate
    public boolean validate(String user,String pwd);
    //check is user rights
    public boolean checkRights(String user,String pwd);
    //method to get details of current user
    public List<USERTABLE> getCurrentUserDetails(long id);
    //getting current userId
    public long getUserID(String user,String pwd);
    //profile editing method
    public boolean update(long id, String username, String address, String message);
    //customer list by name
    public List<USERTABLE> getCustomerListByName(String name);
    //customer list by ID
    public List<USERTABLE> getCustomerListByID(long id);
    //setting user name
    public void setUserName(String userName);
    //getting username
    public String getUserName();
    //All customer list 
    public List<USERTABLE> getAllCustomer();
}
