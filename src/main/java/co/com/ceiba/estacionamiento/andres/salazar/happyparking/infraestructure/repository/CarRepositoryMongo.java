package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.Query;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;

public interface CarRepositoryMongo extends VehicleBaseRepository<Car> {

	@Query("{'plate': ?0, 'isParking' : ?1, 'type' : 'Carro'}")
	public Car findCarByPlateAndIsParking(String plate, boolean isParking);

	@Query(value = "{'isParking' : ?0, 'type' : 'Carro'}", count = true)
	public Long findCountCarsByIsParking(boolean isParking);
	
	@Query(value = "{'isParking' : true, 'type' : 'Carro'}")
	Stream<Car> findAllCarsByIsParkingTrueAndStream();

	@Query("{'plate': ?0, 'isParking' : true}")
	public Car findByPlateAndParkingActive(String plate);

}
