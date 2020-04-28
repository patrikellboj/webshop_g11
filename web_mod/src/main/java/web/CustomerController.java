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

    private String searchInput = "";
    private String foundProductName = "";
    private String foundProductDesc = "";
    private boolean renderResult = false;
    private List <Product> cartList = new ArrayList<>();
    private double cartTotal;

    @EJB
    CustomerHandlerLocal customerHandlerLocal;


    //Reference till produktlistan
    public List <Product> getProductsList(){
        return customerHandlerLocal.getProductsfromDb();
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

    public List<Product> getCartList() {
        return cartList;
    }

    public void setCartList(List<Product> cartList) {
        this.cartList = cartList;
    }

    public double getCartTotal() {
        this.cartTotal= calculateTotal();
        return cartTotal;
    }



    //att flytta till Ejb_model?

    public double calculateTotal(){
        double total = 0;
        for (Product product: cartList)
            total = total + product.getPrice();
        return total;
    }


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

}