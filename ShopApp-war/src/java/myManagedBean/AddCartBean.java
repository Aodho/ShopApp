package myManagedBean;

import LoginPackage.SessionBean;
import beans.ShoppingCartBeanLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import enterpriseBean.ShoppingCart;
import java.util.HashMap;
import statics.post;

@Named(value = "addCartBean")
@SessionScoped
public class AddCartBean implements Serializable {

    @EJB
    private ShoppingCartBeanLocal shoppingCartBean;
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

    public void addToBasket(String pName, int quantityVar) {

        shoppingCartBean.removeItem(pName, quantityVar);
    }
    
    public HashMap<String, Integer> getCartItems()
    {
        return shoppingCartBean.getCartItems();
    }
    public String checkout()

    public String cancel() {
        shoppingCartBean.cancel();
        return "cancel";
    }


    public String getItemList() {
        String content = shoppingCartBean.getItemList();
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
