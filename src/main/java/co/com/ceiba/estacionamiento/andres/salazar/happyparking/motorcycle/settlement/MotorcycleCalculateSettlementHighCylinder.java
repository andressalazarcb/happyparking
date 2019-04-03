package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle.settlement;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@Component
public class MotorcycleCalculateSettlementHighCylinder implements MotorcycleCalculateSettlement {

	@Override
	public BigDecimal getPrice(Motorcycle motorcycle, ParkingOrder parkingOrder) {
		if (motorcycle.getCc() >= 500) {
			return BigDecimal.valueOf(2000d);
		}
		return BigDecimal.ZERO;
	}

}
