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
	public boolean followSellerByCustomer(@PathVariable("sid") long sid, HttpSession session) {
		User  c = (User) session.getAttribute("currentUser");
		Optional<User> u = userRepo.findById(c.getId());
		
		Optional<User> seller = userRepo.findById(sid);
		
		if(seller.isPresent()&& u.isPresent()) {
			Seller data  = (Seller) seller.get();
			Customer data2 = (Customer) u.get();
			if((!data2.getFollowedSeller().contains(data))&&(!data.getFollowedCustomer().contains(data2))) {
			data2.followSeller(data);
			sellRepo.save(data);
			customerRepo.save(data2);
			return true;
			}
			
		}
		return false;
	}
	
	@DeleteMapping("/api/customer/seller/{sid}")
	public boolean unfollowSellerByCustomer(@PathVariable("sid") long sid,HttpSession session ) {
		User c = (User) session.getAttribute("currentUser");
		
		Optional<User> cus1 = userRepo.findById(c.getId());
		Optional<User> sel1 = userRepo.findById(sid);
		
		if ( cus1.isPresent()&& sel1.isPresent()) {
			Customer customer = (Customer)(cus1.get());
			Seller seller = (Seller)(sel1.get());
			customer.disfollowSeller(seller);
			//seller.disfollowCustomer(customer);
			customerRepo.save(customer);
			sellRepo.save(seller);
			return true;
		}
		return false;
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
	
	@GetMapping("/api/event/like")
	public List<Event> findLikedEvent(HttpSession session){
		
	Long cid = ((User)(session.getAttribute("currentUser"))).getId();
	System.out.println(cid);
	Optional<Customer> data = customerRepo.findById(cid);
	
	if(data.isPresent()) {
		Customer c = (Customer) data.get();
		return c.getLikedEvent();
	}
	return null;
	}
	
	

	
	
	@DeleteMapping("/api/{cid}/like/event/{eid}")
	public void notlikeEventByCustomer(@PathVariable("eid") String sid,@PathVariable("cid") long cid) {
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
	
	@DeleteMapping("/api/like/event/{eid}")
	public boolean dislikeEventByCustomer(@PathVariable("eid") String sid,HttpSession session) {
		
		User cus1 = (User) session.getAttribute("currentUser");
		Optional<User> data = userRepo.findById(cus1.getId());
		Optional<Event> sel1 = eventRepo.findById(sid);
		
		if ( data.isPresent()&&sel1.isPresent()) {
			Customer customer = (Customer)(data.get());
			Event event = sel1.get();
			customer.dislikeEvent(event);
			event.setInterested_count(event.getInterested_count()-1);
			//seller.disfollowCustomer(customer);
			customerRepo.save(customer);
			eventRepo.save(event);
			return true;
		}
		return false;
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
	
	
	@GetMapping("/api/seller/isFollow/{sid}")
	public boolean isFollow(@PathVariable ("sid") long sid, HttpSession session) {
		 if(session.getAttribute("currentUser")==null) {
		    	
		    	return false;
		    }
		User currentUser = (User) session.getAttribute("currentUser");
		Optional<User> cus1 = userRepo.findById(currentUser.getId());
		Optional<User> sel1 = userRepo.findById(sid);
		if(cus1.isPresent()&&sel1.isPresent()) {
			Customer c =(Customer) cus1.get();
			Seller s =(Seller) sel1.get();
			if(c.getFollowedSeller().contains(s)) {
				return true;
			}
		}
		return false;
		
	}
	
	
	
	@DeleteMapping("/api/in/event/{eid}")
	public boolean disAttendEventByCustomer(
			@PathVariable("eid") String eid,
			HttpSession session ) {
		    Optional<Event> eid1 = eventRepo.findById(eid);		
			User currentUser = (User) session.getAttribute("currentUser");
			Optional<User> cus1 = userRepo.findById(currentUser.getId());
			
				if(currentUser.getUserType().equals("CUSTOMER_USER")) {			
				if (cus1.isPresent()&&eid1.isPresent()) {
	
					Customer customer = (Customer)(cus1.get());
					Event event = eid1.get();
		
					customer.unAttendEvent(event);
					event.setAttending_count(event.getAttending_count()-1);
				System.out.println("delete");
					customerRepo.save(customer);
					eventRepo.save(event);
					return true;
					
				}
				
		
			}
				return false;
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
