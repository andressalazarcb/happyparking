package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.MotorcycleRepositoryMongo;

@Component
public class RuleMotorcycleSpaceToParking implements Rule<Motorcycle>{
	
	@Autowired
	private MotorcycleRepositoryMongo motorcycleRepository;

	public HappyParkingException excecute(Motorcycle vehicle) {
		Long countMotorcycles = motorcycleRepository.findCountMotorcycleByIsParking(true);
		if (countMotorcycles >= 10) {
			return new HappyParkingException("no hay espacio para motos");
		}
		return null;
	}

}
