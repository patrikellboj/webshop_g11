package web;

import ejb.TestBeanLocal;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class TestController implements Serializable {

    private String inputGreeting;
    private String greeting;

    @EJB
    private TestBeanLocal testBeanLocal;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public void greeting() {
        this.greeting = testBeanLocal.greeting(greeting);
    }

}
