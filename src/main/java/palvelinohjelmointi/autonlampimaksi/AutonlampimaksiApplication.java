package palvelinohjelmointi.autonlampimaksi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import palvelinohjelmointi.autonlampimaksi.models.Car;
import palvelinohjelmointi.autonlampimaksi.models.Customer;
import palvelinohjelmointi.autonlampimaksi.models.Defaproduct;
import palvelinohjelmointi.autonlampimaksi.models.Enterprise;
import palvelinohjelmointi.autonlampimaksi.models.Supplier;
import palvelinohjelmointi.autonlampimaksi.models.SupplierPrice;
import palvelinohjelmointi.autonlampimaksi.models.User;
import palvelinohjelmointi.autonlampimaksi.repositories.CarRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.CustomerRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.DefaproductRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.EnterpriseRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.SupplierRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.UserRepository;
import palvelinohjelmointi.autonlampimaksi.services.CarService;

@SpringBootApplication
public class AutonlampimaksiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutonlampimaksiApplication.class, args);
	}

	@Bean
	public CommandLineRunner testiTiedonLataaminen(DefaproductRepository defaproductRepository, EnterpriseRepository entRepo,
			UserRepository userRepo, BCryptPasswordEncoder crypt) {
		return (args) -> {
			//BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();		
			//System.out.println(lueDefaTiedosto().size());
			List<Defaproduct> kamat= lueDefaa();
			for (Defaproduct dp : kamat) {
				defaproductRepository.save(dp);
			}
			
			// LUODAAN MUUTAMIA YRITYKSIÄ VALMIIKSI JA LIITETÄÄN NIIHIN KÄYTTÄJIÄ
			Enterprise ent = new Enterprise();
			ent.setName("S-Autohuolto");
			ent.setAddress("Laaksotie 35");
			ent.setPostCode("01390");
			ent.setCity("Vantaa");
			ent.setEmail("s-autohuolto@vantaa.fi");
			ent.setHourRate(86.50);
			ent.setTimeToInnerCable(0.9);
			ent.setFreeInDays(1);
			entRepo.save(ent);
			
			User user = new User();
			user.setEnterprise(ent);
			user.setRole("enterprise");
			user.setUsername(ent.getEmail());
			user.setPasswordHash(crypt.encode(ent.getName()));
			userRepo.save(user);
			
			Enterprise ent2 = new Enterprise();
			ent2.setName("Helsingin huoltopiste Oy");
			ent2.setAddress("Parivaljakonkuja 2");
			ent2.setPostCode("00410");
			ent2.setCity("Helsinki");
			ent2.setEmail("huoltopiste@helsinki.fi");
			ent2.setHourRate(76.50);
			ent2.setTimeToInnerCable(1);
			ent2.setFreeInDays(3);
			entRepo.save(ent2);
			
			User user2 = new User();
			user2.setEnterprise(ent2);
			user2.setRole("enterprise");
			user2.setUsername(ent2.getEmail());
			user2.setPasswordHash(crypt.encode(ent2.getName()));
			userRepo.save(user2);
			
			Enterprise ent3 = new Enterprise();
			ent3.setName("AKJ autohuolto");
			ent3.setAddress("palokärjentie 3");
			ent3.setPostCode("02660");
			ent3.setCity("Espoo");
			ent3.setEmail("akj@google.fi");
			ent3.setHourRate(82.50);
			ent3.setTimeToInnerCable(0.7);
			ent3.setFreeInDays(1);
			entRepo.save(ent3);
			
			User user3 = new User();
			user3.setEnterprise(ent3);
			user3.setRole("enterprise");
			user3.setUsername(ent3.getEmail());
			user3.setPasswordHash(crypt.encode(ent3.getName()));
			userRepo.save(user3);
			
			Enterprise ent4 = new Enterprise();
			ent4.setName("BestDrive Olarinluoma");
			ent4.setAddress("Olarinluoma 11");
			ent4.setPostCode("02200");
			ent4.setCity("Espoo");
			ent4.setEmail("bestdrive@olari.fi");
			ent4.setHourRate(96.50);
			ent4.setTimeToInnerCable(1);
			ent4.setFreeInDays(4);
			entRepo.save(ent4);
			
			User user4 = new User();
			user4.setEnterprise(ent4);
			user4.setRole("enterprise");
			user4.setUsername(ent4.getEmail());
			user4.setPasswordHash(crypt.encode(ent4.getName()));
			userRepo.save(user4);
			
			Enterprise ent5 = new Enterprise();
			ent5.setName("Primpex Autohuolto Oy");
			ent5.setAddress("Hämeentie 103");
			ent5.setPostCode("00550");
			ent5.setCity("Helsinki");
			ent5.setEmail("putohuolto@helsinki.fi");
			ent5.setHourRate(100.50);
			ent5.setTimeToInnerCable(0.5);
			ent5.setFreeInDays(5);
			entRepo.save(ent5);
			
			User user5 = new User();
			user5.setRole("enterprise");
			user5.setEnterprise(ent5);
			user5.setUsername(ent5.getEmail());
			user5.setPasswordHash(crypt.encode(ent5.getName()));
			userRepo.save(user5);
			
			Enterprise ent6 = new Enterprise();
			ent6.setName("Fiksuin autokorjaamo");
			ent6.setAddress("Jarrutie 12");
			ent6.setPostCode("00770");
			ent6.setCity("Helsinki");
			ent6.setEmail("fiksua@vantaa.fi");
			ent6.setHourRate(55.50);
			ent6.setTimeToInnerCable(1.2);
			ent6.setFreeInDays(1);
			entRepo.save(ent6);
			
			User user6 = new User();
			user6.setEnterprise(ent6);
			user6.setRole("enterprise");
			user6.setUsername(ent6.getEmail());
			user6.setPasswordHash(crypt.encode(ent6.getName()));
			userRepo.save(user6);
		};
		
		
	}
	
	// LUKEE DEFATIEDOSTON JA PALAUTTAA VAIN 610 RIVIÄ, ENKÄ TAJUA MIKSI (MUTTA TESTIKSI RIITTÄÄ NYT)
	public List<Defaproduct> lueDefaa() {
		List<Defaproduct> kamat = new ArrayList<>();
		try {
			Scanner lukija = new Scanner(new File("defadata.csv"));
			
			
			while (lukija.hasNextLine()) {
				String rivi = lukija.nextLine();
				String[] palat = rivi.split(";");
				if (palat.length == 9) {
					Defaproduct dp = new Defaproduct();
					dp.setModel(palat[0].trim());
					dp.setModyear(palat[1].trim());
					dp.setEnginecode(palat[2].trim());
					dp.setEngineheaters(palat[3].trim());
					dp.setPlace(palat[4].trim());
					dp.setKytkikset(palat[5].trim());
					dp.setHaaroitukset(palat[6].trim());
					dp.setAika(palat[7].trim());
					if (!palat[8].equals("tyhja")) {
						dp.setEngineheatersMore(palat[8].trim());
					}
					kamat.add(dp);
				}
			}
						
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return kamat;
	}
	
	
	// LATAA 1 TOIMITTAJAN HINNASTON TIETOKANTAAN (TIEDOSTO TALLENNETTU TÄMÄN PROJEKTIN JUUREEN)
	public void lueOrumHinnastoTiedosto() {
		
	}
	
	// LATAA 2 TOIMITTAJAN HINNASTON TIETOKANTAAN (TIEDOSTO TALLENNETTU TÄMÄN PROJEKTIN JUUREEN)
	public void lueMekonomenHinnastoTiedosto() {
		
	}
	
}
