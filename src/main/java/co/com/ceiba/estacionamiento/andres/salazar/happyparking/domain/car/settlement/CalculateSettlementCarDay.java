package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.settlement;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.settlement.CalculateSettlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

@Component
class CalculateSettlementCarDay implements CalculateSettlement<Car>{

	@Override
	public BigDecimal getPrice(Car car, ParkingOrder parkingOrder) {
		return getPriceToPayDays(parkingOrder.getDays());
	}
	
	private BigDecimal getPriceToPayDays(long days) {
		return BigDecimal.valueOf(8000L).multiply(BigDecimal.valueOf(days));
	}

}
