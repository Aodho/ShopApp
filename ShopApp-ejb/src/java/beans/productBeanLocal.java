package beans;

import javax.ejb.Local;
@Local
public interface productBeanLocal {
    
    public void addProduct(String title,String amount,String cost);
    public boolean removeProduct(String title);
    public boolean increment(String title,String amount);
    public boolean decrement(String title,String amount);
    
}
