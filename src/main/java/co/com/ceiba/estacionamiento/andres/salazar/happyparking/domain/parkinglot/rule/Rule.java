package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.rule;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.AbstractVehicle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;

public interface Rule<T extends AbstractVehicle> {

	HappyParkingException excecute(ParkingLot<T> parkingLot);

}
