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

import Ent.USERTABLE;
import ManagedBeans.NewUserBeanLocal;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

@Named(value = "userBean")
@RequestScoped
public class userBean implements Serializable {

    @EJB
    private NewUserBeanLocal newUserBean;

    //attributes of user table
    private long uid; // userid
    private String username;//username
    private String password;//password
    private String address;//address
    private String usertype;//usertype
    private String message;//message
    
    //method to check if user exist
    public boolean isUserExist()
    {
     if(newUserBean.isCustomerExist())
     {
      return true;
     }
     else
         return false;
    }
   
    public void initialize()
    {
     if(!isUserExist())
     {
       int id;
       //adding hardcoded users-password 
       id = newUserBean.createUser("Joe","1D10T?","Castletroy, Limerick","customer","Hello, My name is Joe");
       id = newUserBean.createUser("Conor","blah","Galway City, Galway","customer","Hello, This is Conor");
       id = newUserBean.createUser("Sunday","1D10T?","Cork City, Cork","customer","Hello, My name is Sunday");
       id = newUserBean.createUser("Samaa","1D10T?","Limerick City, Limerick","customer","Hello, This is Samaa");
       id = newUserBean.createUser("Aaron","1D10T?","Castletroy, Limerick","customer","Hello, My name is Aaron");
       //adding hardcoded user- admin
       id = newUserBean.createUser("toor","4uIdo0!","Limerick City","admin","Hello, Toor is an admin here.");
       id = newUserBean.createUser("Hugh","hugh","Ennis, Clare","admin","Hello, Hugh is an admin here.");
     }
    }
    
    //getter for userid
    public long getUid() {
        return uid;
    }

    //setter for userid
    public void setUid(long uid) {
        this.uid = uid;
    }
      
    //getter for username
    public String getUsername() {
        return username;
    }

    //setter for username
    public void setUsername(String username) {
        this.username = username;
    }
    
    //getter for usertype
    public String getUsertype() {
        return usertype;
    }

    //setter for usertype
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
    
    //getter for password
    public String getPassword() {
        return password;
    }

    //setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    //getter for address
    public String getAddress() {
        return address;
    }

    //setter for address
    public void setAddress(String address) {
        this.address = address;
    }
    
    //getter for message
    public String getMessage() {
        return message;
    }
    
    //setter for userid
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void create() {
        uid = newUserBean.createUser(username,password,address,usertype,message);
    }
    
    //Creates a new instance of AddCustomerBean
    public userBean() {
    }

    //notifiaction when user added
    public String notification() {
        if (uid == 0) return "";
        else return "New user with id " + uid + " created!!!";
    }
    
    //method to call ejb bean getting All user detail
    public List<USERTABLE> getAllCustomerList()
    {
        List<USERTABLE> userList = newUserBean.getAllCustomer();
        return userList;
    }

    //method to call ejb bean getting user detail by name
    public List<USERTABLE> getCustomerListByName(String name)
    {
		List<USERTABLE> userList = newUserBean.getCustomerListByName(name);
		return userList;
    }
    
    //method to call ejb bean getting user detail by id
    public List<USERTABLE> getCustomerListByID(long id)
    {
		List<USERTABLE> userList = newUserBean.getCustomerListByID(id);
		return userList;
    }
        
    //method to call ejb bean getting current user detail by id
    public List<USERTABLE> getCurrentUserDetail(long uid)
    {
		List<USERTABLE> userList = newUserBean.getCurrentUserDetails(uid);
		return userList;
    }
   
    //method to call ejb bean to set user detail
    public void setCurrentUserDetail(long uid)
    {
      List<USERTABLE> userList = newUserBean.getCurrentUserDetails(uid);
      this.uid = userList.get(0).getId();
      this.username = userList.get(0).getUsername();
      this.address = userList.get(0).getAddress();
      this.message = userList.get(0).getMessage();
    }
}
