package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.Query;

public interface MotorcycleRepositoryMongo extends VehicleBaseRepository<MotorcycleEntity> {

	@Query(value = "{'isParking' : ?0, '_class':'motorcycle'}", count = true)
	Long findCountMotorcycleByIsParking(boolean isParking);

	@Query("{'plate': ?0, 'isParking' : ?1, '_class':'motorcycle'}")
	MotorcycleEntity findMotorcycleByPlateAndIsParking(String plate, boolean isParking);
	
	@Query(value = "{'isParking' : true, '_class':'motorcycle'}")
	Stream<MotorcycleEntity> findAllMotorcyclesByIsParkingTrueAndStream();

}
