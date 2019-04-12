package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.Query;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;

public interface MotorcycleRepositoryMongo extends VehicleBaseRepository<Motorcycle> {

	@Query(value = "{'isParking' : ?0}", count = true)
	Long findCountMotorcycleByIsParking(boolean isParking);

	@Query("{'plate': ?0, 'isParking' : ?1}")
	Motorcycle findMotorcycleByPlateAndIsParking(String plate, boolean isParking);
	
	@Query(value = "{'isParking' : true}")
	Stream<Motorcycle> findAllMotorcyclesByIsParkingTrueAndStream();

}
