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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionBean 
{
    //give the current session 
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
		.getExternalContext().getSession(false);
    }

    //give the current request 
    public static HttpServletRequest getRequest() {
	return (HttpServletRequest) FacesContext.getCurrentInstance()
		.getExternalContext().getRequest();
    }

    //give the token detail
    public static String getToken(){
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
		.getExternalContext().getSession(true);
	return session.getAttribute("token").toString();
    }
    
    //give the current user name
    public static String getUserName() {
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
		.getExternalContext().getSession(false);
	return session.getAttribute("username").toString();
    }

    //give the current userid
    public static String getUserId() {
	HttpSession session = getSession();
            if (session != null)
		return (String) session.getAttribute("userid");
            else
		return null;
    }   
        
    //give the user type- customer/admin
    public static String getUserType(){
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
		.getExternalContext().getSession(true);
	return session.getAttribute("type").toString();
    }
}
