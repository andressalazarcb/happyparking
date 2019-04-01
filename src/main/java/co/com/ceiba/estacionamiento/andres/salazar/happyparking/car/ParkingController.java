package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

public interface ParkingController {
	
	public Object getIn();
	public Object findVehicles();
	public Object getOut();
	public Object findVehicle(String vehiclePlateParam);
}
