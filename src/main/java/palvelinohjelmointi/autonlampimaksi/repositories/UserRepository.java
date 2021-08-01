package palvelinohjelmointi.autonlampimaksi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import palvelinohjelmointi.autonlampimaksi.models.Enterprise;
import palvelinohjelmointi.autonlampimaksi.models.User;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {
	User findUserByUsername(String username);
	List<User> findByEnterprise(Enterprise enterprise);
}
