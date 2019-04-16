package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot;

public interface ParkingLotService<T extends AbstractVehicle> {
	
	public T getInVehicle(ParkingLot<T> parkingLot);
	
	public T getOutVehicle(ParkingLot<T> parkingLot);

}
