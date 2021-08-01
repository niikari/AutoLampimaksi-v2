package palvelinohjelmointi.autonlampimaksi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierPrice {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long supplierPriceId;
	
	private String productGroup;
	
	private String productNumber;
	
	private Double price;
	
	@ManyToOne
	@JoinColumn(name="supplierId")
	private Supplier supplier;
	
	public SupplierPrice(String productGroup, String productNumber, Double price) {
		this.productGroup = productGroup;
		this.productNumber = productNumber;
		this.price = price;
	}
}
