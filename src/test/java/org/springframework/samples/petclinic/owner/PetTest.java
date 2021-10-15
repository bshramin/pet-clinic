package org.springframework.samples.petclinic.owner;

import org.junit.Assume;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(Theories.class)
public class PetTest {

	@DataPoints
	public static Visit[][] visits() {
		return new Visit[][]{
			{},
			{new Visit(), new Visit()},
			{new Visit(), new Visit(), new Visit() },
		};
	}

	@Theory
	public void testGetVisitsEmpty(Visit[] visitList) {
		Assume.assumeTrue(visitList.length == 0);
		Pet pet = new Pet();
		assertEquals( 0, pet.getVisits().size());
	}

	@Theory
	public void testGetVisitsNotEmpty(Visit[] visitList) {
		Assume.assumeTrue(visitList.length != 0);
		Pet pet = new Pet();
		for (Visit v : visitList) {
			pet.addVisit(v);
		}
		assertEquals( visitList.length, pet.getVisits().size());
	}
}
