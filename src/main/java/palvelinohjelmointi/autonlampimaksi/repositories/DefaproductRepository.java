package palvelinohjelmointi.autonlampimaksi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import palvelinohjelmointi.autonlampimaksi.models.Defaproduct;

public interface DefaproductRepository extends CrudRepository<Defaproduct, Long> {
	
	List<Defaproduct> findByEnginecodeAndModyearAndEngineheaters(String enginecode, String modyear, String engineheaters);
}
