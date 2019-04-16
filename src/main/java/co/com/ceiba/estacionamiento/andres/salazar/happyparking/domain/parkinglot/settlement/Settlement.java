package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.settlement;

import java.math.BigDecimal;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

public interface Settlement<T> {

	BigDecimal calculate(T carFound, ParkingOrder parkingOrder);

}
