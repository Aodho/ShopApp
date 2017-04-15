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

import ManagedBeans.ShoppingCartBeanLocal;
import java.io.Serializable;
import java.util.HashMap;
import javax.enterprise.context.SessionScoped;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;


@Named(value = "addCartBean")
@SessionScoped
public class AddCartBean implements Serializable {

    @EJB
    private ShoppingCartBeanLocal shoppingCartBean;
	
    //Creates a new instance of AddCartBean
    public AddCartBean() {
    }
    
    private int quanCntr = 0;
    private int quanCntr1 = 0;

    //getter for quanCntr
    public int getquanCntr() {
        return quanCntr;
    }

    //setter for quanCntr
    public void setquanCntr(int quanCntr) {
        this.quanCntr = quanCntr;
    }
       
    //getter for quanCntr1
    public int getquanCntr1() {
        return quanCntr1;
    }

    //setter for quanCntr1
    public void setquanCntr1(int quanCntr1) {
        this.quanCntr1 = quanCntr1;
    }

    //hash map list to get cart item 
    public HashMap<String, Integer> getCartItems()
    {
        return shoppingCartBean.getCartItems();
    }    
    
    //sring order to add oreder item
    private String order = "";

    //Adds new items to the shopping shoppingCartBean - quantities are taken from instance variables
    public void addToBasket(String pName, int quanCntr) {
       
        //ejb bean to add item
        shoppingCartBean.addItem(pName, quanCntr);  
       
        //reset the counter 
        this.quanCntr = 0;
    }

	//Remove items from the shopping shoppingCartBean - quantities are taken from instance
    //variables. Note: ShoppingCart SFSB takes care of too large values
    public void removeFromBasket(String pName, int quanCntr) {
       
        //ejb bean to add item 
        shoppingCartBean.removeItem(pName, quanCntr);
        
        //reset the counter
        this.quanCntr1 = 0;
    }
   
    //Checkout shopping shoppingCartBean - only stores checked out items in instance
    // variable and removes releases SFSB shoppingCartBean
    public String checkout()
    {   
        //calling ejb bean checkout and add cart item into order string
        order = shoppingCartBean.checkout();
        //calling back checkout page
        return "checkout";
      
    }

    //Getter for order
    public String getOrder() {
        return order;
    }    
    
    //Cancels the order. Only releases SFSB shoppingCartBean
    public String cancel() {
        shoppingCartBean.cancel();
        return "cancel";
    }


    //Returns a list of items and their quantities that are currently in
    //shopping shoppingCartBean
    public String getItemList() {
        String content = shoppingCartBean.getItemList();
        return content.replace("<br>", "");
    }

    //Destroys current session
    public String index() {
        // invalidate session to remove reference 
        // to shopping shoppingCartBean - you want a new one next time to make
        // sure to receive a new SFSB
        FacesContext.getCurrentInstance().getExternalContext().
                invalidateSession();
        return "admin";
    }

    //Starts the check out functionality
    public void runCheckOut()
    {
        shoppingCartBean.runCheckOut();
    }
    
}
