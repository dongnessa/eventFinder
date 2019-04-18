package eventFinderServer.service;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import eventFinderServer.model.Customer;
import eventFinderServer.model.Event;
import eventFinderServer.model.Seller;
import eventFinderServer.model.User;
import eventFinderServer.repository.CustomerRepository;
import eventFinderServer.repository.EventRepository;
import eventFinderServer.repository.SellerRepository;
import eventFinderServer.repository.UserRepository;
import okhttp3.Request;
import okhttp3.Response;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600,allowCredentials = "true")
@Transactional
public class CustomerService {
	
	@Autowired
	EventRepository eventRepo;
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	SellerRepository sellRepo;
	@Autowired
	UserRepository userRepo;
	
	
	@PostMapping("/api/customer/follow/seller/{sid}")
	public void followSellerByCustomer(@PathVariable("sid") long sid, HttpSession session) {
		Customer c = (Customer) session.getAttribute("currentUser");
		System.out.print(c.getUsername());
		Optional<User> seller = userRepo.findById(sid);
		if(seller.isPresent()) {
			Seller data  = (Seller) seller.get();
			System.out.print(data.getUsername());
			c.followSeller(data);
			customerRepo.save(c);
			
		}
	}
	
	@DeleteMapping("/api/customer/{cid}/seller/{sid}")
	public void unfollowSellerByCustomer(@PathVariable("sid") long sid, @PathVariable("cid") long cid) {
		Optional<User> cus1 = userRepo.findById(cid);
		Optional<User> sel1 = userRepo.findById(sid);
		
		if (cus1.isPresent() && sel1.isPresent()) {
			Customer customer = (Customer)(cus1.get());
			Seller seller = (Seller)(sel1.get());
			customer.disfollowSeller(seller);
			//seller.disfollowCustomer(customer);
			customerRepo.save(customer);
			sellRepo.save(seller);
		}
	}
	
	@GetMapping("/api/following/customer/{cid}")
		public List<Seller> findFollowingSellers(@PathVariable ("cid") long cid){
		Optional<User> data = userRepo.findById(cid);
		if(data.isPresent()) {
			Customer cus = (Customer) data.get();
			return cus.getFollowedSeller();
		}
		return null;
		
	}
	@GetMapping("/api/followed/seller/{sid}")
	public List<Customer> findFollowedCustomer(@PathVariable ("sid") long sid){
	Optional<User> data = userRepo.findById(sid);
	if(data.isPresent()) {
		Seller seller = (Seller) data.get();
		return seller.getFollowedCustomer();
	}
	return null;
	}
	
	
	
	@GetMapping("/api/customer/{cid}/event/like")
	public List<Event> findLikedEvent(@PathVariable ("cid") long cid){
	Optional<User> data = userRepo.findById(cid);
	if(data.isPresent()) {
		Customer c = (Customer) data.get();
		return c.getLikedEvent();
	}
	return null;
	
}
	

	/*
	@PostMapping("/api/customer/like/event/{eid}")
	public void likeEventByCustomer(@PathVariable("eid") String eid, HttpSession session) {
		Customer c = (Customer) session.getAttribute("currentUser");
		System.out.print(c.getUsername());
		Optional<Event> e = eventRepo.findById(eid);
		if(e.isPresent()) {
			Event data  =  e.get();
			c.likeEvent(data);
			customerRepo.save(c);
			
		}
	}*/
	
	@DeleteMapping("/api/customer/{cid}/event/{eid}")
	public void dislikeEventByCustomer(@PathVariable("eid") String sid, @PathVariable("cid") long cid) {
		Optional<User> cus1 = userRepo.findById(cid);
		Optional<Event> sel1 = eventRepo.findById(sid);
		
		if (cus1.isPresent() && sel1.isPresent()) {
			Customer customer = (Customer)(cus1.get());
			Event event = sel1.get();
			customer.dislikeEvent(event);
			//seller.disfollowCustomer(customer);
			customerRepo.save(customer);
			eventRepo.save(event);
		}
	}
	
	
	@GetMapping("/api/event/{eid}/liked/customer")
		public List<Customer> findlikeCustomer(@PathVariable ("eid") String eid){
		Optional<Event> data = eventRepo.findById(eid);
		if(data.isPresent()) {
			Event e =  data.get();
			return e.getLikedCustomer();
		}
		return null;
		
	}
	
	
	
		
	
	


	
	
	
	
	
	
	

}
