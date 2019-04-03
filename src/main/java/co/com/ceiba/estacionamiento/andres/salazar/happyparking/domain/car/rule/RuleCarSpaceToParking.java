package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Rule;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.CarRepositoryMongo;

@Component
public class RuleCarSpaceToParking implements Rule<Car>{
	
	@Autowired
	private CarRepositoryMongo carRepository;

	public HappyParkingException excecute(Car vehicle) {
		Long countCars = carRepository.findCountCarsByIsParking(true);
		if (countCars >= 20) {
			return new HappyParkingException("no hay espacio para carros");
		}
		return null;
	}

}
