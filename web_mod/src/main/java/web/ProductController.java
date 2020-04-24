package web;

import ejb.Product;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named (value = "productController")
@SessionScoped
public class ProductController implements Serializable {

    private List<Product> products = new ArrayList<>();

    private Product prod1 = new Product("Dillchips", "Smakfulla dillchips från OLW!", 20.0D);
    private Product prod2 = new Product("Sourcream", "Delikata sorucreamchips från OLW!", 20.0D);
    private Product prod3 = new Product("Grillchips", "Krispiga grillchips från OLW!", 20.0D);

    public ProductController() {
        products.add(prod1);
        products.add(prod2);
        products.add(prod3);
    }

    public Product getProd1() {
        return prod1;
    }

    public Product getProd2() {
        return prod2;
    }

    public Product getProd3() {
        return prod3;
    }

    public Product findProduct(String name) {
        Product temp = null;
        for(int i = 0; i < products.size(); i++) {
            if(products.get(i).getName() == name) {
                temp = products.get(i);
            }
        }
        return temp;
    }
}
