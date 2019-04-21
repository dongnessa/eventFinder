package eventFinderServer.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eventFinderServer.model.Customer;
import eventFinderServer.model.Event;
import eventFinderServer.model.Review;
import eventFinderServer.model.User;
import eventFinderServer.repository.CustomerRepository;
import eventFinderServer.repository.EventRepository;
import eventFinderServer.repository.ReviewRepository;
import eventFinderServer.repository.UserRepository;

@Service
@RestController
@CrossOrigin(origins = "*",maxAge=3600,allowCredentials = "true")
public class ReviewService {
	
	@Autowired
	ReviewRepository reviewRepo;
	@Autowired
	EventRepository eventRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired 
	CustomerRepository cusRepo;
	
	
	
	@PostMapping("api/comment/{eid}") 
	public Review submitComment(@RequestBody Review review, @PathVariable("eid") String eid, HttpSession session ) {
		//Customer user = (Customer)session.getAttribute("currentUser");
		Long userId = ((User)session.getAttribute("currentUser")).getId();
		Optional<Event> data = eventRepo.findById(eid);
		Optional<User> u = userRepo.findById(userId);
		
		
		
		if(u.isPresent()&&data.isPresent()) {
			
			Customer customer =(Customer) u.get();
			Event event = data.get();
			if(customer.getAttendedEvent().contains(event)) {
			Review newReview = new Review(event, customer);
			newReview.setReviewScore(review.getReviewScore());
			newReview.setText(review.getText());
			
			event.addEventReviews(customer, newReview);
			
			
			//double currentRating = event.getRating()* event.getEventReviews().size();
			//double newRating = (currentRating+review.getReviewScore())/(event.getEventReviews().size()+1);
			
			//customer.addCustomerReviews(newReview);
			//event.addEventReviews(customer, newReview);
			//System.out.println(event.getEventReviews().size());
			//event.setRating(newRating);			
			//eventRepo.save(event);
			//cusRepo.save(customer);
			
			return reviewRepo.save(newReview);
			
			}
			//update 
			
		}
		
		return null;
		}
	
	
	
	@GetMapping("/api/comment")
	public List<Review> findAllComments(){
		
		return (List<Review>) reviewRepo.findAll();
			
		}	
	

	
	
	@GetMapping("/api/comment/customer/{cid}")
	public List<Review> findAllCustomerComment(@PathVariable("cid")  long cid){
		
		Optional<User> u = userRepo.findById(cid);
		if(u.isPresent()) {
			Customer c = (Customer) u.get();
					
			return c.getCustomerReviews();
		}		
			return null;
			
		}	
	
	
	@GetMapping("/api/comment/{eid}")
	public List<Review> findAllEventComment(@PathVariable("eid")  String eid){
		
		Optional<Event> e = eventRepo.findById(eid);
		if(e.isPresent()) {
			Event newEvent = e.get();
					
			return newEvent.getEventReviews();
		}
			
			
			return null;
			
		}
	
	/*
	@GetMapping("/api/comment/seller{sid}")
	public List<Review> getAllEventReview(@PathVariable("sid")  long sid){
		
		Optional<Event> e = eventRepo.findById(eid);
		if(e.isPresent()) {
			Event newEvent = e.get();
					
			return newEvent.getEventReviews();
		}
			
			
			return null;
			
		}	*/
	
	
		
	
     
	
	
	
	}
	
	
	
	
	


