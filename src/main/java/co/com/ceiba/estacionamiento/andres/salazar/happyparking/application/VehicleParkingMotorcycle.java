package co.com.ceiba.estacionamiento.andres.salazar.happyparking.application;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.MotorcycleService;

@Component
public class VehicleParkingMotorcycle implements VehicleParking<Motorcycle>{
	
	private MotorcycleService motorcycleService;
	
	@Autowired
	public VehicleParkingMotorcycle(MotorcycleService motorcycleService) {
		this.motorcycleService = motorcycleService;
	}
	

	public Motorcycle getInVehicleToParkingLot(Motorcycle vehicle) {
		try {
			return motorcycleService.getInVehicle(vehicle);
		} catch (Exception e) {
			throw new HappyParkingException(e);
		}
	}

	public Motorcycle getOutVehicleOfParkingLot(String plate) {
		return motorcycleService.getOutVehicle(plate);
	}

	public Stream<Motorcycle> findVehiclesThatAreParking() {
		return motorcycleService.findAllVehiclesParking();
	}

	public Motorcycle findVehicleByPlate(String plate) {
		return motorcycleService.findVehicle(plate);
	}

}
