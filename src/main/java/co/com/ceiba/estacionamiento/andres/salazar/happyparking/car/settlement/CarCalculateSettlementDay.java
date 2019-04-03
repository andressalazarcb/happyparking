package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car.settlement;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@Component
public class CarCalculateSettlementDay implements CarCalculateSettlement{

	@Override
	public BigDecimal getPrice(Car car, ParkingOrder parkingOrder) {
		return getPriceToPayDays(parkingOrder.getDays());
	}
	
	private BigDecimal getPriceToPayDays(long days) {
		return BigDecimal.valueOf(8000L).multiply(BigDecimal.valueOf(days));
	}

}
