package palvelinohjelmointi.autonlampimaksi.repositories;

import org.springframework.data.repository.CrudRepository;

import palvelinohjelmointi.autonlampimaksi.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	Customer findByEmail(String email);
}
