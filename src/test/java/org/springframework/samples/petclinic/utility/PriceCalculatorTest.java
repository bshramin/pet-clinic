package org.springframework.samples.petclinic.utility;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PriceCalculatorTest {
	private static final double baseCharge = 100;
	private static final double basePricePerPet = 100;
	List<Pet> pets;

	@BeforeAll
	public void setUpClass() {
	}

	@BeforeEach
	public void setUp() {
		pets = new ArrayList<>();
	}

	private void mockMaturePet(Pet pet) {
		int yearOfToday = LocalDate.now().getYear();
		LocalDate birthDate = LocalDate.of(yearOfToday - 2 - 1, 1, 1);
		when(pet.getBirthDate()).thenReturn(birthDate);
	}

	private void mockInfantPet(Pet pet)  {
		LocalDate birthDate = LocalDate.now();
		when(pet.getBirthDate()).thenReturn(birthDate);
	}

	private void mockLastVisited(Pet pet, int days) {
		LocalDate visitDate = LocalDate.now().minusDays(days);

		List<Visit> visits = new ArrayList<>();
		Visit mockVisit = mock(Visit.class);
		visits.add(mockVisit);

		when(pet.getVisitsUntilAge(anyInt())).thenReturn(visits);
		when(mockVisit.getDate()).thenReturn(visitDate);
	}

	@Test
	public void emptyPetsTest() {
		assertEquals(0.0, PriceCalculator.calcPrice(pets, baseCharge, basePricePerPet));
	}

	@Test
	public void emptyVisitsPetTest() {
		Pet mockPet = mock(Pet.class);
		mockInfantPet(mockPet);
		pets.add(mockPet);
		double result = PriceCalculator.calcPrice(pets, baseCharge, basePricePerPet);
		assertEquals(168.0,result);
	}


	@Test
	public void infantCounterReachedRecentlyVisitedTest() {
		Pet mockPet = mock(Pet.class);
		mockInfantPet(mockPet);
		mockLastVisited(mockPet, 99);
		pets.add(mockPet);
		pets.add(mockPet);
		pets.add(mockPet);
		pets.add(mockPet);
		pets.add(mockPet);
		double result = PriceCalculator.calcPrice(pets, baseCharge, basePricePerPet);
		assertEquals(1612,result);
	}

	@Test
	public void infantCounterReachedNotRecentlyVisitedTest() {
		Pet mockPet = mock(Pet.class);
		mockInfantPet(mockPet);
		mockLastVisited(mockPet, 100);
		pets.add(mockPet);
		pets.add(mockPet);
		pets.add(mockPet);
		pets.add(mockPet);
		pets.add(mockPet);
		double result = PriceCalculator.calcPrice(pets, baseCharge, basePricePerPet);
		assertEquals(1712,result);
	}

	@Test
	public void infantCounterNotReachedNotRecentlyVisitedTest() {
		Pet mockPet = mock(Pet.class);
		mockInfantPet(mockPet);
		mockLastVisited(mockPet, 100);
		pets.add(mockPet);
		pets.add(mockPet);
		double result = PriceCalculator.calcPrice(pets, baseCharge, basePricePerPet);
		assertEquals(336,result);
	}

	@Test
	public void infantCounterNotReachedRecentlyVisitedTest() {
		Pet mockPet = mock(Pet.class);
		mockInfantPet(mockPet);
		mockLastVisited(mockPet, 99);
		pets.add(mockPet);
		pets.add(mockPet);
		double result = PriceCalculator.calcPrice(pets, baseCharge, basePricePerPet);
		assertEquals(336,result);
	}

	@Test
	public void matureCounterNotReachedRecentlyVisitedTest() {
		Pet mockPet = mock(Pet.class);
		mockMaturePet(mockPet);
		mockLastVisited(mockPet, 99);
		pets.add(mockPet);
		pets.add(mockPet);
		double result = PriceCalculator.calcPrice(pets, baseCharge, basePricePerPet);
		assertEquals(240,result);
	}

	@Test
	public void matureCounterNotReachedRecentlyVisitedZeroBaseChargeTest() {
		Pet mockPet = mock(Pet.class);
		mockMaturePet(mockPet);
		mockLastVisited(mockPet, 99);
		pets.add(mockPet);
		pets.add(mockPet);
		double result = PriceCalculator.calcPrice(pets, 0, basePricePerPet);
		assertEquals(240,result);
	}

	@Test
	public void matureCounterNotReachedRecentlyVisitedZeroBasePricePerPetTest() {
		Pet mockPet = mock(Pet.class);
		mockMaturePet(mockPet);
		mockLastVisited(mockPet, 99);
		pets.add(mockPet);
		pets.add(mockPet);
		double result = PriceCalculator.calcPrice(pets, baseCharge, 0.0);
		assertEquals(0.0,result);
	}
}
