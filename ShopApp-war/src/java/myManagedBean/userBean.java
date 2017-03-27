package myManagedBean;

import Ent.G13USERS;
import beans.NewUserBeanLocal;
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
    private long uid;
    private String username;
    private String password;
    private String address;
    private String usertype;
    private String message;
    
    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
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
     if(!isUserExist())
     {
       int id;
       id = newUserBean.createUser("joe","1D10T?","Castletroy, Limerick","customer","Hello, My name is Joe");
       id = newUserBean.createUser("ankit","1D10T?","Castletroy, Limerick","customer","Hello, My name is Ankit");
       id = newUserBean.createUser("josh","1D10T?","Castletroy, Limerick","customer","Hello, My name is Josh");
       id = newUserBean.createUser("emmylou","1D10T?","Castletroy, Limerick","customer","Hello, My name is Emmylou");
       id = newUserBean.createUser("vishrut","1D10T?","Castletroy, Limerick","customer","Hello, My name is Vishrut");
       id = newUserBean.createUser("toor","4uIdo0!","Limerick City","admin","Hello, My name is Toor and I'm admin here."); 
     }
     
    }

    public userBean() {
        
    }

    public String notification() {
        if (uid == 0) return "";
        else return "New user with id " + uid + " created!!!";
    }
    
    public List<G13USERS> getCurrentUserDetail(long uid)
    {
      List<G13USERS> uList = newUserBean.getCurrentUserDetails(uid);
      return uList;
    }
    
    public List<G13USERS> getCustomerListByName(String name)
    {
     List<G13USERS> uList = newUserBean.getCustomerListByName(name);
      return uList;
    }
    
    public List<G13USERS> getCustomerListByID(long id)
    {
     List<G13USERS> uList = newUserBean.getCustomerListByID(id);
      return uList;
    }
    public void setCurrentUserDetail(long uid)
    {
      List<G13USERS> uList = newUserBean.getCurrentUserDetails(uid);
      this.uid = uList.get(0).getId();
      this.username = uList.get(0).getUsername();
      this.address = uList.get(0).getAddress();
      this.message = uList.get(0).getMessage();
    }
}
