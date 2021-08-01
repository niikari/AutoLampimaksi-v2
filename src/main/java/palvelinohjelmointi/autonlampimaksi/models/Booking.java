package palvelinohjelmointi.autonlampimaksi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long bookingId;
	
	private LocalDate bookedDate;
	
	@OneToOne
	@JoinColumn(name="offerid")
	private Offer offer;

	@OneToOne
	@JoinColumn(name="customerid")
	private Customer customer;
}
