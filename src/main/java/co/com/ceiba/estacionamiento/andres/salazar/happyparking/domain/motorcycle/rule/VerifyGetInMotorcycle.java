package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.VerifyGetIn;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;

@Component
public class VerifyGetInMotorcycle implements VerifyGetIn<Motorcycle>{
	
	@Autowired
	private List<Rule<Motorcycle>> rules;

	@Override
	public HappyParkingException check(Motorcycle vehicle) {
		HappyParkingException exception = null;
		for (Rule<Motorcycle> rule : rules) {
			exception = rule.excecute(vehicle);
			if(exception != null)
				break;
		}
		return exception;
	}

}
