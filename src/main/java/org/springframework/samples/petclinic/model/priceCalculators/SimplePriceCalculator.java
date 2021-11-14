package org.springframework.samples.petclinic.model.priceCalculators;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.UserType;

import java.util.List;

public class SimplePriceCalculator implements PriceCalculator {

    private static final double BASE_RARE_COEF = 1.2;

    @Override
    public double calcPrice(List<Pet> pets, double baseCharge, double basePricePerPet, UserType userType) {
        double totalPrice = baseCharge; // A

        for (int i=0; i<pets.size(); i++) { // B
            double price = 0; // C

            if (pets.get(i).getType().getRare()) { // E
                price = basePricePerPet * BASE_RARE_COEF; // G
            } else {
                price = basePricePerPet; // F
            }
            totalPrice += price; // H
        }

        if (userType == UserType.NEW) { // D
                totalPrice = totalPrice * userType.discountRate; // K
        }

        return totalPrice; // M
    }
}
