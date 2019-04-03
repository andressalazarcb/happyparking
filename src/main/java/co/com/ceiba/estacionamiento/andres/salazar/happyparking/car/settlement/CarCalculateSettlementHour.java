package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car.settlement;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@Component
public class CarCalculateSettlementHour implements CarCalculateSettlement{

	@Override
	public BigDecimal getPrice(Car car, ParkingOrder parkingOrder) {
		return getPriceToPayHours(parkingOrder.getHours());
	}
	
	private BigDecimal getPriceToPayHours(long hours) {
		return BigDecimal.valueOf(500L).multiply(BigDecimal.valueOf(hours));
	}

}
