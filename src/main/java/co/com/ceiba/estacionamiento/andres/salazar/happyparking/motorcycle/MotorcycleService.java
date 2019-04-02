package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import java.util.stream.Stream;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;

public interface MotorcycleService {

	Motorcycle save(Motorcycle motorcycle);
	
	Stream<Motorcycle> findAllMotorcyclesParking();

}
