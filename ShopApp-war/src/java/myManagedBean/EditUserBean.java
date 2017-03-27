package myManagedBean;

import beans.NewUserBeanLocal;
import Ent.G13USERS;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

@Named(value = "editUserBean")
@SessionScoped
public class EditUserBean implements Serializable {

     @EJB
    private NewUserBeanLocal newUserBean;
    
    public EditUserBean() {
        id = 1;
    }
    
    private long id;
    private String username;
    private String address;
    private String message;
    private G13USERS user;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public G13USERS getUser() {
        return user;
    }

    public void setUser(G13USERS user) {
        this.user = user;
    }
    
    public void changeUserDetail(long id,String username,String address,String message)
    {
        System.out.println("id : "+id+" username : "+username+" Address : "+address+" message : "+message);
        newUserBean.update(id, username, address, message);
    }
}
