package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.VerifyGetIn;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;

@Component
public class VerifyGetInCar implements VerifyGetIn<Car>{
	
	@Autowired
	private List<Rule<Car>> rules;

	@Override
	public HappyParkingException check(Car vehicle) {
		HappyParkingException exception = null;
		for (Rule<Car> rule : rules) {
			exception = rule.excecute(vehicle);
			if(exception != null)
				break;
		}
		return exception;
	}

}
