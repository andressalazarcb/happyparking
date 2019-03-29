package co.com.ceiba.estacionamiento.andres.salazar.happyparking.parking;

public interface ControllerParking {
	
	public Object getIn();
	public Object findVehicles();
	public Object getOut();
	public Object findVehicle(String vehiclePlateParam);
}
