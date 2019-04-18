package eventFinderServer.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

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
		this.followedSeller.add(s);
		if(!s.getFollowedCustomer().contains(this)) {
			s.getFollowedCustomer().add(this);
		}
		
	}
	
	public void disfollowSeller(Seller seller) {
		if (this.followedSeller.contains(seller)) {
			this.followedSeller.remove(seller);		}
	}

	
	


}
