package statics;


import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
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
    
    public static void postRedirect(String url)
   {
       
           FacesContext context = FacesContext.getCurrentInstance();
           String redirectUrl = url;
           ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

           try {
               ec.redirect(redirectUrl);
           } catch (IOException e) {
               e.printStackTrace();
               FacesMessage message = new FacesMessage(e.getMessage());
               context.addMessage(null, message);
           }
       
   }
   public static void generateToken()
   {
       post.token= (int) (Math.random()*50000005)+1+"";
   }
    public static String getHidden(String filed)
    {
	   String value = FacesContext.getCurrentInstance().
		getExternalContext().getRequestParameterMap().get(filed);
          return value;	
    }
     public  String getHiddenFromSession()
    {
	 //  HttpSession session1= request.getSession(false);
          return null;	
    }
     public static String FetchSessionAttributes(String attribut)
     {
         HttpSession session = RequestFilter.getSession();
         HttpServletRequest req = RequestFilter.getRequest(); 
         String atribute = (String) session.getAttribute("token");
         return atribute;
        
     }
     public static String escapeString(String s)
    {
       if(s.contains("<"))
       {
           s = s.replaceAll("<", "&lt");
           s = s.replaceAll(">", "&gt");
       }
       return s;
    }
    
}
