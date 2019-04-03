package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.settlement;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.CalculateSettlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

@Component
public class MotorcycleCalculateSettlementHighCylinder implements CalculateSettlement<Motorcycle> {

	@Override
	public BigDecimal getPrice(Motorcycle motorcycle, ParkingOrder parkingOrder) {
		if (motorcycle.getCc() >= 500) {
			return BigDecimal.valueOf(2000d);
		}
		return BigDecimal.ZERO;
	}

}
