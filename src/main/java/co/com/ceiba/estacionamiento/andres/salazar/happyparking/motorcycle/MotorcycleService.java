package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import java.util.stream.Stream;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;

public interface MotorcycleService {

	Motorcycle save(Motorcycle motorcycle);
	
	Stream<Motorcycle> findAllMotorcyclesParking();

	Motorcycle getOutVehicle(String plate);

	public long calculateDays(long totalHours);

	public long calculateHours(long totalHours);

}
