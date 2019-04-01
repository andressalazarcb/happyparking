package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.HappyParkingTime;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;

@Service
public class CarServiceBusiness implements CarService {

	private CarRepository carRepository;

	private HappyParkingTime happyParkingTime;

	@Autowired
	public CarServiceBusiness(CarRepository carRepository, HappyParkingTime happyParkingTime) {
		this.carRepository = carRepository;
		this.happyParkingTime = happyParkingTime;
	}

	public CarServiceBusiness() {
	}

	@Override
	public Car save(Car car) {
		checkIfThereWasSameVehicleInParkingLot(car.getPlate());
		checkIfThereAreSpaceToParking();
		checkIfAbleToParkingDay(car.getPlate());
		Car carToSave = car.copy();
		carToSave.setParking(true);
		return carRepository.save(carToSave);
	}

	private void checkIfThereWasSameVehicleInParkingLot(String plate) {
		Car carFound = carRepository.findCarByPlateAndIsParking(plate, true);
		if (carFound != null) {
			throw new HappyParkingException("hay un carro parqueado");
		}
	}

	private void checkIfThereAreSpaceToParking() {
		Long countCars = carRepository.findCountCarsByIsParking(true);
		if (countCars >= 20) {
			throw new HappyParkingException("no hay espacio para carros");
		}
	}

	private void checkIfAbleToParkingDay(String plate) {
		if (plate.toUpperCase().startsWith("A") && !isAbleDayToPlateStartWithA()) {
			throw new HappyParkingException("no puede ingresar porque no esta en un dia habil");
		}
	}
	
	private boolean isAbleDayToPlateStartWithA() {
		switch (happyParkingTime.getDay()) {
		case "Sunday":
			return true;
		case "Monday":
			return true;
		default:
			return false;
		}
	}

}
