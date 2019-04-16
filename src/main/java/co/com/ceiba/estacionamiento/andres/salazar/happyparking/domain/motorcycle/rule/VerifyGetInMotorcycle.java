package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.rule.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.rule.VerifyGetIn;

@Component
class VerifyGetInMotorcycle implements VerifyGetIn<Motorcycle> {

	private List<Rule<Motorcycle>> rules;

	@Autowired
	public VerifyGetInMotorcycle(List<Rule<Motorcycle>> rules) {
		this.rules = rules;
	}

	@Override
	public HappyParkingException check(ParkingLot<Motorcycle> parkingLot) {
		HappyParkingException exception = null;
		for (Rule<Motorcycle> rule : rules) {
			exception = rule.excecute(parkingLot);
			if (exception != null)
				break;
		}
		return exception;
	}

}
