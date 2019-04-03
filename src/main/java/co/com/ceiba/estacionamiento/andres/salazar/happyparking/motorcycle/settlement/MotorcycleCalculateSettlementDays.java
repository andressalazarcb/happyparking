package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle.settlement;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@Component
public class MotorcycleCalculateSettlementDays implements MotorcycleCalculateSettlement{

	@Override
	public BigDecimal getPrice(Motorcycle motorcycle, ParkingOrder parkingOrder) {
		return getPriceToPayDays(parkingOrder.getDays());
	}
	
	private BigDecimal getPriceToPayDays(long days) {
		return BigDecimal.valueOf(4000L).multiply(BigDecimal.valueOf(days));
	}

}
