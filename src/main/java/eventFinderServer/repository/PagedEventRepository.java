package eventFinderServer.repository;

import org.springframework.data.repository.CrudRepository;

import eventFinderServer.model.Event;

public interface PagedEventRepository extends CrudRepository<Event, Long> {
	

}
