package beans;

import javax.ejb.Local;

@Local
public interface NewUserBeanLocal {
    
    //method to add users
    public int createUser(String username,String password,String address,String usertype,String message);
    public boolean isCustomerExist();
    public boolean validate(String user,String pwd);
}
