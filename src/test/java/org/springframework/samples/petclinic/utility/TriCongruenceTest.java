package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ReportingExtension.class)
class TriCongruenceTest {

	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);

	@Test
	public void sampleTest() {
		Triangle t1 = new Triangle(2, 3, 7);
		Triangle t2 = new Triangle(7, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	// CUTPNFP for line 14 predicate
	// FFF
	@Test
	public void ifPredicateFFFTest() {
		// FFF
		Triangle t1 = new Triangle(5, 3, 7);
		Triangle t2 = new Triangle(7, 5, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}

	// CUTPNFP for line 14 predicate
	// TFF
	@Test
	public void ifPredicateTFFTest() {
		// TFF
		Triangle t1 = new Triangle(5, 4, 7);
		Triangle t2 = new Triangle(7, 5, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	// CUTPNFP for line 14 predicate
	// FFT
	@Test
	public void ifPredicateFFTTest() {
		// FFT
		Triangle t1 = new Triangle(5, 4, 8);
		Triangle t2 = new Triangle(7, 5, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	// CUTPNFP for line 14 predicate
	// FTF
	@Test
	public void ifPredicateFTFTest() {
		// FTF
		Triangle t1 = new Triangle(5, 4, 7);
		Triangle t2 = new Triangle(7, 4, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	// CC for line 15 predicate (false, false)
	@Test
	public void elseIfPredicateCC1Test() {
		Triangle t1 = new Triangle(5, 3, 7);
		Triangle t2 = new Triangle(7, 5, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}

	// CC for line 15 predicate (true, true)
	@Test
	public void elseIfPredicateCC2Test() {
		Triangle t1 = new Triangle(2, 3, -1);
		Triangle t2 = new Triangle(-1, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	// CACC for line 15 predicate
	// Major clause is t1arr[0] < 0
	// (t1arr[0] + t1arr[1] < t1arr[2]) should evaluate to false
	@Test
	public void elseIfPredicateCACCFirstCondMajorTest() {
		// Major clause is false
		Triangle t1 = new Triangle(5, 3, 7);
		Triangle t2 = new Triangle(7, 5, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);

		// Major clause is true
		// This test is infeasible, it cannot happen
	}

	// CACC for line 15 predicate
	// Major clause is (t1arr[0] + t1arr[1] < t1arr[2])
	// (t1arr[0] < 0) should evaluate to false
	@Test
	public void elseIfPredicateCACCSecondCondMajorTest() {
		// Major clause is false
		Triangle t1 = new Triangle(5, 3, 7);
		Triangle t2 = new Triangle(7, 5, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);

		// Major clause is true
		Triangle t3 = new Triangle(2, 3, 7);
		Triangle t4 = new Triangle(7, 2, 3);
		boolean areCongruent2 = TriCongruence.areCongruent(t3, t4);
		log.debug("Triangles identified as '{}'.", areCongruent2 ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent2);
	}

	/**
	 * TODO
	 * explain your answer here
	 * f = ab + cd
	 * ab set = {TTFT, TFFT, FTFT}
	 * cd set = {FTTT, FTTF, FTFT}
	 * CUTPNFP set = {TTFT, TFFT, FTFT, FTTT, FTTF}
	 * (~f) = (~a)(~c) + (~a)(~d) + (~b)(~c) + (~b)(~d)
	 * we have 4 prime implicants in (~f) and 2 in f
	 * so we need exactly 6 tests to cover UTPC
	 * but we have only 5 in the CUTPNFP set
	 * so CUTPNFP does not subsume UTPC
	 */
	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		boolean predicate = false;
		// ab + cd
		predicate = (a && b) || (c && d);
		return predicate;
	}
}
