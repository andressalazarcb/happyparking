package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.MotorcycleRepositoryMongo;

@Component
public class RuleMotorcycleThereWasSameVehicle implements Rule<Motorcycle>{
	
	@Autowired
	private MotorcycleRepositoryMongo motorcycleRepository;

	public HappyParkingException excecute(Motorcycle vehicle) {
		Motorcycle motorcycleFound = motorcycleRepository.findMotorcycleByPlateAndIsParking(vehicle.getPlate(), true);
		if (motorcycleFound != null) {
			return new HappyParkingException("hay una moto parqueada");
		}
		return null;
	}

}
