package palvelinohjelmointi.autonlampimaksi.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long customerId;

	private String custFirstName;
	private String custLastName;
	private String custAddress;
	private String custPostcode;
	private String custCity;
	private String phone;
	
	@Column(name="email", nullable=false, unique=true)
	private String email;

	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL,mappedBy="customer")
	List<Car> cars;	

	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL,mappedBy="customer")
	List<Rating> ratings;
	
	@OneToOne(mappedBy="customer")
	private Booking booking;
}
