package eventFinderServer.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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
	
	
	
	
	
	
	

}