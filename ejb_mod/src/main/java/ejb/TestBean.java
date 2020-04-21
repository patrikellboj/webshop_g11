package ejb;

import javax.ejb.Stateless;
import java.io.Serializable;

@Stateless(name = "TestEJB")
public class TestBean implements TestBeanLocal {

    private String greeting;

    public TestBean() {
    }

    @Override
    public String greeting(String greeting) {
        return greeting + " fellow coders";
    }
}
