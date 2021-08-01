package palvelinohjelmointi.autonlampimaksi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private int rating;
	
	private String text;
	
	@ManyToOne
	@JoinColumn(name="enterpriseId")
	private Enterprise enterprise;
	
	@ManyToOne
	@JoinColumn(name="customerid")
	private Customer customer;
	
	public void setRating(int rating) {
		if (rating <= 5 && rating >= 0) {
			this.rating = rating;
		}
	}

}
