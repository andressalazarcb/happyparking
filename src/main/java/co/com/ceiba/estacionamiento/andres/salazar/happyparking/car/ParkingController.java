package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;

public interface ParkingController {
	
	public Object getIn(Car car);
	public Object findVehicles();
	public Object getOut();
	public Object findVehicle(String vehiclePlateParam);
}
