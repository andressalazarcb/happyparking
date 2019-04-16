package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.Query;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;

public interface CarRepositoryMongo extends VehicleBaseRepository<Car> {

	@Query("{'plate': ?0, 'isParking' : ?1, '_class':'car'}")
	Car findCarByPlateAndIsParking(String plate, boolean isParking);

	@Query(value = "{'isParking' : ?0, '_class':'car'}", count = true)
	Long findCountCarsByIsParking(boolean isParking);

	@Query(value = "{'isParking' : true, '_class':'car'}")
	Stream<Car> findAllCarsByIsParkingTrueAndStream();

}
