package org.springframework.samples.petclinic.model.priceCalculators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;
import org.springframework.samples.petclinic.service.clinicService.ApplicationTestConfig;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= ApplicationTestConfig.class)
@WebAppConfiguration
public class SimplePriceCalculatorTests {
	@Test
	public void testCalcPriceNoPets() {
		SimplePriceCalculator spc = new SimplePriceCalculator();
		Double baseCharge = 20.0;
		Double basePricePerPet = 10.0;
		UserType userType = UserType.SILVER;
		List<Pet> pets = new ArrayList<Pet>();
		Double totalPrice = spc.calcPrice(pets,baseCharge,basePricePerPet, userType);
		assertEquals(totalPrice, baseCharge);
	}

	@Test
	public void testCalcPriceRarePet() {
		SimplePriceCalculator spc = new SimplePriceCalculator();
		Double baseCharge = 20.0;
		Double basePricePerPet = 40.0;
		UserType userType = UserType.SILVER;
		List<Pet> pets = new ArrayList<Pet>();
		Pet pet1 = new Pet();
		pet1.setType(new PetType());
		pets.add(pet1);
		Double result = 68.0;
		Double totalPrice = spc.calcPrice(pets,baseCharge,basePricePerPet, userType);
		assertEquals(result,totalPrice);
	}

	@Test
	public void testCalcPriceNewUser() {
		SimplePriceCalculator spc = new SimplePriceCalculator();
		Double baseCharge = 20.0;
		Double basePricePerPet = 40.0;
		UserType userType = UserType.NEW;
		List<Pet> pets = new ArrayList<Pet>();
		Pet pet1 = new Pet();
		pet1.setType(new PetType());
		pets.add(pet1);

		Pet pet3 = new Pet();
		PetType customPetType = mock(PetType.class);
		when(customPetType.getRare()).thenReturn(false);
		pet3.setType(customPetType);
		pets.add(pet3);

		Double result = 102.6;
		Double totalPrice = spc.calcPrice(pets,baseCharge,basePricePerPet, userType);
		assertEquals(result,totalPrice);
	}
}
