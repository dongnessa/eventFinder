package eventFinderServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import eventFinderServer.model.Event;
import eventFinderServer.repository.EventRepository;

@Service
@RestController
@CrossOrigin(origins = "*",maxAge=3600,allowCredentials = "true")
public class EventService {

	@Autowired
	private EventRepository eventRepo;
	
	
	@GetMapping("/api/event")
	
	public List<Event> findAllEvents(){
		return (List<Event>) eventRepo.findAll();
	}
	
	
	
}
