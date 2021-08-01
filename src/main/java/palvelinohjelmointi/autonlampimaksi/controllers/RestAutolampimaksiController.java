package palvelinohjelmointi.autonlampimaksi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import palvelinohjelmointi.autonlampimaksi.models.Car;
import palvelinohjelmointi.autonlampimaksi.models.Customer;
import palvelinohjelmointi.autonlampimaksi.models.Enterprise;
import palvelinohjelmointi.autonlampimaksi.models.Rating;
import palvelinohjelmointi.autonlampimaksi.models.Supplier;
import palvelinohjelmointi.autonlampimaksi.repositories.CustomerRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.EnterpriseRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.RatingRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.SupplierRepository;
import palvelinohjelmointi.autonlampimaksi.services.CarService;

@RestController
public class RestAutolampimaksiController {
	
	@Autowired
	private EnterpriseRepository enterpriseReposity;
	
	@Autowired
	private SupplierRepository supplierRepository;
		
	@Autowired
	private CarService carService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@GetMapping("/enterprises")
	@ResponseBody
	public List<Enterprise> getAllEnterprises() {
		return (List<Enterprise>) this.enterpriseReposity.findAll();
	}
	
	@PostMapping("/enterprises")
	public Enterprise addEnterprise(@RequestBody Enterprise enterprise) {
		
		return this.enterpriseReposity.save(enterprise);
	}
	
	@PostMapping("/customerlogged/newrating/{entid}/{custid}/{rating}")
	public Rating newRating(@PathVariable Long entid, @PathVariable Long custid, @PathVariable int rating) {
		Rating newRating = new Rating();
		if (rating >= 0 && rating <= 5) {
			newRating.setRating(rating);
			Enterprise ent = enterpriseReposity.findById(entid).get();
			Customer customer = customerRepository.findById(custid).get();
			newRating.setCustomer(customer);
			newRating.setEnterprise(ent);			
		}
		return ratingRepository.save(newRating);
	}
	
	@GetMapping("/suppliers")
	@ResponseBody
	public List<Supplier> getAllSuppliers() {
		return (List<Supplier>) this.supplierRepository.findAll();
	}
	//ss
	
	
	@GetMapping("/cars/{plate}")
	@ResponseBody
	public Car returnACarByLicense(@PathVariable(name="plate") String plate) {
		return this.carService.getCarByRegisterplate(plate);		
	}
	
	@GetMapping("/cars")
	@ResponseBody
	public void returnAllCars() {
		//return this.carService.allSearchedCars();
		System.out.println("Ihan jossain muualla");
	}
	
}
