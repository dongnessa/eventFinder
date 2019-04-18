package eventFinderServer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import eventFinderServer.model.Event;
import eventFinderServer.repository.EventRepository;
import eventFinderServer.repository.SellerRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600,allowCredentials = "true")
public class SellerService {
	
	@Autowired
	SellerRepository sellRepo;
	@Autowired
	EventRepository eventRepo;
	
	
	@GetMapping("/api/seller/{sellerId}/event")
	public List<Event> findEventForSeller(@PathVariable("sellerId") long sellerId){
		List<Event>  allEvents = (List<Event>) eventRepo.findAll();
		List<Event> result = new ArrayList<>();
		
		for (Event e : allEvents) {
			if(e.getSeller()!=null) {
				if(e.getSeller().getId()== sellerId) {
					result.add(e);
				}
			}
		}
		
		return result;
				
	}
	
	
	
	
	
	
	
	
	
	
	

}
