package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingSystemException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Settlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.VerifyGetIn;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrderFactory;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.CarRepositoryMongo;

@Service
class CarServiceBusiness implements CarService {

	private CarRepositoryMongo carRepository;

	private Settlement<Car> carSettlement;

	private CarFactory carFactory;

	private ParkingOrderFactory parkingOrderFactory;

	private VerifyGetIn<Car> verifyGetIn;

	private String msgException = "messages.vehicle.exception.notexist.value";

	@Autowired
	public CarServiceBusiness(CarRepositoryMongo carRepository, Settlement<Car> carSettlement, CarFactory carFactory,
			ParkingOrderFactory parkingOrderFactory, VerifyGetIn<Car> verifyGetIn) {
		this.carRepository = carRepository;
		this.carSettlement = carSettlement;
		this.carFactory = carFactory;
		this.parkingOrderFactory = parkingOrderFactory;
		this.verifyGetIn = verifyGetIn;
	}

	@Override
	public Car getOutVehicle(String plate) {
		Car carFound = carRepository.findByPlateAndParkingActive(plate);
		if (carFound == null) {
			throw new HappyParkingException(msgException);
		}
		ListIterator<ParkingOrder> parkingOrders = carFound.getParkingOrders().listIterator();
		while (parkingOrders.hasNext()) {
			ParkingOrder parkingOrder = parkingOrders.next();
			if (parkingOrder.isActive() && parkingOrder.getEndDate() == null) {
				parkingOrder.setEndDate(System.currentTimeMillis());
				parkingOrder.setPrice(carSettlement.calculate(carFound, parkingOrder));
				if (parkingOrder.getPrice().equals(BigDecimal.ZERO))
					parkingOrder.setActive(false);
				carFound.setParking(false);
			}
		}

		return carRepository.save(carFound);
	}

	@Override
	public Stream<Car> findAllVehiclesParking() {
		if (carRepository.findCountCarsByIsParking(true) > 0) {
			return carRepository.findAllCarsByIsParkingTrueAndStream();
		}
		return null;
	}

	@Override
	public Car findVehicle(String plate) {
		Optional<Car> optional = carRepository.findById(plate);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Car getInVehicle(Car vehicle) {
		verifyGetIn(vehicle);
		try {
			Car currentCar = null;
			Optional<Car> optional = carRepository.findById(vehicle.getPlate());

			if (optional.isPresent())
				currentCar = optional.get();

			if (currentCar != null) {
				currentCar.setParking(true);
				ParkingOrder parkingOrder = parkingOrderFactory.getObject();
				parkingOrder.createParkingOrderId(vehicle.getPlate());
				currentCar.getParkingOrders().add(parkingOrder);
			} else {
				currentCar = carFactory.getObject();
				currentCar.setPlate(vehicle.getPlate());
				ParkingOrder parkingOrder = parkingOrderFactory.getObject();
				parkingOrder.createParkingOrderId(currentCar.getPlate());
				currentCar.setParkingOrders(Arrays.asList(parkingOrder));
			}
			return carRepository.save(currentCar);
		} catch (Exception e) {
			throw new HappyParkingSystemException(e);
		}
	}

	private void verifyGetIn(Car car) {
		HappyParkingException exception = verifyGetIn.check(car);
		if (exception != null)
			throw exception;
	}

}
