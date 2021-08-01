package palvelinohjelmointi.autonlampimaksi.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enterprise {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long enterpriseId;
	
	@Column(name="name", nullable=false, unique=true)
	private String name;
	
	private String yTunnus;
	private String address;
	private String postCode;
	private String city;
	
	@Column(nullable=false, unique=true)
	private String email;
	
	private double hourRate = 50;
	private double discountHourRate = 0;
	private double discountParts = 0;
	private double timeToInnerCable = 1;
	
	private int freeInDays = 2;
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL,mappedBy="enterprise")
	List<User> users;

	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL,mappedBy="enterprise")
	List<Rating> ratings;
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL,mappedBy="enterprise")
	List<Offer> offers;
	
}
