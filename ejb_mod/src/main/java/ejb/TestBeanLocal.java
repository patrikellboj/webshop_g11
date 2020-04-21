package ejb;

import javax.ejb.Local;

@Local
public interface TestBeanLocal {
    String greeting(String greeting);
}
