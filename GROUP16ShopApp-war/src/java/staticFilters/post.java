/*
 *
 * Aaron Dias              16068963 
 * Conor McDonnell   16120604
 * Hugh Mullins           16106989
 * Sunday Jimoh         16090462
 * Samaa Alsafwani   16087941
 *
 */
package staticFilters;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class post 
{
    static String token;

    public static String getToken() {
        return token;
    }
    
    public static void postErrorRedirect(String url){
       
        FacesContext context = FacesContext.getCurrentInstance();
        String ErrorRedirect = url;
        ExternalContext exCon = FacesContext.getCurrentInstance().getExternalContext();

        try {
                exCon.redirect(ErrorRedirect);
            }catch (IOException e) {
                e.printStackTrace();
                FacesMessage message = new FacesMessage(e.getMessage());
                context.addMessage(null, message);
            }   
    }
 
           
    public static void generateToken(){
       post.token= (int) (Math.random()*50000005)+1+"";
    }
    
    public static String getHidden(String filed){
	   String value = FacesContext.getCurrentInstance().
		getExternalContext().getRequestParameterMap().get(filed);
          return value;	
    }
    
    public static String FetchSessionAttributes(String attribut){
         HttpSession session = RequestFilter.getSession();
         HttpServletRequest req = RequestFilter.getRequest(); 
         String attr = (String) session.getAttribute("token");
         return attr;     
    }

    public  String getHiddenFromSession(){
          return null;	
    }
    public static String escapeString(String escape){
       if(escape.contains("<"))
       {
           escape = escape.replaceAll("<", "&lt");
           escape = escape.replaceAll(">", "&gt");
       }
       return escape;
    }
    
}
