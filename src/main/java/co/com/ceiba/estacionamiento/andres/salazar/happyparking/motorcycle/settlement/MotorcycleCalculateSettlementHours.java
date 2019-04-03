package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle.settlement;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@Component
public class MotorcycleCalculateSettlementHours implements MotorcycleCalculateSettlement {

	@Override
	public BigDecimal getPrice(Motorcycle motorcycle, ParkingOrder parkingOrder) {
		return getPriceToPayHours(parkingOrder.getHours());
	}
	
	private BigDecimal getPriceToPayHours(long hours) {
		return BigDecimal.valueOf(500L).multiply(BigDecimal.valueOf(hours));
	}

}
