package eventFinderServer.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@DiscriminatorValue(value = "Seller")
public class Seller extends User {
	
	
	public Seller() {
	    super("Seller_USER");
	  }

	
	/*
	@OneToMany(mappedBy = "seller")
	@JsonIgnore
	private List <Event> events= new ArrayList<>();
	
	@OneToMany(mappedBy = "seller")
	@JsonIgnore
	private List <Review> reviews= new ArrayList<>();
	*/
	
	
	
	
	
	


}
