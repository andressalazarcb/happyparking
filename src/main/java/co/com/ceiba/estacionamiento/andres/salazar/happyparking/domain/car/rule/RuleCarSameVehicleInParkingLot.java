package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.rule;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.rule.Rule;

@Component
class RuleCarSameVehicleInParkingLot implements Rule<Car>{
	
	private String msgException = "messages.vehicle.exception.exist.value";
	
	@Override
	public HappyParkingException excecute(ParkingLot<Car> parkingLot) {
		if(parkingLot.getVehicleIn() != null && parkingLot.getVehicleIn().isParking()) {
			return new HappyParkingException(msgException);
		}
		return null;
	}
	
}
