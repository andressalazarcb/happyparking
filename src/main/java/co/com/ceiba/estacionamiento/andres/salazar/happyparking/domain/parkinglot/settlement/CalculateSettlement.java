package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.settlement;

import java.math.BigDecimal;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

public interface CalculateSettlement<T> {
	
	BigDecimal getPrice(T car, ParkingOrder parkingOrder);

}
