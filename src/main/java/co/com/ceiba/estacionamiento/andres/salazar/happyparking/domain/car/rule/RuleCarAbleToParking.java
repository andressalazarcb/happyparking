package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.system.HappyParkingTime;

@Component
public class RuleCarAbleToParking implements Rule<Car>{
	
	@Autowired
	private HappyParkingTime happyParkingTime;

	public HappyParkingException excecute(Car vehicle) {
		if (vehicle.getPlate().toUpperCase().startsWith("A") && !isAbleDayToPlateStartWithA()) {
			return new HappyParkingException("no puede ingresar porque no esta en un dia habil");
		}
		return null;
	}
	
	private boolean isAbleDayToPlateStartWithA() {
		switch (happyParkingTime.geCurrentDay()) {
		case "Sunday":
			return true;
		case "Monday":
			return true;
		default:
			return false;
		}
	}

}
