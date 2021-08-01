package palvelinohjelmointi.autonlampimaksi.models;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@JsonIgnoreProperties
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long carId;
	
	@Column(name="plate", unique=true, nullable=false)
	private String plate;
	
	private String make;
	private String model;
	private String engine;
	private String fuel;
	public String modyear;
	private String engineheater;
	private String devaEngineCode;
	
	@ManyToOne
	@JoinColumn(name="customerid")
	private Customer customer;

	@OneToMany(cascade=CascadeType.ALL,mappedBy="car")
	List<Offer> offers;
		
}
 