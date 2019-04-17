package eventFinderServer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import eventFinderServer.model.Seller;

public interface SellerRepository  extends CrudRepository<Seller, Long>{
	@Query("SELECT user FROM Seller user WHERE  user.username=:username")
	Optional <Seller> findUserByUsername(@Param("username") String username);
	@Query("SELECT u FROM Seller u WHERE u.username=:username AND u.password=:password")
	  Optional<Seller> findUserByCredentials(@Param("username") String username,
	                                       @Param("password") String password);

}
