package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.rule.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.system.HappyParkingTime;

@Component
class RuleCarAbleToParking implements Rule<Car> {

	private HappyParkingTime happyParkingTime;

	private String msdException = "messages.car.exception.ableday.value";

	@Autowired
	public RuleCarAbleToParking(HappyParkingTime happyParkingTime) {
		this.happyParkingTime = happyParkingTime;
	}

	@Override
	public HappyParkingException excecute(ParkingLot<Car> parkingLot) {
		if (parkingLot.getVehicleIn().isPlateStartWith("A") && !isAbleDayToPlateStartWithA()) {
			return new HappyParkingException(msdException);
		}
		return null;
	}
	
	private boolean isAbleDayToPlateStartWithA() {
		switch (happyParkingTime.geCurrentDay()) {
		case "Sunday":
		case "Monday":
			return true;
		default:
			return false;
		}
	}

}
