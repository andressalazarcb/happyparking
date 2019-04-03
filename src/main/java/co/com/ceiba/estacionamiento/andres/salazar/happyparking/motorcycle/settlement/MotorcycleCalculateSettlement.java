package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle.settlement;

import java.math.BigDecimal;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

public interface MotorcycleCalculateSettlement {
	
	BigDecimal getPrice(Motorcycle motorcycle, ParkingOrder parkingOrder);

}
