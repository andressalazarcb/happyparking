package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.rule.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.rule.VerifyGetIn;

@Component
class VerifyGetInCar implements VerifyGetIn<Car> {

	private List<Rule<Car>> rules;

	@Autowired
	public VerifyGetInCar(List<Rule<Car>> rules) {
		this.rules = rules;
	}

	@Override
	public HappyParkingException check(ParkingLot<Car> parkingLot) {
		HappyParkingException exception = null;
		for (Rule<Car> rule : rules) {
			exception = rule.excecute(parkingLot);
			if (exception != null)
				break;
		}
		return exception;
	}

}
