package palvelinohjelmointi.autonlampimaksi.controllers;


import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import palvelinohjelmointi.autonlampimaksi.models.Booking;
import palvelinohjelmointi.autonlampimaksi.models.Car;
import palvelinohjelmointi.autonlampimaksi.models.Customer;
import palvelinohjelmointi.autonlampimaksi.models.Defaproduct;
import palvelinohjelmointi.autonlampimaksi.models.Enterprise;
import palvelinohjelmointi.autonlampimaksi.models.Offer;
import palvelinohjelmointi.autonlampimaksi.models.User;
import palvelinohjelmointi.autonlampimaksi.repositories.BookingRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.CarRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.CustomerRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.EnterpriseRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.OfferRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.UserRepository;
import palvelinohjelmointi.autonlampimaksi.services.CarService;
import palvelinohjelmointi.autonlampimaksi.services.OfferService;
import palvelinohjelmointi.autonlampimaksi.services.ParsingService;

@Controller
public class AutoLampimaksiController {
	
	// private static final String url = "https://secure.defa.com/api/eh/searchregm/?regid=ere-523&c=f";
	
	@Autowired
	private ParsingService parsingService;

	@Autowired
	private EnterpriseRepository enterpriseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	
	@RequestMapping(value="/login")
    public String login() {	
		
        return "login";
	}
	
	// TÄMÄ PITÄÄ VARMAAN TOTEUTTAA SECURITY LUOKAN TULLESSA MUKAAN KUVIOIHIN...
	/*
	@PostMapping("/login")
	public String loginUser(@RequestParam String username, @RequestParam String password, RedirectAttributes redirAttrs) {
		User user = userRepository.findUserByUsername(username);
		if (user != null) {
			if (password.equals(user.getPasswordHash())) {
				if (user.getEnterprise() == null) {
					// KULUTTAJA
					Customer customer = customerRepository.findByEmail(username);
					return "redirect:/customerlogged/" + customer.getCustomerId();
				} else {
					// PALVELUNTARJOAJA
					Enterprise ent = user.getEnterprise();
					return "redirect:/enterpriselogged/" + ent.getEnterpriseId();
				}
				
			}
		}
		
		redirAttrs.addFlashAttribute("message", "Käyttäjätunnus tai salasana virheellinen, yritä uudelleen tai rekisteröidy käyttäjäksi");
		return "redirect:/login";
	}
	*/	
		
	@GetMapping("/enterpriselogged/{id}")
	public String enterpriseView(Model model, @PathVariable Long id) {
		Enterprise ent = enterpriseRepository.findById(id).get();
		model.addAttribute("ent", ent);
		return "enterpriselogged";
	}
	
	@GetMapping("/customerlogged/{id}")
	public String customerView(Model model, @PathVariable Long id) {
		Customer customer = customerRepository.findById(id).get();
		String customerName = customer.getCustFirstName() + " " + customer.getCustLastName();
		model.addAttribute("customerName", customerName);
		List<Car> cars = carRepository.findByCustomer(customer);
		model.addAttribute("cars", cars);
		List<Booking> bookings = bookingRepository.findByCustomer(customer);
		model.addAttribute("bookings", bookings);
		return "customerlogged";
	}
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("enterprise", new Enterprise());
		model.addAttribute("yritykset", this.enterpriseRepository.findAll());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getName().equals("anonymousUser")) {
			String username = auth.getName();
			User user = userRepository.findUserByUsername(username);
			
			if (user.getRole().equals("customer")) {
				Customer customer = customerRepository.findByEmail(username);
				List<Car> cars = carRepository.findByCustomer(customer);
				model.addAttribute("cars", cars);
				List<Booking> bookings = bookingRepository.findByCustomer(customer);
				model.addAttribute("bookings", bookings);
			} else {
				Enterprise enterprise = enterpriseRepository.findByName(username);
			}
		}
		
		return "index";
	}
			
	@PostMapping("/addnewenterprise")
	public String addNewEnterpriseForm(@RequestParam String name, @RequestParam String yTunnus, @RequestParam String address, @RequestParam String postinumero,
			@RequestParam String city, RedirectAttributes redirAttrs, @RequestParam String email) {
		if (name=="" || yTunnus==""|| address==""|| postinumero=="" || city=="" || email=="") {
			redirAttrs.addFlashAttribute("message", "Kaikkien kenttien tulee olla täytettyinä");
			return "redirect:/enterprise";
		}
		Enterprise ent = new Enterprise();
		ent.setName(name);
		ent.setYTunnus(yTunnus);
		ent.setAddress(address);
		ent.setPostCode(postinumero);
		ent.setCity(city);
		ent.setEmail(email);
		Enterprise added = enterpriseRepository.save(ent);
		
		return "redirect:/enterprise/" + added.getEnterpriseId();
	}
	
	// TARJOUKSIEN NÄYTTÄMINEN ASIAKKAALLE - UUSI HTML SIVU LIST.HTML
	// LUOKKA TARJOUS => TALLENNETAAN JOKAINEN TARJOUS, AUTO JA ASIAKAS JÄÄVÄT POIS VIELÄ TÄSSÄ VAIHEESSA:)
	@GetMapping("/list/{rek}")
	public String chooseEnterpriseByPrice(Model model, @PathVariable String rek, RedirectAttributes redirAttrs) {
		// JOS AUTO ON JO MERKITTY JOLLEKKIN ASIAKKAALLE NIIN OHJATAAN LOGIN SIVULLE
		// TÄHÄN PITÄISI SAADA FLASH VIESTI => AUTO ON JO MERKITTY ASIAKKAALLE - OLE HYVÄ JA KIRJAUDU SISÄÄN
		if (carRepository.findCarByPlate(rek.toUpperCase()) != null) {
			redirAttrs.addFlashAttribute("message", "Ajoneuvo on jo rekisteröity käyttäjälle, ole hyvä ja kirjaudu nähdäksesi lisätietoja.");
			return "redirect:/login";
		}
		model.addAttribute("offer", new Offer());
		List<Defaproduct> productsFit = carService.getDefaproductsByCar(carService.getCarByRegisterplate(rek));
		// ANTAA HERJAN ARRAYLIST => MUKA TYHJÄ? VAIKKA LÖYTYY OSA
		List<String> parts = offerService.getPartsOfTheOffer(productsFit.get(0));
		List<Offer> offers = new ArrayList<>();
		for (Enterprise enterprise : enterpriseRepository.findAll()) {
			offers.add(offerRepository.save(offerService.newOffer(enterprise, productsFit)));
			//offerRepository.save(offerService.newOffer(enterprise, productsFit));
		}
				
		model.addAttribute("offers", offers);
		model.addAttribute("parts", parts);
		model.addAttribute("rek", rek);
		
		return "list";
	}
	
	@GetMapping("/list/offer/{id}/{rek}")
	public String customerChoseAnOffer(@PathVariable Long id, @PathVariable String rek, Model model, @Validated Customer customer, BindingResult bd) {
		Offer offer = offerRepository.findById(id).get();
		model.addAttribute("customer", new Customer());
		model.addAttribute("offer", offer);
		model.addAttribute("rek", rek);
		return "offer";
	}
	
	@PostMapping("/addnewcustomer/{offerid}/{rek}")
	public String addUserForCustomer(@PathVariable Long offerid, @PathVariable String rek, Model model, @Validated Customer customer, BindingResult bd) {
		if (bd.hasErrors()) {
			return "redirect:/addnewcustomer/" + offerid + "/" + rek;
		}
		
		Customer added = customerRepository.save(customer);
		
		return "redirect:/reguser/" + offerid + "/" + rek + "/" + added.getCustomerId();
	}
	
	// TÄHÄN KOHTAAN VIELÄ: REK, ASIAKASID JA SITTEN ASIAKKAAN LIITTÄMINEN AUTOON JA TARJOUKSEN VAHVISTAMINEN VARAUKSEKSI
	@GetMapping("/reguser/{offerid}/{rek}/{custid}")
	public String regCustomersUser(@PathVariable Long offerid, @PathVariable String rek, @PathVariable Long custid, Model model) {
		//model.addAttribute("user", new User());
		Offer offer = offerRepository.findById(offerid).get();
		Enterprise ent = offer.getEnterprise();
		model.addAttribute("jono", ent.getFreeInDays());
		Customer customer = customerRepository.findById(custid).get();
		model.addAttribute("email", customer.getEmail());
		return "customeruser";
	}
	
	@PostMapping("/reguser/{offerid}/{rek}/{custid}")
	public String addCustomersUser(@PathVariable Long offerid, @PathVariable String rek, @PathVariable Long custid, Model model, RedirectAttributes redirAttrs,
			@RequestParam String firstPwrd, @RequestParam String secondPwrd, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookedDate,
			BCryptPasswordEncoder crypt) {
		
		if (firstPwrd.equals(secondPwrd) && bookedDate != null) {
			Booking booking = new Booking();
			Car car = carService.getCarByRegisterplate(rek);
			car.setCustomer(customerRepository.findById(custid).get());
			carRepository.save(car);
			Offer offer = offerRepository.findById(offerid).get();
			Enterprise ent = offer.getEnterprise();
			offer.setCar(car); 
			offerRepository.save(offer);			
			booking.setOffer(offer);
			User user = new User();
			user.setUsername(customerRepository.findById(custid).get().getEmail());
			user.setPasswordHash(crypt.encode(secondPwrd));
			user.setRole("customer");
			userRepository.save(user);
			booking.setBookedDate(bookedDate);
			booking.setCustomer(customerRepository.findById(custid).get());
			bookingRepository.save(booking);
			redirAttrs.addFlashAttribute("message", "Kiitos ajanvarauksestasi, aika varattu " + ent.getName() + ". Varaus tehty päivälle: " + bookedDate);
		} else if (!firstPwrd.equals(secondPwrd)) {
			redirAttrs.addFlashAttribute("message", "Salasanojen pitää olla samat, yritäs uudelleen");
			return "redirect:/reguser/" + offerid + "/" + rek + "/" + custid;
		} 
				
		return "redirect:/";
	}
	
	@GetMapping("/enterprise/{id}")
	public String enterpriseUser(Model model, @PathVariable Long id) {
		Enterprise ent = enterpriseRepository.findById(id).get();
		model.addAttribute("enterprise", ent.getEmail());
				
		return "enterpriseuser";
	}
	
	@PostMapping("/enterprise/{id}")
	public String addNewUser(@PathVariable Long id, @RequestParam String password, @RequestParam String passwordAgain,
			RedirectAttributes redirAttrs, BCryptPasswordEncoder crypt) {
		if (password=="" || passwordAgain=="") {
			redirAttrs.addFlashAttribute("message", "Kaikki kentät on täytettävä");
			return "redirect:/enterprise/" + id;
		} 
		if (!password.equals(passwordAgain)) {
			redirAttrs.addFlashAttribute("message", "Salasanojen tulee olla keskenään samat");
			return "redirect:/enterprise/" + id;
		}
		
		Enterprise ent = enterpriseRepository.findById(id).get();
		User user = new User();
		user.setEnterprise(ent);
		user.setUsername(ent.getEmail());
		user.setPasswordHash(crypt.encode(passwordAgain));
		user.setRole("enterprise");
		userRepository.save(user);
		
		return "redirect:/enterpriselogged/" + id;
	}
		
	@GetMapping("/rekisterointi")
	public String enterpriseRegister(Model model) {
		return "enterprise";
	}
	
	
	// LOGIN
	
}
