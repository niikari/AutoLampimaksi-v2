package palvelinohjelmointi.autonlampimaksi.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Defaproduct {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long defaproductId;
	
	private String model;
	private String modyear;
	private String enginecode;
	private String engineheaters;
	private String place;
	private String kytkikset;
	private String haaroitukset;
	private String aika;
	private String engineheatersMore;
	
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="product")
	List<Offer> offers;
	
}
