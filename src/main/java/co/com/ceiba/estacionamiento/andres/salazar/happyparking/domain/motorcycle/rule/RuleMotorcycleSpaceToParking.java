package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.rule;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.rule.Rule;

@Component
class RuleMotorcycleSpaceToParking implements Rule<Motorcycle> {
	
	private String msgException = "messages.motorcycle.exception.nospace.value";

	@Override
	public HappyParkingException excecute(ParkingLot<Motorcycle> parkingLot) {
		if (parkingLot.getQuantity() >= 10) {
			return new HappyParkingException(msgException);
		}
		return null;
	}

}
