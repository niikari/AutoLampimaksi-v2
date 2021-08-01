package palvelinohjelmointi.autonlampimaksi.services;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import palvelinohjelmointi.autonlampimaksi.models.Car;
import palvelinohjelmointi.autonlampimaksi.models.Defaproduct;
import palvelinohjelmointi.autonlampimaksi.repositories.CarRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.DefaproductRepository;

@Service
public class CarService {

	@Autowired
	private ParsingService parsingService;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private DefaproductRepository defaRepository;
	
	// For consept of proof! => should be changed to payd service
	// private String url = "https://secure.defa.com/api/eh/searchregm/?regid=";
	
	//ere-523&c=f
	
	public List<Car> allSearchedCars() {
		List<Car> cars = (List<Car>) this.carRepository.findAll();
		return cars;
	}
	
	public List<Defaproduct> getDefaproductsByCar(Car car) {
		List<Defaproduct> products = defaRepository.findByEnginecodeAndModyearAndEngineheaters(car.getDevaEngineCode(), car.getModyear(), car.getEngineheater());
		if (products.isEmpty()) {
			// ARVOTAAN TESTIN VUOKSI JOKIN TUOTE
			int id = 1 + (int)(Math.random() * ((610 - 1) + 1));
			Long i = Long.valueOf(id);
			products.add(defaRepository.findById(i).get());
		}
		return products;
	}
	
	
	public Car getCarByRegisterplate(String plate) {
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		Car car = new Car();
		Map<String, Object> map = new HashMap<>();
		String url = "https://secure.defa.com/api/eh/searchregm/?regid=";
		url += plate + "&c=f";
		// System.out.println(url);
		try {
			map = objectMapper.readValue(new URL(url),new TypeReference<Map<String,Object>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String rivi = "";
		int i = 0;
		// VOI TEHDÄ TARKEMMAKSI JOS OSAISI KÄSITELLÄ JSON TIEDOSTON OIKEIN...
		for (Object o : map.keySet()) {
			rivi = map.get(o).toString();
			if (i > 0) {
				String[] palat = rivi.split(",");
				for (String sana : palat) {					
					sana = sana.replace("{", "");
					sana = sana.replace("[", "");
					sana = sana.replace("}", "");
					sana = sana.replace("]", "");
					sana = sana.replace(" ", "");
					//System.out.println(sana);
					//sana = sana.replace("-", "");
					
					String[] j = sana.split("=");
					if (j[0].equals("make")) {
						car.setMake(j[1].trim());						
					}
					if (j[0].equals("model")) {
						car.setModel(j[1].trim());
					}
					if (j[0].equals("engine")) {
						car.setEngine(j[1].trim());
					}
					if (j[0].equals("fuel")) {
						car.setFuel(j[1].trim());
					}
					if (j[0].equals("modyear")) {
						car.setModyear(j[1].trim());
					} 
					if (j[0].equals("engineheater")) {
						car.setEngineheater(j[1].trim());
					}
					if (j[0].equals("enginecode")) {
						car.setDevaEngineCode(j[1].trim());
					}
					car.setPlate(plate.toUpperCase());
				}
			}			
			
			i++;
		}
		
		return car;
		
		
	}
}
