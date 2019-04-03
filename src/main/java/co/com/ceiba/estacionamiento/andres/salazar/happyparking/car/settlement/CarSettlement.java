package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car.settlement;

import java.math.BigDecimal;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

public interface CarSettlement {

	BigDecimal calculate(Car carFound, ParkingOrder parkingOrder);

}
