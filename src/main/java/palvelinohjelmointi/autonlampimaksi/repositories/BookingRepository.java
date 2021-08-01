package palvelinohjelmointi.autonlampimaksi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import palvelinohjelmointi.autonlampimaksi.models.Booking;
import palvelinohjelmointi.autonlampimaksi.models.Customer;
import palvelinohjelmointi.autonlampimaksi.models.Offer;

public interface BookingRepository extends CrudRepository<Booking, Long> {
	List<Booking> findByCustomer(Customer customer);
}
