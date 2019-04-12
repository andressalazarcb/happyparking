package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.settlement;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.CalculateSettlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

@Component
class MotorcycleCalculateSettlementDays implements CalculateSettlement<Motorcycle>{

	@Override
	public BigDecimal getPrice(Motorcycle motorcycle, ParkingOrder parkingOrder) {
		return getPriceToPayDays(parkingOrder.getDays());
	}
	
	private BigDecimal getPriceToPayDays(long days) {
		return BigDecimal.valueOf(4000L).multiply(BigDecimal.valueOf(days));
	}

}
