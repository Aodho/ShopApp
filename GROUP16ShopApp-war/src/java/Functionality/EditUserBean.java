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

import ManagedBeans.NewUserBeanLocal;
import Ent.USERTABLE;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import Login.XssFilter;

@Named(value = "editUserBean")
@SessionScoped
public class EditUserBean implements Serializable {

    //bean to edit user profile
     @EJB
    private NewUserBeanLocal newUserBean;
    

    //Creates a new instance of EditUserBean
    public EditUserBean() {
        id = 1;
    }
    
    //required attributes
    private long id; //user id
    private String username; //username
    private String address; // address
    private String message; //message
    private USERTABLE user; //user
    
    //getter to get id 
    public long getId() {
        return id;
    }

    //setter to set id
    public void setId(long id) {
        this.id = id;
    }

    //getter to get username
    public String getUsername() {
        return username;
    }

    //setter to set username
    public void setUsername(String username) {
        this.username = username;
    }

    //getter to get address
    public String getAddress() {
        return address;
    }

    //setter to set address
    public void setAddress(String address) {
        this.address = address;
    }

    //getter to get message
    public String getMessage() {
        return message;
    }

    //setter to set message
    public void setMessage(String message) {
        this.message = message;
    }

    //getter to get user
    public USERTABLE getUser() {
        return user;
    }

    //setter to set user
    public void setUser(USERTABLE user) {
        this.user = user;
    }
    
    //updates and cleans user details, calling ejb bean to change user profile
    public void changeUserDetail(long id,String username,String address,String message)
    {
        String cleanusername = XssFilter.clean(username);
        String cleanaddress = XssFilter.clean(address);
        String cleanmessage = XssFilter.clean(message);
        newUserBean.update(id, cleanusername, cleanaddress, cleanmessage);
    }
}
