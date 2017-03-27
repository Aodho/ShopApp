package myManagedBean;

import LoginPackage.SessionBean;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import beans.ShoppingCartLocal;
import java.util.HashMap;
import statics.post;

@Named(value = "addCartBean")
@SessionScoped
public class AddCartBean implements Serializable {

    /**
     * Creates a new instance of AddCartBean
     */
    public AddCartBean() {
    }
    
    private int quantityVar = 0;
    private int quantityVar1 = 0;

    public int getQuantityVar1() {
        return quantityVar1;
    }

    public void setQuantityVar1(int quantityVar1) {
        this.quantityVar1 = quantityVar1;
    }

    public int getQuantityVar() {
        return quantityVar;
    }

    public void setQuantityVar(int quantityVar) {
        this.quantityVar = quantityVar;
    }

    private String order = "";
    @EJB
    ShoppingCartLocal cart;
    public void addToBasket(String pName, int quantityVar) {
        
        cart.addItem(pName, quantityVar);
        System.out.println("Product Name : " + pName + " Quantity : " + quantityVar);
        this.quantityVar = 0;
    }

    public void removeFromBasket(String pName, int quantityVar) {

        cart.removeItem(pName, quantityVar);
    }
    
    public HashMap<String, Integer> getCartItems()
    {
        return cart.getCartItems();
    }
    public String checkout()
    {   
     
        order = cart.getItemList().replace("<br>", "");
        cart.checkout();
        return "checkout";
      
    }

    public String cancel() {
        cart.cancel();
        return "cancel";
    }

    public String getItemList() {
        String content = cart.getItemList();
        return content.replace("<br>", "");
    }

    public String index() {
        FacesContext.getCurrentInstance().getExternalContext().
                invalidateSession();
        return "admin";
    }

    public String getOrder() {
        return order;
    }
}
