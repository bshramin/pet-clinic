package bdd;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

public class PetServiceSteps {
  @Autowired
  PetService petService;

  @Autowired
  PetTypeRepository petTypeRepository;

  @Autowired
  OwnerRepository ownerRepository;

  private PetType petType;
  private final HashMap<String, Owner> allOwners = new HashMap<>();
  private final HashMap<String, Pet> allPets = new HashMap<>();

  private Owner foundedOwner;
  private Pet foundedPet;

  @Given("There is an owner with first name {string}")
  public void createOwner(String ownerName) {
    Owner owner = new Owner();
    owner.setFirstName(ownerName);
    owner.setLastName("Bashiri");
    owner.setAddress("Uni of Tehran");
    owner.setCity("Tehran");
    owner.setTelephone("09123456789");
    ownerRepository.save(owner);
    allOwners.put(ownerName, owner);
  }

  @Given("There is pet type {string}")
  public void createPetType(String petTypeName) {
    petType = new PetType();
    petType.setName(petTypeName);
    petTypeRepository.save(petType);
  }

  @Given("There is a pet called {string} with owner {string}")
  public void createPet(String petName, String ownerName) {
    Owner owner = allOwners.get(ownerName);
    Pet pet = petService.newPet(owner);
    pet.setType(petType);
    pet.setName(petName);
    petService.savePet(pet, owner);
    allPets.put(petName, pet);
  }

  @When("Using find pet service to find {string}")
  public void findPet(String petName) {
    foundedPet = petService.findPet(allPets.get(petName).getId());
  }

  @When("Using find owner service to find {string}")
  public void findOwner(String ownerName) {
    foundedOwner = petService.findOwner(allOwners.get(ownerName).getId());
  }

  @Then("Founded pet name is {string}")
  public void FoundedPetIs(String expectedPetName) {
    assertEquals(expectedPetName, foundedPet.getName());
  }

  @Then("Founded pet name is not {string}")
  public void FoundedPetIsNot(String expectedPetName) {
    assertNotEquals(expectedPetName, foundedPet.getName());
  }

  @Then("Founded owner first name is {string}")
  public void FoundedOwnerIs(String expectedOwnerName) {
    assertEquals(expectedOwnerName, foundedOwner.getFirstName());
  }

  @Then("Founded owner first name is not {string}")
  public void FoundedOwnerIsNot(String expectedOwnerName) {
    assertNotEquals(expectedOwnerName, foundedOwner.getFirstName());
  }
}
