package myManagedBean;

import beans.NewUserBeanLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named(value = "userBean")
@RequestScoped
public class userBean implements Serializable {

    @EJB
    private NewUserBeanLocal newUserBean;

    //attributes of user table
    private int uid;
    private String username;
    private String password;
    private String address;
    private String usertype;
    private String message;
    
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void create() {
        uid = newUserBean.createUser(username,password,address,usertype,message);
    }

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
      int id;
      id = newUserBean.createUser("joe","1D10T?","Castletroy, Limerick","customer","Hello I am a cutomer here !");
      System.out.println("ID : "+id);
      id = newUserBean.createUser("toor","4uIdoO!","Limerick City","admin","Hi I am Admin"); 
      System.out.println("ID : "+id);
    }
    public userBean() {
        uid = 0;
    }

    public String notification() {
        if (uid == 0) return "";
        else return "New user with id " + uid + " created!!!";
    }
    
}
