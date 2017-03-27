package beans;

import Ent.Manufacturer;
import Ent.Product;
import Ent.ProductCode;
import static Ent.Product_.available;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
@Stateless
public class productBean implements productBeanLocal
{

    @PersistenceContext(unitName = "OnlineShoppingApplication-ejbPU")
    private EntityManager em;

   
    @Override
    public void addProduct(String title, String amount,String cost)
    {
        Query q= em.createNamedQuery("Product.getHighestProductId");
        int id=(int) q.getSingleResult()+1;
        Product pr=new Product();
        Manufacturer m=new Manufacturer();
       
        m.setManufacturerId(19985590);
        BigDecimal bigDecimalValue= new BigDecimal(cost);
        
        ProductCode pc=new ProductCode();
        pc.setProdCode("HW");
        pr.setDescription(title);
        pr.setProductId(id);
        pr.setQuantityOnHand(Integer.parseInt(amount));
        pr.setMarkup(BigDecimal.ZERO);
        pr.setManufacturerId(m);
        pr.setProductCode(pc);
        pr.setPurchaseCost(bigDecimalValue);
        pr.setAvailable("true");
        
        em.persist(pr); 
        
    }
    @Override
    public boolean removeProduct(String title) {
       Query q= em.createNamedQuery("Product.findByDescription");
       q.setParameter("description", title);
       
        List <Product> isin=q.getResultList();
        if(isin.isEmpty())
      {
         return false;
      }
        else
        {  
            
          em.remove(isin.get(0));
          return true;
        }
    }
     @Override
    public boolean increment(String title,String amount) 
    {
        Query q= em.createNamedQuery("Product.findByDescription");
        q.setParameter("description", title);
        List <Product> isin=q.getResultList();
        if(isin.isEmpty())
      {
         return false;
      }
        else
        {  
            int am=Integer.parseInt(amount);
            int currentAm=isin.get(0).getQuantityOnHand();
            Product p=isin.get(0);
            p.setQuantityOnHand(currentAm + am);
            em.persist(p);
          
          return true;
        }
        
    }

    @Override
    public boolean decrement(String title,String amount)
    {
        
        Query q= em.createNamedQuery("Product.findByDescription");
        q.setParameter("description", title);
         List <Product> isin=q.getResultList();
          Product p=isin.get(0);
          int am=Integer.parseInt(amount);
        if(isin.isEmpty())
      {
         return false;
      }
        else
        { 
            if(p.getQuantityOnHand()<= am)
            {
                p.setQuantityOnHand(0);
                em.persist(p);
                return true;
            }
            if(p.getQuantityOnHand()>0)
            {
               p.setQuantityOnHand(p.getQuantityOnHand()- am);
               em.persist(p);
               return true;
            }
            return true;

        }

    }

    public void persist(Object object) {
        em.persist(object);
    }

    public void persist1(Object object) {
        em.persist(object);
    }

   

   
}
