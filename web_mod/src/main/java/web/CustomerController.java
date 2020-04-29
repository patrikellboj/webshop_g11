package web;

import ejb.*;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


@Named (value = "customerController")
@SessionScoped
public class CustomerController implements Serializable {


    @EJB
    CustomerHandlerLocal customerHandlerLocal;
    private String searchInput = "";
    private String foundProductName = "";
    private String foundProductDesc = "";
    private boolean renderResult = false;
    private boolean renderDiscountMsg = false;
    private List <Product> cartList = new ArrayList<>();
    private List <Product> confirmedOrder = new ArrayList<>();
    private double cartTotal;
    private double orderTotal;

    //----------------------------------------------------------

    //Referens till produktlistan


    public void findProduct(String name) {
        Product temp = null;
        for(int i = 0; i < getProductsList().size(); i++) {
            if(getProductsList().get(i).getName().equals(name)) {
                temp = getProductsList().get(i);
                foundProductName = temp.getName();
                foundProductDesc = temp.getDescription();
                renderResult = true;
                break;
            }
            else {
                foundProductName = "Not found!";
                renderResult = false;
            }
        }
    }

    public double calculateTotal(User currentUser){
        double total = 0;
        for (Product product: cartList)
            total = total + product.getPrice();
        if (currentUser.getRole() == Role.PREMIUM_CUSTOMER) {
            renderDiscountMsg=true;
            return total * 0.90;
        }
        renderDiscountMsg=false;
        return total;
    }

    public void add (String name) {
        Product temp = null;
        for (int i = 0; i < getProductsList().size(); i++) {
            if (getProductsList().get(i).getName().equals(name)) {
                temp = getProductsList().get(i);
                cartList.add(new Product(temp.getName(), temp.getDescription(), temp.getPrice()));
                break;
            }
        }
    }

    //Går till order sidan
    public String confirmOrder(User currentUser){
        for(Product product : cartList) {
            confirmedOrder.add(product);
        }
        LoggHandler.logg(Level.INFO, currentUser.getUsername());
        LoggHandler.logg(Level.INFO, confirmedOrder.get(1).getName());
        orderTotal = cartTotal;
        customerHandlerLocal.registerNewOrder(currentUser, confirmedOrder);
        cartList.clear(); //Tömmer varukorgen
        return "order";
    }

    public String backToShop() {
        confirmedOrder.clear(); //Tömmer bekräftelse listan
        return "customer";
    }

    public List <Product> getProductsList(){
        return customerHandlerLocal.getProductsfromDb();
    }

    public List <Product> getConfirmedOrder(){

        return this.confirmedOrder;
    }

    public double getOrderTotal(){
        return this.orderTotal;
    }

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public String getFoundProductName() {
        return foundProductName;
    }

    public void setFoundProductName(String foundProductName) {
        this.foundProductName = foundProductName;
    }

    public String getFoundProductDesc() {
        return foundProductDesc;
    }

    public void setFoundProductDesc(String foundProductDesc) {
        this.foundProductDesc = foundProductDesc;
    }

    public boolean getRenderResult() {
        return renderResult;
    }

    public void setRenderResult(boolean renderResult) {
        this.renderResult = renderResult;
    }

    public boolean getRenderDiscountMsg() {
        return renderDiscountMsg;
    }

    public void setRenderDiscountMsg(boolean renderDiscountMsg) {
        this.renderDiscountMsg = renderDiscountMsg;
    }

    public List<Product> getCartList() {
        return cartList;
    }

    public void setCartList(List<Product> cartList) {
        this.cartList = cartList;
    }

    public double getCartTotal(User currentUser) {
        this.cartTotal= calculateTotal(currentUser);
        return cartTotal;
    }
}