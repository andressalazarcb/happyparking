package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;

@Service
public class MotorcycleServicebusiness implements MotorcycleService {
	
	private MotorcycleRepository motorcycleRepository;

	@Autowired
	public MotorcycleServicebusiness(MotorcycleRepository motorcycleRepository) {
		this.motorcycleRepository = motorcycleRepository;
	}

	public Motorcycle save(Motorcycle motorcycle) {
		motorcycle.setParking(true);
		Motorcycle motorcycleSaved = motorcycleRepository.save(motorcycle);
		return motorcycleSaved;
	}

}
