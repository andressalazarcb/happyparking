package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.car.settlement.CarSettlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@Service
public class CarServiceBusiness implements CarService {

	private CarRepositoryMongo carRepository;

	private HappyParkingTime happyParkingTime;

	private CarSettlement carSettlement;
	

	@Autowired
	public CarServiceBusiness(CarRepositoryMongo carRepository, HappyParkingTime happyParkingTime,
			CarSettlement carSettlement) {
		this.carRepository = carRepository;
		this.happyParkingTime = happyParkingTime;
		this.carSettlement = carSettlement;
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

	@Override
	public Car getOutVehicle(String plate) {
		Car carFound = carRepository.findByPlateAndParkingActive(plate);
		if(carFound == null) {
			throw new HappyParkingException("No esta el vehiculo en el parqueadero");
		}
		ListIterator<ParkingOrder> parkingOrders = carFound.getParkingOrders().listIterator();
		while (parkingOrders.hasNext()) {
			ParkingOrder parkingOrder = (ParkingOrder) parkingOrders.next();
			if(parkingOrder.isActive() && parkingOrder.getEndDate() == null) {
				parkingOrder.setEndDate(System.currentTimeMillis());
				parkingOrder.setPrice(carSettlement.calculate(carFound, parkingOrder));
				if(parkingOrder.getPrice().equals(BigDecimal.ZERO))
					parkingOrder.setActive(false);
				carFound.setParking(false);
			}
		}
		
		return carRepository.save(carFound);
	}

}
