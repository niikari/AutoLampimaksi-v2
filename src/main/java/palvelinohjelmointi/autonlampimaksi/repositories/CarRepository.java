package palvelinohjelmointi.autonlampimaksi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import palvelinohjelmointi.autonlampimaksi.models.Car;
import palvelinohjelmointi.autonlampimaksi.models.Customer;

public interface CarRepository extends CrudRepository<Car, Long> {
	Car findCarByPlate(String plate);
	List<Car> findByCustomer(Customer customer);
}
