package palvelinohjelmointi.autonlampimaksi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import palvelinohjelmointi.autonlampimaksi.controllers.AutoLampimaksiController;
import palvelinohjelmointi.autonlampimaksi.repositories.EnterpriseRepository;
import palvelinohjelmointi.autonlampimaksi.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AutonlampimaksiApplicationTests {
	
	@Autowired
	private AutoLampimaksiController controller;

	@Autowired
	private EnterpriseRepository enterpriseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	//TÄNNE TESTEJÄ!
	
}
