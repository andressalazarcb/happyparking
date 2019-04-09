package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository;

import org.springframework.data.repository.CrudRepository;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.AbstractVehicle;

public interface VehicleBaseRepository <T extends AbstractVehicle> extends CrudRepository<T, String>{

}
