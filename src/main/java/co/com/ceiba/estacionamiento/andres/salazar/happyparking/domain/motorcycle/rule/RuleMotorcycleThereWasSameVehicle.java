package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.MotorcycleRepositoryMongo;

@Component
class RuleMotorcycleThereWasSameVehicle implements Rule<Motorcycle> {

	private MotorcycleRepositoryMongo motorcycleRepository;
	
	private String msgException = "messages.vehicle.exception.exist.value";

	@Autowired
	public RuleMotorcycleThereWasSameVehicle(MotorcycleRepositoryMongo motorcycleRepository) {
		this.motorcycleRepository = motorcycleRepository;
	}

	public HappyParkingException excecute(Motorcycle vehicle) {
		Motorcycle motorcycleFound = motorcycleRepository.findMotorcycleByPlateAndIsParking(vehicle.getPlate(), true);
		if (motorcycleFound != null) {
			return new HappyParkingException(msgException);
		}
		return null;
	}

}
