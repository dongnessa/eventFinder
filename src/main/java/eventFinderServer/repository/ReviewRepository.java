package eventFinderServer.repository;

import org.springframework.data.repository.CrudRepository;

import eventFinderServer.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Long>{
	

}
