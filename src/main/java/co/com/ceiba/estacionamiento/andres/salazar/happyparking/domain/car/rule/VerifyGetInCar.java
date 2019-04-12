package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.VerifyGetIn;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;

@Component
class VerifyGetInCar implements VerifyGetIn<Car> {

	private List<Rule<Car>> rules;

	@Autowired
	public VerifyGetInCar(List<Rule<Car>> rules) {
		this.rules = rules;
	}

	@Override
	public HappyParkingException check(Car vehicle) {
		HappyParkingException exception = null;
		for (Rule<Car> rule : rules) {
			exception = rule.excecute(vehicle);
			if (exception != null)
				break;
		}
		return exception;
	}

}
