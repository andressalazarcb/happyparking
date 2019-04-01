package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

public interface ParkingOrderBuilder {
	
	
	public void buildStartDate(Long startDate);
	public void buildendDate(Long endDate);
	public void buildActive(boolean active);
	public ParkingOrder getParkingOrder();

}
