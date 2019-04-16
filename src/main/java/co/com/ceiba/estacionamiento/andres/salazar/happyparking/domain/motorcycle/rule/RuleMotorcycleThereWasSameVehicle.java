package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.rule;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.rule.Rule;

@Component
class RuleMotorcycleThereWasSameVehicle implements Rule<Motorcycle> {

	
	private String msgException = "messages.vehicle.exception.exist.value";

	@Override
	public HappyParkingException excecute(ParkingLot<Motorcycle> parkingLot) {
		Motorcycle motorcycleFound = parkingLot.getVehicleIn();
		if (motorcycleFound != null && motorcycleFound.isParking()) {
			return new HappyParkingException(msgException);
		}
		return null;
	}

}
