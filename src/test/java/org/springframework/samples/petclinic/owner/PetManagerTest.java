package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;



class PetManagerTest {
	OwnerRepository ownerRepository;
	PetTimedCache petTimedCache;
	Logger logger;
	PetManager petManager;

	@BeforeEach
	public void setUpEach() {
		// Using mock
		// All test are written with Mockist view
		ownerRepository = mock(OwnerRepository.class);
		petTimedCache = mock(PetTimedCache.class);
		logger = LoggerFactory.getLogger("CRITICAL_LOGGER");

		petManager = new PetManager(petTimedCache, ownerRepository, logger);
	}

	@Test
	public void testFindOwner() {
		// Setup
		Owner alex = new Owner();
		alex.setId(23);
		alex.setFirstName("Alex");
		when(ownerRepository.findById(alex.getId())).thenReturn(alex);

		// Exercise
		Owner owner = petManager.findOwner(alex.getId());

		// Verify
		// Behavior
		verify(ownerRepository).findById(alex.getId());
		// State
		assertNotNull(owner);
		assertEquals(owner.getFirstName(), alex.getFirstName());
	}

	@Test
	public void testNewPet() {
		// Setup
		Owner alex = mock(Owner.class);

		// Exercise
		Pet pet = petManager.newPet(alex);

		// Verify
		// Behavior
		verify(alex).addPet(pet);
		// State
		assertNotNull(pet);
	}

	@Test
	public void testFindPet() {
		// Setup
		Pet bob = new Pet();
		bob.setId(23);
		when(petTimedCache.get(bob.getId())).thenReturn(bob);

		// Exercise
		Pet pet = petManager.findPet(bob.getId());

		// Verify
		// Behavior
		verify(petTimedCache).get(bob.getId());
		// State
		assertEquals(pet.getName(), bob.getName());
	}

	@Test
	public void testSavePet() {
		// Setup
		Owner alex = mock(Owner.class);
		Pet bob = new Pet();
		bob.setId(23);

		// Exercise
		petManager.savePet(bob, alex);

		// Verify
		// Behavior
		verify(petTimedCache).save(bob);
		verify(alex).addPet(bob);
	}

	@Test
	public void testGetOwnerPets() {
		// Setup
		Owner alex = mock(Owner.class);
		Pet bob = new Pet();
		bob.setId(23);
		Pet betty = new Pet();
		betty.setId(25);
		List<Pet> alexPets = new ArrayList<Pet>();
		alexPets.add(bob);
		alexPets.add(betty);

		when(alex.getPets()).thenReturn(alexPets);
		when(ownerRepository.findById(alex.getId())).thenReturn(alex);

		// Exercise
		List<Pet> pets = petManager.getOwnerPets(alex.getId());

		// Verify
		// Behavior
		verify(ownerRepository).findById(alex.getId());
		verify(alex).getPets();
		// State
		assertEquals(pets, alexPets);
	}

	@Test
	public void testGetOwnerPetTypes() {
		// Setup
		Owner alex = mock(Owner.class);
		PetType dogType = new PetType();
		dogType.setName("dog");
		PetType catType = new PetType();
		catType.setName("cat");
		Pet bob = new Pet();
		bob.setId(23);
		bob.setType(dogType);
		Pet betty = new Pet();
		betty.setId(25);
		betty.setType(catType);
		List<Pet> alexPets = new ArrayList<Pet>();
		alexPets.add(bob);
		alexPets.add(betty);

		when(alex.getPets()).thenReturn(alexPets);
		when(ownerRepository.findById(alex.getId())).thenReturn(alex);

		// Exercise
		Set<PetType> petTypes = petManager.getOwnerPetTypes(alex.getId());

		// Verify
		// Behavior
		verify(ownerRepository).findById(alex.getId());
		verify(alex).getPets();
		// State
		assertTrue(petTypes.contains(catType));
		assertTrue(petTypes.contains(dogType));
		assertFalse(petTypes.contains(new PetType()));
	}

	@Test
	public void testGetVisitsBetween() {
		// Setup
		Owner alex = mock(Owner.class);
		Visit visit1 = new Visit();
		visit1.setId(1);
		visit1.setDate(LocalDate.ofYearDay(2021,250));
		Visit visit2 = new Visit();
		visit2.setId(2);
		visit2.setDate(LocalDate.ofYearDay(2021,150));
		Pet bob = new Pet();
		bob.setId(23);
		bob.addVisit(visit1);
		bob.addVisit(visit2);

		when(petTimedCache.get(bob.getId())).thenReturn(bob);

		// Exercise
		List<Visit> visits = petManager.getVisitsBetween(bob.getId(), LocalDate.ofYearDay(2021,200), LocalDate.ofYearDay(2021, 300));

		// Verify
		// Behavior
		verify(petTimedCache).get(bob.getId());
		// State
		assertTrue(visits.contains(visit1));
		assertFalse(visits.contains(visit2));
	}
}
