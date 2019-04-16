package eventFinderServer.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Customer")
public class Customer extends User{
	
	public Customer() {
	    super("CUSTOMER_USER");
	  }


}
