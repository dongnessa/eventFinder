package eventFinderServer.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event {
	
	@Id
	private String id;
	private String name;
	private int attending_count;
	private double cost;
	private String category;
	private String description;
	private String event_site_url;
	private String image_url;
	private int interested_count;
	private boolean is_canceled;
	private boolean is_free;
	private boolean is_official;
	private String tickets_url;
	private String time_start;
	
	private String time_end;
	private double latitude;
	private double longitude;
	
	private String address;
	private String city;
	private String zip_code;
	private String country;
	private String state;
	private String business_id;
	
	
	@ManyToOne
	@JsonIgnore
	private Seller seller;
	
	
	
	
	 @ManyToMany(mappedBy = "likedEvent")
	 @JsonIgnore
	 private List<Customer> likedCustomer = new ArrayList<>();
		

	
	
	public Seller getSeller() {
		return this.seller;
	}
	
	public void setSeller(Seller s) {
		this.seller = s;
		if(!s.getEvents().contains(this)) {
			s.getEvents().add(this);
		}
	}
	
	
	
	
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAttending_count() {
		return attending_count;
	}
	public void setAttending_count(int attending_count) {
		this.attending_count = attending_count;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEvent_site_url() {
		return event_site_url;
	}
	public void setEvent_site_url(String event_site_url) {
		this.event_site_url = event_site_url;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public int getInterested_count() {
		return interested_count;
	}
	public void setInterested_count(int interested_count) {
		this.interested_count = interested_count;
	}
	public boolean isIs_canceled() {
		return is_canceled;
	}
	public void setIs_canceled(boolean is_canceled) {
		this.is_canceled = is_canceled;
	}
	public boolean isIs_free() {
		return is_free;
	}
	public void setIs_free(boolean is_free) {
		this.is_free = is_free;
	}
	public boolean isIs_official() {
		return is_official;
	}
	public void setIs_official(boolean is_official) {
		this.is_official = is_official;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getTicket_url() {
		return tickets_url;
	}
	public void setTicket_url(String ticket_url) {
		this.tickets_url = ticket_url;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}

	public List<Customer> getLikedCustomer() {
		return likedCustomer;
	}

	public void setLikedCustomer(List<Customer> likedCustomer) {
		this.likedCustomer = likedCustomer;
	}
	
	
	
	
	
	
	
	

}
