package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;

public interface MotorcycleRepositoryMongo extends CrudRepository<Motorcycle, String> {

	@Query(value = "{'isParking' : ?0}", count = true)
	Long findCountMotorcycleByIsParking(boolean isParking);

	@Query("{'plate': ?0, 'isParking' : ?1}")
	Motorcycle findMotorcycleByPlateAndIsParking(String plate, boolean isParking);
	
	@Query(value = "{'isParking' : true}")
	Stream<Motorcycle> findAllMotorcyclesByIsParkingTrueAndStream();

	@Query("{'plate': ?0, 'isParking' : true}")
	Motorcycle findByPlateAndParkingActive(String plate);

}
