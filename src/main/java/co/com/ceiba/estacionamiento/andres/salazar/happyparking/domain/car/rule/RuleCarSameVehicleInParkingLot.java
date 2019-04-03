package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.CarRepositoryMongo;

@Component
public class RuleCarSameVehicleInParkingLot implements Rule<Car>{
	
	@Autowired
	private CarRepositoryMongo carRepository;
	
	public HappyParkingException excecute(Car vehicle) {
		Car carFound = carRepository.findCarByPlateAndIsParking(vehicle.getPlate(), true);
		if (carFound != null) {
			return new HappyParkingException("hay un carro parqueado");
		}
		return null;
	}
	
}
