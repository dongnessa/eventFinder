package eventFinderServer.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue(value = "Customer")
public class Customer extends User{
	
	
	
	@ManyToMany
	@JsonIgnore
	@JoinTable (name = "FOLLOW",joinColumns= @JoinColumn(name= "CUSTOMER_ID", referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(name=
	        "SELLER_ID", referencedColumnName="ID"))
	private List<Seller> followedSeller = new ArrayList<>();
	
	
	@ManyToMany
	@JsonIgnore
	@JoinTable (name = "Liked",joinColumns= @JoinColumn(name= "CUSTOMER_ID", referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(name=
	        "EVENT_ID", referencedColumnName="ID"))
	private List<Event> likedEvent = new ArrayList<>();
	
	@ManyToMany
	@JsonIgnore
	@JoinTable (name = "ATTEND",joinColumns= @JoinColumn(name= "CUSTOMER_ID", referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(name=
	        "EVENT_ID", referencedColumnName="ID"))
	private List<Event> attendedEvent = new ArrayList<>();
	
	
	
	@OneToMany(mappedBy= "customer")
	@JsonIgnore
	private List<Review> customerReviews = new ArrayList<>();
	
	
	
	
	public Customer() {
	    super("CUSTOMER_USER");
	  }
	
	
	
	
	public 	Customer(String username, String password, String first, String last, String email, String phone, String address,
            Date reg, String link) {
		super(username, password, first, last, email, phone, address, reg, link);}




	public List<Seller> getFollowedSeller() {
		return followedSeller;
	}




	public void setFollowedSeller(List<Seller> followedSeller) {
		this.followedSeller = followedSeller;
	}
	
	
	
	
	public void followSeller(Seller s) {
		if(!this.followedSeller.contains(s)&& !s.getFollowedCustomer().contains(this)) {
		this.followedSeller.add(s);
			s.getFollowedCustomer().add(this);
		}
		
	}
	
	public void disfollowSeller(Seller seller) {
		
		if (this.followedSeller.contains(seller)) {
			this.followedSeller.remove(seller);	
			seller.getFollowedCustomer().remove(this);
			}
	}




	public List<Event> getLikedEvent() {
		return likedEvent;
	}




	public void setLikedEvent(List<Event> likedEvent) {
		this.likedEvent = likedEvent;
	}

	public void likeEvent(Event s) {
		if(!this.likedEvent.contains(s)&&!s.getLikedCustomer().contains(this)) {
		this.likedEvent.add(s);
			s.getLikedCustomer().add(this);
		}
		
	}
	
	public void dislikeEvent(Event e) {
		if (this.likedEvent.contains(e)) {
			this.likedEvent.remove(e);	
			e.getLikedCustomer().remove(this);}
	}




	public List<Event> getAttendedEvent() {
		return attendedEvent;
	}


	public void setAttendedEvent(List<Event> attendedEvent) {
		this.attendedEvent = attendedEvent;
	}


	public void attendEvent(Event s) {
		if(!this.attendedEvent.contains(s)&&!s.getAttendedCustomer().contains(this)) {
		this.attendedEvent.add(s);
		
			s.getAttendedCustomer().add(this);
		}
		
	}
	
	public void unAttendEvent(Event e) {
		
		if (this.attendedEvent.contains(e)) {
			this.attendedEvent.remove(e);	
			e.getAttendedCustomer().remove(this);}
	}




	public List<Review> getCustomerReviews() {
		return customerReviews;
	}




	public void setCustomerReviews(List<Review> customerReviews) {
		this.customerReviews = customerReviews;
	}

	

	public void addCustomerReviews(Review r) {
		this.customerReviews.add(r);
	}
	
	public void removeCustomerReview( Review r) { 
		if(this.customerReviews.contains(r)) {
		this.customerReviews.remove(r);
		r.setCustomer(null);
	}
	}
   
}
