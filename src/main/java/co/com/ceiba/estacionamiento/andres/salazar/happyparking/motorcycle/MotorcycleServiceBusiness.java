package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;

@Service
public class MotorcycleServiceBusiness implements MotorcycleService {
	
	private MotorcycleRepository motorcycleRepository;

	@Autowired
	public MotorcycleServiceBusiness(MotorcycleRepository motorcycleRepository) {
		this.motorcycleRepository = motorcycleRepository;
	}

	public Motorcycle save(Motorcycle motorcycle) {
		checkIfThereWasSameVehicleInParkingLot(motorcycle.getPlate());
		checkIfThereAreSpaceToParking();
		motorcycle.setParking(true);
		return motorcycleRepository.save(motorcycle);
	}
	
	private void checkIfThereWasSameVehicleInParkingLot(String plate) {
		Motorcycle motorcycleFound = motorcycleRepository.findMotorcycleByPlateAndIsParking(plate, true);
		if (motorcycleFound != null) {
			throw new HappyParkingException("hay una moto parqueada");
		}
	}
	
	private void checkIfThereAreSpaceToParking() {
		Long countMotorcycles = motorcycleRepository.findCountMotorcycleByIsParking(true);
		if (countMotorcycles >= 10) {
			throw new HappyParkingException("no hay espacio para motos");
		}
	}

}
