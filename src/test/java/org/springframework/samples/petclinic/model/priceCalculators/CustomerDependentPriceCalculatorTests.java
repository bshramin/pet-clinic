package org.springframework.samples.petclinic.model.priceCalculators;

import org.joda.time.DateTime;
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
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= ApplicationTestConfig.class)
@WebAppConfiguration
public class CustomerDependentPriceCalculatorTests {
	@Test
	public void testCalcPriceNoPets() {
		CustomerDependentPriceCalculator cdpc = new CustomerDependentPriceCalculator();
		Double baseCharge = 20.0;
		Double basePricePerPet = 40.0;
		UserType userType = UserType.NEW;
		List<Pet> pets = new ArrayList<Pet>();

		Double result = 0.0;
		Double totalPrice = cdpc.calcPrice(pets,baseCharge,basePricePerPet, userType);
		assertEquals(result,totalPrice);
	}

	@Test
	public void testCalcPriceGoldUser() {
		CustomerDependentPriceCalculator cdpc = new CustomerDependentPriceCalculator();
		Double baseCharge = 100.0;
		Double basePricePerPet = 200.0;
		UserType userType = UserType.GOLD;
		List<Pet> pets = new ArrayList<Pet>();
		Pet pet1 = new Pet();
		pet1.setType(new PetType());
		pets.add(pet1);
		Double result = 368.8;
		Double totalPrice = cdpc.calcPrice(pets,baseCharge,basePricePerPet, userType);
		assertEquals(result,totalPrice);
	}

	@Test
	public void testCalcPriceDiscountReachedNewUser() {
		CustomerDependentPriceCalculator cdpc = new CustomerDependentPriceCalculator();
		Double baseCharge = 20.0;
		Double basePricePerPet = 40.0;
		UserType userType = UserType.NEW;
		List<Pet> pets = new ArrayList<Pet>();
		Pet pet1 = new Pet();
		pet1.setType(new PetType());
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		Double result = 722.24;
		Double totalPrice = cdpc.calcPrice(pets,baseCharge,basePricePerPet, userType);
		assertEquals(result,totalPrice);
	}

	@Test
	public void testCalcPriceDiscountReachedNormalUser() {
		CustomerDependentPriceCalculator cdpc = new CustomerDependentPriceCalculator();
		Double baseCharge = 200.0;
		Double basePricePerPet = 400.0;
		UserType userType = UserType.GOLD;
		List<Pet> pets = new ArrayList<Pet>();
		Pet pet1 = new Pet();
		pet1.setBirthDate(new Date());
		pet1.setType(new PetType());
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		pets.add(pet1);
		Pet pet2 = new Pet();
		pet2.setType(new PetType());
		pet2.setBirthDate(new Date(2019));
		pets.add(pet2);

		Pet pet3 = new Pet();
		PetType customPetType = mock(PetType.class);
		when(customPetType.getRare()).thenReturn(false);
		pet3.setType(customPetType);
		pets.add(pet3);

		Double result = 6304.0;
		Double totalPrice = cdpc.calcPrice(pets,baseCharge,basePricePerPet, userType);
		assertEquals(result,totalPrice);
	}
}
