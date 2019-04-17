package eventFinderServer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import eventFinderServer.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT s FROM User s WHERE s.username=:username")
   Optional <User> findUserByUsername(@Param("username") String username);
	
	@Query("SELECT u FROM User u WHERE u.username=:username AND u.password=:password")
	  Optional<User> findUserByCredentials(@Param("username") String username,
	                                       @Param("password") String password);
}
