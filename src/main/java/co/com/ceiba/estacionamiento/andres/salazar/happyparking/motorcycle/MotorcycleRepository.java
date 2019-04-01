package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;

public interface MotorcycleRepository extends CrudRepository<Motorcycle, String> {

	@Query(value = "{'isParking' : ?0}", count = true)
	Long findCountMotorcycleByIsParking(boolean isParking);

	@Query("{'plate': ?0, 'isParking' : ?1}")
	Motorcycle findMotorcycleByPlateAndIsParking(String plate, boolean isParking);

}