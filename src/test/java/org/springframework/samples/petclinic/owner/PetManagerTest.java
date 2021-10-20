package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PetManagerTest {
	@Autowired
	private OwnerRepository repository;
	@Autowired
	private PetTimedCache petTimedCache;


	@Test
	void testFindPet() {
		Logger log = LoggerFactory.getLogger("CRITICAL_LOGGER");
		PetManager pm = new PetManager(petTimedCache, repository, log);
		System.out.println(pm.getOwnerPets(1));
	}

	@Test
	void testTheTest() {
		Logger log = LoggerFactory.getLogger("CRITICAL_LOGGER");
		PetManager pm = new PetManager(petTimedCache, repository, log);
		System.out.println(pm.getOwnerPets(2));
	}

}
