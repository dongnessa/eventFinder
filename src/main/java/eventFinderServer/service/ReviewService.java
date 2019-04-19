package eventFinderServer.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eventFinderServer.model.Customer;
import eventFinderServer.model.Event;
import eventFinderServer.model.Review;
import eventFinderServer.model.User;
import eventFinderServer.repository.EventRepository;
import eventFinderServer.repository.ReviewRepository;

@Service
@RestController
@CrossOrigin(origins = "*",maxAge=3600,allowCredentials = "true")
public class ReviewService {
	
	@Autowired
	ReviewRepository reviewRepo;
	@Autowired
	EventRepository eventRepo;
	
	
	/*
	@PostMapping("api/comment/{eid}") 
	public Review writeReviewForEvent(@RequestBody Review review, @PathVariable("eid") String eid, HttpSession session ) {
		Customer user = (Customer)session.getAttribute("currentUser");
		if (user.getUserType().equals("CUSTOMER_USER")) {
		
		Optional<Event> data = eventRepo.findById(eid);
		if (data.isPresent()) {
			Event event = data.get();
			Review newReview = new Review();
			newReview.setReviewScore(review.getReviewScore());
			newReview.setText(review.getText());	
			newReview.setEvent(event);
			
			
			//update 
			
		}
		
		
		}
		
		
	}
	*/
	
	
	
	

}
