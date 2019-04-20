package eventFinderServer.service;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		Optional<User> seller = userRepo.findById(sid);
		if(seller.isPresent()) {
			Seller data  = (Seller) seller.get();
			System.out.print(data.getUsername());
			c.followSeller(data);
			customerRepo.save(c);
			
		}
	}
	
	@DeleteMapping("/api/customer/seller/{sid}")
	public void unfollowSellerByCustomer(@PathVariable("sid") long sid,HttpSession session ) {
		Customer c = (Customer) session.getAttribute("currentUser");
		Optional<User> cus1 = userRepo.findById(c.getId());
		Optional<User> sel1 = userRepo.findById(sid);
		
		if ( cus1.isPresent()&& sel1.isPresent()) {
			Customer customer = (Customer)(cus1.get());
			Seller seller = (Seller)(sel1.get());
			customer.disfollowSeller(seller);
			//seller.disfollowCustomer(customer);
			customerRepo.save(customer);
			sellRepo.save(seller);
		}
	}
	
	@GetMapping("/api/following/customer/{cid}")
		public List<Seller> findFollowingSellers(@PathVariable("cid") long cid){
		//Customer customer = (Customer) session.getAttribute("currentUser");
		
		//return customer.getFollowedSeller();
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
	
	
	
	@GetMapping("/api/event/like/{cid}")
	public List<Event> findLikedEvent(@PathVariable ("cid") long cid){
	Optional<User> data = userRepo.findById(cid);
	if(data.isPresent()) {
		Customer c = (Customer) data.get();
		return c.getLikedEvent();
	}
	return null;
	
}
	
	

	
	
	@DeleteMapping("/api/{cid}/like/event/{eid}")
	public void dislikeEventByCustomer(@PathVariable("eid") String sid,@PathVariable("cid") long cid) {
		Optional<User> cus1 = userRepo.findById(cid);
		//HttpSession session = request.getSession(false);
		//User cus1 = (User) session.getAttribute("currentUser");
		Optional<Event> sel1 = eventRepo.findById(sid);
		
		if ( cus1.isPresent()&&sel1.isPresent()) {
			Customer customer = (Customer)(cus1.get());
			Event event = sel1.get();
			customer.dislikeEvent(event);
			event.setInterested_count(event.getInterested_count()-1);
			//seller.disfollowCustomer(customer);
			customerRepo.save(customer);
			eventRepo.save(event);
		}
		/*
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} else {
			User currentUser = (User) session.getAttribute("currentUser");
			if(currentUser.getUserType().equals("CUSTOMER_USER")){
				Optional<Event> sel1 = eventRepo.findById(sid);
				
				if (sel1.isPresent()) {
					Customer customer = (Customer)(currentUser);
					Event event = sel1.get();
					customer.dislikeEvent(event);
					event.setInterested_count(event.getInterested_count()-1);
					//seller.disfollowCustomer(customer);
					customerRepo.save(customer);
					eventRepo.save(event);
				}
				
			}
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		*/
		
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
	
	
	
	@DeleteMapping("/api/in/event/{eid}")
	public void disAttendEventByCustomer(
			@PathVariable("eid") String eid,
			HttpSession session ) {
		    Optional<Event> eid1 = eventRepo.findById(eid);		
			User currentUser = (User) session.getAttribute("currentUser");
			Optional<User> cus1 = userRepo.findById(currentUser.getId());
			if(currentUser.getUserType().equals("CUSTOMER_USER")){
							
				if (cus1.isPresent()&&eid1.isPresent()) {
	
					Customer customer = (Customer)(currentUser);
					Event event = eid1.get();
					customer.unAttendEvent(event);
					event.setAttending_count(event.getAttending_count()-1);
				System.out.println("delete");
					customerRepo.save(customer);
					eventRepo.save(event);
					
				}
		
			}
		}
		
	
	
	
	
	
	@DeleteMapping("/api/{cid}/in/event/{eid}")
	public void unAttendEventByCustomer(@PathVariable("eid") String sid, @PathVariable("cid") long cid) {
		
		
		Optional<User> cus1 = userRepo.findById(cid);
		//HttpSession session = request.getSession(false);
		//User cus1 = (User) session.getAttribute("currentUser");
		Optional<Event> sel1 = eventRepo.findById(sid);
		
		if ( cus1.isPresent()&&sel1.isPresent()) {
			Customer customer = (Customer)(cus1.get());
			Event event = sel1.get();
			customer.unAttendEvent(event);
			event.setAttending_count(event.getAttending_count()-1);
			//seller.disfollowCustomer(customer);
			customerRepo.save(customer);
			eventRepo.save(event);
		}
	}
	
	
	
	@GetMapping("/api/customer/in/{eid}")
	public List<Customer> findAttendedCustomer(@PathVariable ("eid") String eid){
	Optional<Event> data = eventRepo.findById(eid);
	if(data.isPresent()) {
		Event e =  data.get();
		return e.getAttendedCustomer();
	}
	return null;
	
}
	
	
	
	@GetMapping("/api/event/in/{cid}")
	public List<Event> findAttendedEvent(@PathVariable ("cid") long cid){
	Optional<User> data = userRepo.findById(cid);
	if(data.isPresent()) {
		Customer c = (Customer) data.get();
		return c.getAttendedEvent();
	}
	return null;
	
	}
	
	
	
	
		
	
	


	
	
	
	
	
	
	

}
