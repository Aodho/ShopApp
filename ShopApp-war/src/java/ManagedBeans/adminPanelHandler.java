package ManagedBeans;

import LoginPackage.SessionBean;
import beans.productBeanLocal;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import statics.post;

@ManagedBean
@SessionScoped
public class adminPanelHandler {

    
    
     String productTitle;
     String productQuantity;
     String cost;
     String actionMessage;
     String sessionToken;
     String passedToken;

  

    
    @EJB
    private productBeanLocal productBean;

     public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }
    
    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
    
    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
   public void storeProduct()
   {
       this.sessionToken=SessionBean.getToken();//gets the generated token from the session
       this.passedToken=post.getHidden("tokenPass");//gets the generated token from hidden form field
       String userType=SessionBean.getUserType();
       if(sessionToken.equalsIgnoreCase(passedToken) && userType.equalsIgnoreCase("admin") )//match the tokens if the tokens do not match thats mean the form  comings from a mirror site and denie accses
       {
            this.productTitle= post.escapeString(this.productTitle);
            this.productQuantity=post.escapeString(this.productQuantity);
            this.cost=post.escapeString(this.cost);
            productBean.addProduct(this.productTitle, this.productQuantity,this.cost);//call a method from injected Bean 
            this.actionMessage="Product: "+this.productTitle+" Quantity:"+this.productQuantity+" Succsefuly added to DB";
       }
       else 
           post.postRedirect("./error.xhtml");//if missmathc in the tokens redirect do error page and accses is forbiden
   }
   public void removeProduct()
   {
       this.sessionToken=SessionBean.getToken();
       this.passedToken=post.getHidden("tokenPass");
       if(sessionToken.equalsIgnoreCase(passedToken))
       {
       boolean b;
      b= productBean.removeProduct(this.productTitle);
      if(b)
      {
          this.actionMessage="Product: "+this.productTitle+" Succsefuly removed from  DB";
      }
      else
      {
          this.actionMessage="Product: "+this.productTitle+" Not Found";
      }
       }
      else
      {
           post.postRedirect("./error.xhtml");   
      }
       
   }
   public void incrementQ()
   { 
        this.sessionToken=SessionBean.getToken();
       this.passedToken=post.getHidden("tokenPass");
       if(sessionToken.equalsIgnoreCase(passedToken))
       {
       boolean b;
      b= productBean.increment(this.productTitle,this.productQuantity);
        if(b)
        {
            this.actionMessage="Product: "+this.productTitle+" Quantity has been incremeted by"+this.productQuantity ;
        }
        else
            this.actionMessage="Product: "+this.productTitle+" Not Found";
         }
       else
           post.postRedirect("./error.xhtml");   
   }
   public void decrementQ()
   {
      
       this.sessionToken=SessionBean.getToken();
       this.passedToken=post.getHidden("tokenPass");
       if(sessionToken.equalsIgnoreCase(passedToken))
       { 
      boolean b;
      b= productBean.decrement(this.productTitle,this.productQuantity);
        if(b)
        {
            this.actionMessage="Product: "+this.productTitle+" Quantity has been decremented by"+this.productQuantity ;
        }
        else
            this.actionMessage="Product: "+this.productTitle+" Not Found";
       }
       else
             post.postRedirect("./error.xhtml");
   }
    public void logOut()
    {
        
        
    }
    public void adminAcsses()
    {
         String userType=SessionBean.getUserType();
         if(!userType.equalsIgnoreCase("admin"))
         {
             post.postRedirect("./error.xhtml");
         }
         
             
        
    }
    public adminPanelHandler()
    {
    }
}
