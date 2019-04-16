package co.com.ceiba.estacionamiento.andres.salazar.happyparking.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.application.api.VehicleParking;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLotService;

@Component
class VehicleParkingCar implements VehicleParking<Car>{
	
	private ParkingLotService<Car> parkingLotService;
	
	@Autowired
	public VehicleParkingCar(ParkingLotService<Car> parkingLotService) {
		this.parkingLotService = parkingLotService;
	}

	@Override
	public Car getInVehicle(ParkingLot<Car> parkingLot) {
		return parkingLotService.getInVehicle(parkingLot);
	}

	@Override
	public Car getOutVehicle(ParkingLot<Car> parkingLot) {
		return parkingLotService.getOutVehicle(parkingLot);
	}

}
