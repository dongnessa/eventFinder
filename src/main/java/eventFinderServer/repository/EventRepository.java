package eventFinderServer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import eventFinderServer.model.Event;

public interface EventRepository extends CrudRepository<Event, String>{
	@Query("SELECT e FROM Event e WHERE e.id=:event_id")
	  Optional<Event> findEventByEvent_id(
	          @Param("event_id") String event_id);
	//@Query("SELECT e FROM Event e WHERE e.city=:city")
	
	List<Event> findEventByCity(@Param ("city") String city);
	

}
