package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;

public interface CarRepositoryMongo extends CrudRepository<Car, String> {

	@Query("{'plate': ?0, 'isParking' : ?1}")
	public Car findCarByPlateAndIsParking(String plate, boolean isParking);

	@Query(value = "{'isParking' : ?0}", count = true)
	public Long findCountCarsByIsParking(boolean isParking);
	
	@Query(value = "{'isParking' : true}")
	Stream<Car> findAllCarsByIsParkingTrueAndStream();

	@Query("{'plate': ?0, 'isParking' : true}")
	public Car findByPlateAndParkingActive(String plate);

}
