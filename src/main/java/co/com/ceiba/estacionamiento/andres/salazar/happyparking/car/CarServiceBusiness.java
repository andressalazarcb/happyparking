package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@Service
public class CarServiceBusiness implements CarService {

	private CarRepositoryMongo carRepository;

	private HappyParkingTime happyParkingTime;

	@Autowired
	public CarServiceBusiness(CarRepositoryMongo carRepository, HappyParkingTime happyParkingTime) {
		this.carRepository = carRepository;
		this.happyParkingTime = happyParkingTime;
	}

	@Override
	public Car save(Car car) {
		checkIfThereWasSameVehicleInParkingLot(car.getPlate());
		checkIfThereAreSpaceToParking();
		checkIfAbleToParkingDay(car.getPlate());
		Car carToSave = car.copy();
		carToSave.setParking(true);
		carToSave.setType("Carro");
		ParkingOrder parkingOrder = new ParkingOrder();
		parkingOrder.setParkingOrderId(carToSave.getPlate()+"_"+System.currentTimeMillis());
		parkingOrder.setActive(true);
		parkingOrder.setStartDate(System.currentTimeMillis());
		carToSave.setParkingOrders(Arrays.asList(parkingOrder));
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
		switch (happyParkingTime.geCurrentDay()) {
		case "Sunday":
			return true;
		case "Monday":
			return true;
		default:
			return false;
		}
	}

	@Override
	public Stream<Car> findAllCarsParking() {
		return carRepository.findAllCarsByIsParkingTrueAndStream();
	}

}
