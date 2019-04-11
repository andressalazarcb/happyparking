package co.com.ceiba.estacionamiento.andres.salazar.happyparking.application;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.CarService;

@Component
public class VehicleParkingCar implements VehicleParking<Car>{
	
	private CarService carService;

	@Autowired
	public VehicleParkingCar(CarService carService) {
		this.carService = carService;
	}

	public Car getInVehicleToParkingLot(Car vehicle) {
		return carService.getInVehicle(vehicle);
	}

	public Car getOutVehicleOfParkingLot(String plate) {
		return carService.getOutVehicle(plate);
	}

	public Stream<Car> findVehiclesThatAreParking() {
		return carService.findAllVehiclesParking();
	}

	public Car findVehicleByPlate(String plate) {
		return carService.findVehicle(plate);
	}

}
