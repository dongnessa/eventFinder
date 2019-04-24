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
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
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
			Event e = data.get();
		    //Seller s = e.getSeller();
			//e.setBusiness_id(s.getId().toString());
			return e;
		}
		return null;
		
	}
	
	//
	@PostMapping("/api/event/create")
	public boolean createEvent(@RequestBody Event event,HttpSession session ) {
		
		
			User currentUser = (User) session.getAttribute("currentUser");
			//if(currentUser.getUserType().equals("SELLER_USER")){
		    Optional<User> u = userRepo.findById(currentUser.getId());
		    
		   if(u.isPresent()) {
			 Seller s = (Seller)u.get();
			
		  Event newEvent = new Event();
		   
		  newEvent.setAddress(event.getAddress());
		  newEvent.setName(event.getName());
		  newEvent.setCategory(event.getCategory());
		  newEvent.setAttending_count(event.getAttending_count());
		  newEvent.setCity(event.getCity());
		  newEvent.setEvent_site_url(event.getEvent_site_url());
		  newEvent.setCountry(event.getCountry());
		  newEvent.setDescription(event.getDescription());
		  newEvent.setImage_url(event.getImage_url());
		  newEvent.setInterested_count(event.getInterested_count());
		  newEvent.setCost(event.getCost());
		  newEvent.setRating(event.getRating());
		 newEvent.setId(event.getId());
		 newEvent.setState(event.getState());
		 newEvent.setZip_code(event.getZip_code());
		  newEvent.setTime_end(event.getTime_end());
		  newEvent.setTime_start(event.getTime_start());
		  newEvent.setInterested_count(event.getInterested_count());
		  newEvent.setTicket_url(event.getTicket_url());
		  newEvent.setBusiness_id(s.getId().toString());
		  s.setEvent(newEvent);
		  //newEvent.setSeller(s);
		
		   //sellRepo.save(s);	 			
			  eventRepo.save(newEvent);
			  sellRepo.save(s);
			 
			//}
			return true;
		   }
		   
		   return false;
		}
		
	  
	
	@GetMapping("/api/event/{eid}/seller")
	public Seller findSellerByEventId(@PathVariable("eid") String eid) {
		Optional<Event> e = eventRepo.findEventByEvent_id(eid);
		if(e.isPresent()) {
			Event data = e.get();
			Seller s = data.getSeller();
			return s;
		}
		return null;
	}
	
	
	/*
	@DeleteMapping("/api/event/{eventId}")
	public void deleteEvent(@PathVariable("eventId") String id) {
		if(eventRepo.existsById(id)) {
		eventRepo.deleteById(id);
		}
	}*/
	
	
	@DeleteMapping("/api/event/{eventId}")
	public boolean deleteEvent(@PathVariable("eventId") String id,HttpSession session) {
		 if(session.getAttribute("currentUser")==null) {
		    	
		    	return false;
		    }
		
		User u  = (User)session.getAttribute("currentUser");
		long sid = u.getId();
	 Optional<User> data = userRepo.findById(sid);
	 Optional<Event> e = eventRepo.findById(id);
		if(e.isPresent() && data.isPresent()) {
			Seller s = (Seller) data.get();	
			Event e1 = e.get();
			if(s.getUserType().equals("SELLER_USER")&&s.getEvents().contains(e1));
		eventRepo.deleteById(id);
		return  true;
		}
		 return false;
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
