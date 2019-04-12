package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.CarRepositoryMongo;

@Component
class RuleCarSpaceToParking implements Rule<Car> {

	private CarRepositoryMongo carRepository;
	
	private String msgException = "messages.car.exception.nospace.value";

	@Autowired
	public RuleCarSpaceToParking(CarRepositoryMongo carRepository) {
		this.carRepository = carRepository;
	}

	public HappyParkingException excecute(Car vehicle) {
		Long countCars = carRepository.findCountCarsByIsParking(true);
		if (countCars >= 20) {
			return new HappyParkingException(msgException);
		}
		return null;
	}

}
