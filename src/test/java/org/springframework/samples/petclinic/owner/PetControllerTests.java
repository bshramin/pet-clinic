package org.springframework.samples.petclinic.owner;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
  value = PetController.class,
  includeFilters = {
    @ComponentScan.Filter(
      value = PetTypeFormatter.class,
      type = FilterType.ASSIGNABLE_TYPE
    ),
    @ComponentScan.Filter(
      value = PetService.class,
      type = FilterType.ASSIGNABLE_TYPE
    ),
    @ComponentScan.Filter(
      value = LoggerConfig.class,
      type = FilterType.ASSIGNABLE_TYPE
    ),
    @ComponentScan.Filter(
      value = PetTimedCache.class,
      type = FilterType.ASSIGNABLE_TYPE
    ),
  }
)
class PetControllerTests {
  private static final int TEST_OWNER_ID = 12;
  private static final int TEST_PET_ID = 100;
  private static final int TEST_PET_TYPE_ID = 87;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PetRepository pets;

  @MockBean
  private OwnerRepository owners;

  @BeforeEach
  void setup() {
    PetType cat = new PetType();
    cat.setId(TEST_PET_TYPE_ID);
    cat.setName("persian");
    Pet pet = new Pet();
    pet.setId(TEST_PET_ID);
    given(this.pets.findPetTypes()).willReturn(Lists.newArrayList(cat));
    given(this.owners.findById(TEST_OWNER_ID)).willReturn(new Owner());
    given(this.pets.findById(TEST_PET_ID)).willReturn(pet);
  }

  @Test
  void testCreatingForm() throws Exception {
    mockMvc
      .perform(get("/owners/{ownerId}/pets/new", TEST_OWNER_ID))
      .andExpect(status().isOk())
      .andExpect(view().name("pets/createOrUpdatePetForm"))
      .andExpect(model().attributeExists("pet"));
  }

  @Test
  void testCreatingFormSuccess() throws Exception {
    mockMvc
      .perform(
        post("/owners/{ownerId}/pets/new", TEST_OWNER_ID)
          .param("name", "Kadoo")
          .param("type", "persian")
          .param("birthDate", "2017-11-22")
      )
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name("redirect:/owners/{ownerId}"));
  }

  @Test
  void testCreatingFormRaisesErrors() throws Exception {
    mockMvc
      .perform(
        post("/owners/{ownerId}/pets/new", TEST_OWNER_ID)
          .param("name", "Halim")
          .param("birthDate", "2020-05-22")
      )
      .andExpect(model().attributeHasNoErrors("owner"))
      .andExpect(model().attributeHasErrors("pet"))
      .andExpect(model().attributeHasFieldErrors("pet", "type"))
      .andExpect(model().attributeHasFieldErrorCode("pet", "type", "required"))
      .andExpect(status().isOk())
      .andExpect(view().name("pets/createOrUpdatePetForm"));
  }

  @Test
  void testUpdatingForm() throws Exception {
    mockMvc
      .perform(
        get("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID)
      )
      .andExpect(status().isOk())
      .andExpect(model().attributeExists("pet"))
      .andExpect(view().name("pets/createOrUpdatePetForm"));
  }

  @Test
  void testUpdatingFormSuccess() throws Exception {
    mockMvc
      .perform(
        post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID)
          .param("name", "Fandogh")
          .param("type", "persian")
          .param("birthDate", "2013-01-11")
      )
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name("redirect:/owners/{ownerId}"));
  }

  @Test
  void testUpdatingFormRaisesErrors() throws Exception {
    mockMvc
      .perform(
        post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID)
          .param("name", "Pesteh")
          .param("birthDate", "2017/03/10")
      )
      .andExpect(model().attributeHasNoErrors("owner"))
      .andExpect(model().attributeHasErrors("pet"))
      .andExpect(status().isOk())
      .andExpect(view().name("pets/createOrUpdatePetForm"));
  }
}
