package palvelinohjelmointi.autonlampimaksi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import palvelinohjelmointi.autonlampimaksi.models.Supplier;

@RepositoryRestResource
public interface SupplierRepository extends CrudRepository<Supplier, Long> {
	Supplier findByName(String name);
}
