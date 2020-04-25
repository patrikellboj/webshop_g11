package web;

import ejb.CustomerHandlerLocal;
import ejb.Product;
import ejb.UserHandlerLocal;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named (value = "customerController")
@SessionScoped
public class CustomerController implements Serializable {

    @EJB
    CustomerHandlerLocal customerHandlerLocal;

    public Product getProd1() { return customerHandlerLocal.getProductsfromDb().get(0);}
    public Product getProd2() { return customerHandlerLocal.getProductsfromDb().get(1);}
    public Product getProd3()  { return customerHandlerLocal.getProductsfromDb().get(2);}

    // vi har inte längre en list med products
    // listan har blivit userHandlerLocal.getProductsfromDb(). Detta är redan en list<Products>
    // om vi kommer att behöva en list  product här
    // antingen vi kan prova att fånga userHandlerLocal.getProductsfromDb() i en instansvariabel
    // eller kan vi loopa genom userHandlerLocal.getProductsfromDb() ocj skapa en ny lista
    // men vi kan också göra  alla sökningar direkt i DB (genom Ejb_mod). Jag tror att det är bättre.




   /* public Product findProduct(String name) {
        Product temp = null;
        for(int i = 0; i < products.size(); i++) {
            if(products.get(i).getName() == name) {
                temp = products.get(i);
            }
        }
        return temp;
    }*/
}
