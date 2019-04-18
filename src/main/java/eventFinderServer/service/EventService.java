package eventFinderServer.service;

import java.util.List;
import java.util.Optional;

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

import eventFinderServer.model.Event;
import eventFinderServer.repository.EventRepository;
import eventFinderServer.repository.SellerRepository;

@Service
@RestController
@CrossOrigin(origins = "*",maxAge=3600,allowCredentials = "true")
public class EventService {

	@Autowired
	private EventRepository eventRepo;
	@Autowired
	SellerRepository sellRepo;
	
	@GetMapping("/api/event")
	public List<Event> findAllEvents(){
		return (List<Event>) eventRepo.findAll();
	}
	
	/*
	@GetMapping("/api/event/{eventId}")
	public Event findEventById(@PathVariable ("eventId") String id ) {
		Optional<Event> data = eventRepo.findById(id);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
		
	}*/
	
	//
	@PostMapping("/api/event/create")
	public Event createEvent(@RequestBody Event event) {
		return eventRepo.save(event);
				
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
