package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@Service
public class MotorcycleServiceBusiness implements MotorcycleService {
	
	private MotorcycleRepositoryMongo motorcycleRepository;

	@Autowired
	public MotorcycleServiceBusiness(MotorcycleRepositoryMongo motorcycleRepository) {
		this.motorcycleRepository = motorcycleRepository;
	}

	public Motorcycle save(Motorcycle motorcycle) {
		checkIfThereWasSameVehicleInParkingLot(motorcycle.getPlate());
		checkIfThereAreSpaceToParking();
		Motorcycle motorcycleToSave = motorcycle.copy();
		motorcycleToSave.setParking(true);
		motorcycleToSave.setType("Moto");
		ParkingOrder parkingOrder = new ParkingOrder();
		parkingOrder.setActive(true);
		parkingOrder.setStartDate(System.currentTimeMillis());
		motorcycleToSave.setParkingOrders(Arrays.asList(parkingOrder));
		return motorcycleRepository.save(motorcycleToSave);
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

	@Override
	public Stream<Motorcycle> findAllMotorcyclesParking() {
		return motorcycleRepository.findAllMotorcyclesByIsParkingTrueAndStream();
	}

}
