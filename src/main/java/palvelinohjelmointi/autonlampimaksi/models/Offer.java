package palvelinohjelmointi.autonlampimaksi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long offerId;
	
	private double totalPrice;

	@ManyToOne
	@JoinColumn(name="defaproductId")
	private Defaproduct product;
	
	@ManyToOne
	@JoinColumn(name="enterpriseId")
	private Enterprise enterprise;
	
	@ManyToOne
	@JoinColumn(name="carId")
	private Car car;
	
	@OneToOne(mappedBy="offer")
	private Booking booking;
}
