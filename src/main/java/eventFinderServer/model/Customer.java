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
		if(!this.followedSeller.contains(s)) {
		this.followedSeller.add(s);
		}
		if(!s.getFollowedCustomer().contains(this)) {
			s.getFollowedCustomer().add(this);
		}
		
	}
	
	public void disfollowSeller(Seller seller) {
		if (this.followedSeller.contains(seller)) {
			this.followedSeller.remove(seller);		}
	}




	public List<Event> getLikedEvent() {
		return likedEvent;
	}




	public void setLikedEvent(List<Event> likedEvent) {
		this.likedEvent = likedEvent;
	}

	public void likeEvent(Event s) {
		if(!this.likedEvent.contains(s)) {
		this.likedEvent.add(s);
		}
		if(!s.getLikedCustomer().contains(this)) {
			s.getLikedCustomer().add(this);
		}
		
	}
	
	public void dislikeEvent(Event e) {
		if (this.likedEvent.contains(e)) {
			this.likedEvent.remove(e);		}
	}




	public List<Event> getAttendedEvent() {
		return attendedEvent;
	}


	public void setAttendedEvent(List<Event> attendedEvent) {
		this.attendedEvent = attendedEvent;
	}


	public void attendEvent(Event s) {
		if(!this.attendedEvent.contains(s)) {
		this.attendedEvent.add(s);
		}
		if(!s.getAttendedCustomer().contains(this)) {
			s.getAttendedCustomer().add(this);
		}
		
	}
	
	public void unAttendEvent(Event e) {
		if (this.attendedEvent.contains(e)) {
			this.attendedEvent.remove(e);		}
	}

	


}
