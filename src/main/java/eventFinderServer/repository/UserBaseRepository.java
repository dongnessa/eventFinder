package eventFinderServer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import eventFinderServer.model.User;



@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends CrudRepository<T, Long>{
	public User findByusername(String username);
}
