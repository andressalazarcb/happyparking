package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import java.util.stream.Stream;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;

public interface CarService {

	public Car save(Car car);
	
	public Stream<Car> findAllCarsParking();

	public Car getOutVehicle(String plate);

}
