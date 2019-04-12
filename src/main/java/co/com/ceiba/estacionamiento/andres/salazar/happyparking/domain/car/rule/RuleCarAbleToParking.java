package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.system.HappyParkingTime;

@Component
class RuleCarAbleToParking implements Rule<Car> {

	private HappyParkingTime happyParkingTime;

	private String msdException = "messages.car.exception.ableday.value";

	@Autowired
	public RuleCarAbleToParking(HappyParkingTime happyParkingTime) {
		this.happyParkingTime = happyParkingTime;
	}

	public HappyParkingException excecute(Car vehicle) {
		if (vehicle.getPlate().toUpperCase().startsWith("A") && !isAbleDayToPlateStartWithA()) {
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
