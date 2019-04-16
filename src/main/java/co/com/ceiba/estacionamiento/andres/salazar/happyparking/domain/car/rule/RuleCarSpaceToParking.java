package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.rule;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.rule.Rule;

@Component
class RuleCarSpaceToParking implements Rule<Car> {

	private String msgException = "messages.car.exception.nospace.value";

	@Override
	public HappyParkingException excecute(ParkingLot<Car> parkingLot) {
		long countCars = parkingLot.getQuantity();
		if (countCars >= 20) {
			return new HappyParkingException(msgException);
		}
		return null;
	}

}
