package palvelinohjelmointi.autonlampimaksi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import palvelinohjelmointi.autonlampimaksi.models.Enterprise;

@RepositoryRestResource
public interface EnterpriseRepository extends CrudRepository<Enterprise, Long> {
	Enterprise findByName(String name);
}
