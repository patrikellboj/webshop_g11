package ejb;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;


@Entity
@Table(name = "ORDERS")


public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional=false)
    private long id;

    @ManyToOne(cascade=PERSIST)
    private User user;
    @ManyToMany(cascade=PERSIST,mappedBy="ordersList")
    private List<Product> productList;
    private String text;

    //-------------------------------------

    public Orders() {}


    //-------------------------------------


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> products) {
        this.productList = productList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
