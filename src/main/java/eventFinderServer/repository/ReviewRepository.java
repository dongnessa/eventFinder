package eventFinderServer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import eventFinderServer.model.Review;
import eventFinderServer.model.ReviewAssociationId;

public interface ReviewRepository extends CrudRepository<Review, Review.ReviewAssociationId>{
	
 

}
