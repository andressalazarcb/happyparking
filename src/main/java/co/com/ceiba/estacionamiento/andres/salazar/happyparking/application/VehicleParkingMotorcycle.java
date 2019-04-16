package co.com.ceiba.estacionamiento.andres.salazar.happyparking.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.application.api.VehicleParking;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLotService;

@Component
class VehicleParkingMotorcycle implements VehicleParking<Motorcycle> {
	
	private ParkingLotService<Motorcycle> parkingLotService;
	
	@Autowired
	public VehicleParkingMotorcycle(ParkingLotService<Motorcycle> parkingLotService) {
		this.parkingLotService = parkingLotService;
	}

	@Override
	public Motorcycle getInVehicle(ParkingLot<Motorcycle> parkingLot) {
		return parkingLotService.getInVehicle(parkingLot);
	}

	@Override
	public Motorcycle getOutVehicle(ParkingLot<Motorcycle> parkingLot) {
		return parkingLotService.getOutVehicle(parkingLot);
	}

}
