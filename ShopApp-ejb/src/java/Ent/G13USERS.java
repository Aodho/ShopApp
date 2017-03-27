package Ent;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "G13USERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "G13USERS.findByName", query = "SELECT u FROM G13USERS u WHERE u.username = :username"),
    @NamedQuery(name = "G13USERS.loginValidate", query = "SELECT COUNT(u) FROM G13USERS u WHERE u.username = :username AND u.password = :password"),
    @NamedQuery(name = "G13USERS.isUserExists", query = "SELECT COUNT(u) FROM G13USERS u WHERE u.username = :username")})
    
public class G13USERS implements Serializable {
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
    @Column(name = "USERTYPE")//Column Usertype with maximum value 30 t
    private String usertype; //either customer or admin
    
    //message
    @Size(max = 500)
    @Column(name = "MESSAGE")//Column Message with maximum value 30
    private String message;
    
    public G13USERS(Long uid) {
        this.uid = uid;
    }
    
    public G13USERS()
    {}
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public Long getId() {
        return uid;
    }

    public void setId(Long id) {
        this.uid = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uid != null ? uid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof G13USERS)) {
            return false;
        }
        G13USERS other = (G13USERS) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ent.G13USERS[ id=" + uid + " ]";
    }
    
}
