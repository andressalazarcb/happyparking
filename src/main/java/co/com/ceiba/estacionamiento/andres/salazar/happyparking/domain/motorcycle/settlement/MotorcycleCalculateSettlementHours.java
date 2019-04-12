package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.settlement;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.CalculateSettlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

@Component
class MotorcycleCalculateSettlementHours implements CalculateSettlement<Motorcycle> {

	@Override
	public BigDecimal getPrice(Motorcycle motorcycle, ParkingOrder parkingOrder) {
		return getPriceToPayHours(parkingOrder.getHours());
	}
	
	private BigDecimal getPriceToPayHours(long hours) {
		return BigDecimal.valueOf(500L).multiply(BigDecimal.valueOf(hours));
	}

}
