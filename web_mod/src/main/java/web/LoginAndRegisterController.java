package web;

import ejb.Product;
import ejb.Role;
import ejb.User;
import ejb.UserHandlerLocal;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "loginAndRegisterController")
@SessionScoped
public class LoginAndRegisterController implements Serializable {


    @EJB
    UserHandlerLocal userHandlerLocal;
    private String firstNameInput;
    private String lastNameInput;
    private String addressInput;
    private String usernameInput;
    private String passwordInput;
    private String emailInput;
    private String message;
    private User currentUser;

//-----------------------------------------------

    public String getUsernameInput() {
        return usernameInput;
    }

    public void setUsernameInput(String usernameInput) {
        this.usernameInput = usernameInput;
    }

    public String getPasswordInput() {
        return passwordInput;
    }

    public void setPasswordInput(String passwordInput) {
        this.passwordInput = passwordInput;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getFirstNameInput() {
        return firstNameInput;
    }

    public void setFirstNameInput(String firstNameInput) {
        this.firstNameInput = firstNameInput;
    }

    public String getLastNameInput() {
        return lastNameInput;
    }

    public void setLastNameInput(String lastNameInput) {
        this.lastNameInput = lastNameInput;
    }

    public String getAddressInput() {
        return addressInput;
    }

    public void setAddressInput(String addressInput) {
        this.addressInput = addressInput;
    }

    public String getEmailInput() {
        return emailInput;
    }

    public void setEmailInput(String emailInput) {
        this.emailInput = emailInput;
    }

    public String login() {
       this.currentUser = userHandlerLocal.login(usernameInput,passwordInput);
       if (currentUser != null) {
           if (currentUser.getRole() == Role.ADMIN) {
               this.message = "";
               return "admin";

           } else {
               this.message = "";
               return "customer";
          }
       }
        this.message= "Okänd användare";
        return "";
    }

//    public String registerNewCustomer() {
//        this.message= userHandlerLocal.register(usernameInput, passwordInput);
//        return "index";
//    }

    public String registerNewCustomer() {
        boolean userAdded = userHandlerLocal.addNewUser(firstNameInput, lastNameInput, addressInput, usernameInput, passwordInput, emailInput);
        if (userAdded) {
            this.message = "La till användare";
        } else {
            this.message = "Användaren \"" + usernameInput + "\" finns redan!";
        }
        return "index";
    }

// kommer vi att använda det?
    public void reset() {
       usernameInput = "";
       passwordInput = "";
       message = "";
    }
}
