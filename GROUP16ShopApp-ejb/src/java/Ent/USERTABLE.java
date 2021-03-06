/*
 *
 * Aaron Dias              16068963 
 * Conor McDonnell   16120604
 * Hugh Mullins           16106989
 * Sunday Jimoh         16090462
 * Samaa Alsafwani   16087941
 *
 */
package Ent;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "USERTABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "USERTABLE.findByName", query = "SELECT u FROM USERTABLE u WHERE u.username = :username"),
    @NamedQuery(name = "USERTABLE.loginValidate", query = "SELECT COUNT(u) FROM USERTABLE u WHERE u.username = :username AND u.password = :password"),
    @NamedQuery(name = "USERTABLE.loginValidate2", query = "SELECT COUNT(u) FROM USERTABLE u WHERE u.username = :username AND u.password = :password AND u.usertype = :usertype"),
    @NamedQuery(name = "USERTABLE.getUserID", query = "SELECT u.uid FROM USERTABLE u WHERE u.username = :username AND u.password = :password"),
    @NamedQuery(name = "USERTABLE.findByUserId", query = "SELECT u FROM USERTABLE u WHERE u.uid = :uid"),
    @NamedQuery(name = "USERTABLE.findAllCustomer", query = "SELECT u FROM USERTABLE u WHERE u.usertype = :usertype"), 
    @NamedQuery(name = "USERTABLE.findByCustomerId", query = "SELECT u FROM USERTABLE u WHERE u.uid = :uid AND u.usertype = :usertype"),
    @NamedQuery(name = "USERTABLE.findByCustomerName", query = "SELECT u FROM USERTABLE u WHERE u.username = :username AND u.usertype = :usertype"),
    @NamedQuery(name = "USERTABLE.isUserExists", query = "SELECT COUNT(u) FROM USERTABLE u WHERE u.username = :username")})
	
    
public class USERTABLE implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uid;
    
    //username
    @Size(max = 30)
    @Column(name = "USERNAME") //Column username with maximum value 30
    private String username;
   
    //password
    @Size(max = 30)
    @Column(name = "PASSWORD")//Column PASSWORD with maximum value 30
    private String password;
    
    //address
    @Size(max = 30)
    @Column(name = "ADDRESS")//Column Address with maximum value 30
    private String address;
   
    //usertype: cutomer/admin
    @Size(max = 30)
    @Column(name = "USERTYPE")//Column Usertype with maximum value 30 
    private String usertype; 
    
    //message
    @Size(max = 500)
    @Column(name = "MESSAGE")//Column Message with maximum value 30
    private String message;
    
    public USERTABLE(Long uid) {
        this.uid = uid;
    }
    
    public USERTABLE()
    {}
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
	
	public Long getId() {
        return uid;
    }

    public void setId(Long id) {
        this.uid = id;
    }
	
	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
	
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
	
    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uid != null ? uid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof USERTABLE)) {
            return false;
        }
        USERTABLE other = (USERTABLE) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ent.USERTABLE[ id=" + uid + " ]";
    }
    
}
