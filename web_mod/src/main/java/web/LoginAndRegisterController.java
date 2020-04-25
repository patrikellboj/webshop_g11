package web;

import ejb.Product;
import ejb.Role;
import ejb.User;
import ejb.UserHandlerLocal;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "loginAndRegisterController")
@SessionScoped
public class LoginAndRegisterController implements Serializable {

    @EJB
    UserHandlerLocal userHandlerLocal;
    private String usernameInput;
    private String passwordInput;
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

    public String login() {
       this.currentUser = userHandlerLocal.login(usernameInput,passwordInput); //varför inte userHandler?
       if (currentUser != null) {
           if (currentUser.getRole() == Role.ADMIN) {
               return "admin";
           } else {
               return "customer";
          }
       }
        this.message= "User not found";
        return "";
    }

//    public String registerNewCustomer() {
//        this.message= userHandlerLocal.register(usernameInput, passwordInput);
//        return "index";
//    }

    public String registerNewCustomer() {
        this.message = userHandlerLocal.addNewUser(usernameInput, passwordInput);
        return "index";
    }

// kommer vi att använda det?
    public void reset() {
       usernameInput = "";
       passwordInput = "";
       message = "";
    }
}
