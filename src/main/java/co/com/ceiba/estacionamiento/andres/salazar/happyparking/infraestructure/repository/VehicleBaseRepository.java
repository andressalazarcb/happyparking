package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VehicleBaseRepository <T extends AbstractVehicle> extends CrudRepository<T, String>{
	
	@Query("{'plate': ?0, 'isParking' : true}")
	T findByPlateAndParkingActive(String plate);

}
