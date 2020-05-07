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
    @EJB
    UserHandlerLocal userHandlerLocal;

    private String searchInput = "";
    private String foundProductName = "";
    private String foundProductDesc = "";
    private boolean renderResult = false;
    private boolean renderDiscountMsg = false;
    private List <Product> cartList = new ArrayList<>();
    private List <Product> confirmedOrder = new ArrayList<>();
    private double cartTotal;
    private double orderTotal;


    public void findProduct(String name) {
        Product temp;
        for(int i = 0; i < getProductsList().size(); i++) {
            if(getProductsList().get(i).getName().equalsIgnoreCase(name)) {
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
        double total= customerHandlerLocal.calculateTotal(currentUser, cartList);
        renderDiscountMsg = currentUser.getRole() == Role.PREMIUM_CUSTOMER;
        return total;
    }

    public String add (String name) {
        Product temp;
        for (int i = 0; i < getProductsList().size(); i++) {
            if (getProductsList().get(i).getName().equals(name)) {
                temp = getProductsList().get(i);
                cartList.add(new Product(temp.getName(), temp.getDescription(), temp.getPrice()));
                break;
            }
        }
        return "customer?faces-redirect=true";
    }

    //Går till order sidan
    public String confirmOrder(User currentUser){
        if (cartList.size()>0) {
            confirmedOrder.addAll(cartList);
            orderTotal = cartTotal;
            customerHandlerLocal.registerNewOrder(currentUser, confirmedOrder);
            userHandlerLocal.uppdateTotalAmountAndRole(currentUser.getUsername(), orderTotal);
            cartList.clear(); //Tömmer varukorgen
            return "order";
        }
        return "";
    }

    public void removeItem(Product p){
        for(int i = 0; i < cartList.size(); i++) {
            if(cartList.get(i).hashCode() == p.hashCode()){
                cartList.remove(i);
            }
        }
    }

    public String logout() {
        clear();
        return "index";
    }

    public void clear() {
        cartList.clear();
        searchInput = "";
        foundProductName = "";
        foundProductDesc = "";
        confirmedOrder.clear();
    }


    public List <Product> getProductsList(){
        return customerHandlerLocal.getProductsfromDb();
    }

    public List <Product> getConfirmedOrder() {
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