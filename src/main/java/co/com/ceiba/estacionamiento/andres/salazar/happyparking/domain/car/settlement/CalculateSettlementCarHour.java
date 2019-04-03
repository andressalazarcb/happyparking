package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.settlement;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.CalculateSettlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

@Component
public class CalculateSettlementCarHour implements CalculateSettlement<Car>{

	@Override
	public BigDecimal getPrice(Car car, ParkingOrder parkingOrder) {
		return getPriceToPayHours(parkingOrder.getHours());
	}
	
	private BigDecimal getPriceToPayHours(long hours) {
		return BigDecimal.valueOf(500L).multiply(BigDecimal.valueOf(hours));
	}

}
