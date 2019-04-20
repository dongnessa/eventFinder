package eventFinderServer.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eventFinderServer.model.Customer;
import eventFinderServer.model.Event;
import eventFinderServer.model.Seller;
import eventFinderServer.model.User;
import eventFinderServer.repository.CustomerRepository;
import eventFinderServer.repository.EventRepository;
import eventFinderServer.repository.SellerRepository;
import eventFinderServer.repository.UserRepository;

@Service
@RestController
@CrossOrigin(origins = "*",maxAge=3600,allowCredentials = "true")
public class EventService {
	

	@Autowired
	private EventRepository eventRepo;
	@Autowired
	SellerRepository sellRepo;
	@Autowired
	CustomerRepository customerRepo;
	@Autowired
	UserRepository userRepo;
	/*
	@GetMapping("/api/event")
	public List<Event> findAllEvents( HttpServletRequest request, HttpServletResponse response){
		/*
		  
		 
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		      return null;	
		} else {
			User currentUser = (User) session.getAttribute("currentUser");
			if(currentUser.getUserType().equals("ADMIN_USER")){
				return (List<Event>) eventRepo.findAll();
			}
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	    return null;
		
		
		
		
	}*/

@GetMapping("/api/event")
	public List<Event> findAllEvents( ){
	
	return (List<Event>) eventRepo.findAll();
	
	
}
	
	
	@GetMapping("/api/event/{eventId}")
	public Event findEventById(@PathVariable ("eventId") String id ) {
		Optional<Event> data = eventRepo.findById(id);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
		
	}
	
	//
	@PostMapping("/api/event/create")
	public Event createEvent(@RequestBody Event event,HttpSession session ) {
		
			//User currentUser = (User) session.getAttribute("currentUser");
			//if(currentUser.getUserType().equals("SELLER_USER")){
		event.setSeller((Seller)session.getAttribute("currentUser"));
			 			
				return eventRepo.save(event);
			//}
			
			//return null;
		}
		
	  
		
		
				
	
	
	@DeleteMapping("/api/event/{eventId}")
	public void deleteEvent(@PathVariable("eventId") String id) {
		if(eventRepo.existsById(id)) {
		eventRepo.deleteById(id);
		}
	}
	
	
	
	@PutMapping("/api/event/{eventId}")
	public Event updateEvent(@PathVariable("eventId")String eventId, @RequestBody Event newEvent) {
		Optional <Event> data = eventRepo.findById(eventId);
		
		if(data.isPresent()) {
			Event e = data.get();
			newEvent.setAddress(e.getAddress());
			newEvent.setName(e.getName());
			newEvent.setDescription(e.getDescription());
			newEvent.setImage_url(e.getEvent_site_url());
			
			return eventRepo.save(newEvent);
		}
		return null;
		
	}
	
	
	
	
	
	
	
	

	
	
}
