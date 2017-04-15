/*
 *
 * Aaron Dias              16068963 
 * Conor McDonnell   16120604
 * Hugh Mullins           16106989
 * Sunday Jimoh         16090462
 * Samaa Alsafwani   16087941
 *
 */
 
package Login;
import ManagedBeans.NewUserBeanLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import staticFilters.post;

@ManagedBean
@SessionScoped
public class login implements Serializable {
    
    //making ejb newuserbean object
    @EJB
    private NewUserBeanLocal newUserBean;
	
    //Creates a new instance of login
    public login() 
    {
    }
    
    //required attributes
    private String pwd;//passwword
    private String msg;//message
    private String user;//user
    private String token;//token
        
    //getter for token
    public String getToken() {
        return token;
    }
    
    //gettter for username
	public String getUser() {
		return user;
	}
 
    //setter for username
	public void setUser(String user) {
		this.user = user;
	}
        
    //getter for password
	public String getPwd() {
            return pwd;
	}

    //setter for password
	public void setPwd(String pwd) {
	this.pwd = pwd;
	}

    //gettter for message
	public String getMsg() {
		return msg;
	}

    //setter for message
	public void setMsg(String msg) {
		this.msg = msg;
	}
    //return userID by passing username and password
    public long userID()
   {
        long id = newUserBean.getUserID(user, pwd);
        return id;
    }
        
	//validate login
	public String validateUsernamePassword() 
        {
                //Clean user name and password against Xss attacks
                String cleanuser = XssFilter.clean(user);
                String cleanpwd = XssFilter.clean(pwd);
                //return true or false by validating username and password 
		boolean valid = newUserBean.validate(user, pwd);
                //return true or false on wether the user has the correct rights
		boolean rights = newUserBean.checkRights(user, pwd);
		if (valid) {
                //if validate http session set to current session 
                HttpSession session = SessionBean.getSession();
                newUserBean.setUserName(user);
		if(rights){
                          post.generateToken();//generets a token 
                          this.token=post.getToken();//gets the generated token
                          session.setAttribute("username", user);
                          session.setAttribute("type","admin");//assign the user type to the session as needs to be checked ,no user should have access to admin panel
                          session.setAttribute("token",post.getToken());
                          session.setMaxInactiveInterval(20*60);
						  return "index";//redirect to correct user page"admin panel
                      }
                else
                {
                          post.generateToken();//generate token
                          this.token=post.getToken(); //get the generated token
                          session.setAttribute("username", user);
                          session.setAttribute("type","user");//assign the usertype
                          session.setAttribute("token",post.getToken());
                          session.setMaxInactiveInterval(20*60);
                     
                          return "index";//redirect to normal user start page
                }
			
			
		} else {//message if validate failed
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Password",
							"Please enter correct username and Password"));
			return "login";
		}
	}
	//logout event, invalidate session
	public String logout() {
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		return "login";
	}
}
