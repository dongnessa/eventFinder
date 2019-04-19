package eventFinderServer.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@DiscriminatorValue(value = "Seller")
public class Seller extends User {
	
	@OneToMany(mappedBy = "seller" )
		@JsonIgnore
		private List <Event> events= new ArrayList<>();
		
	
	/*
		@OneToMany(mappedBy = "seller")
		@JsonIgnore
		private List <Review> sellerReviews= new ArrayList<>();*/
	
		
	    @ManyToMany(mappedBy = "followedSeller")
	    @JsonIgnore
		private List<Customer> followedCustomer = new ArrayList<>();
		
		
		
		
		
	public Seller() {
	    super("SELLER_USER");
	  }
	
	public 	Seller(String username, String password, String first, String last, String email, String phone, String address,
            Date reg, String link) {
 super(username, password, first, last, email, phone, address, reg, link);

}


	

	
	public List <Event> getEvents() {
		return events;
	}

	public void setEvents(List <Event> events) {
		this.events = events;
	}
	
	public void setEvent(Event e) {
		this.events.add(e);
		if(e.getSeller()!=this) {
			e.setSeller(this);
		}
	}

	
	/*
	public List <Review> getSellerReviews() {
		return sellerReviews;
	}

	public void setSellerReviews(List <Review> reviews) {
		this.sellerReviews = reviews;
	}*/

	public List<Customer> getFollowedCustomer() {
		return followedCustomer;
	}

	public void setFollowedCustomer(List<Customer> followingCustomer) {
		this.followedCustomer = followingCustomer;
	}




	
	
	
	
	
	
	
	
	
	


}
