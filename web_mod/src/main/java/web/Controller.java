package web;

import ejb.User;
import ejb.UserHandlerLocal;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "controller")
@SessionScoped

public class Controller implements Serializable {


    @EJB
    //TestBeanLocal testBeanLocal;
    UserHandlerLocal userHandlerLocal;
    private String usernameInput;
    private String passwordInput;
    private String message;
    private User currentUser;
    private User user = new User();
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

    public String submit () {
       this.currentUser = userHandlerLocal.check(usernameInput,passwordInput);
       if (currentUser != null) {
           //if (currentUser.getRole() == Role.ADMIN) {
           //    return "admin";
           //} else {

               return "customer";
          //}
       }
        this.message= "Not Found";
        return "";
    }

    public String registerNewCustomer() {
        this.message= userHandlerLocal.register(usernameInput, passwordInput);
        return "index";
    }

// kommer vi att använda det?
    public void reset() {
       usernameInput = "";
       passwordInput = "";
       message = "";
    }
}
