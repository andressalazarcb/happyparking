package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;

public interface CarRepository extends CrudRepository<Car, String>{
	
	@Query("{'plate': ?0, 'isParking' : ?1}")
	public Car findCarByPlateAndIsParking(String plate, boolean isParking);
	
	@Query(value =  "{'isParking' : ?0}", count = true)
	public Long findCountCarsByIsParking(boolean isParking);
	

}
