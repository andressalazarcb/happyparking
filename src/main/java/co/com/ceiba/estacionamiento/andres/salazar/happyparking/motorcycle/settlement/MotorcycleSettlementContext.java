package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle.settlement;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@Component
public class MotorcycleSettlementContext implements MotorcycleSettlement {
	
	private List<MotorcycleCalculateSettlement> settlements;

	@Autowired
	public MotorcycleSettlementContext(List<MotorcycleCalculateSettlement> settlements) {
		this.settlements = settlements;
	}
	
	public BigDecimal calculate(Motorcycle motorcycle, ParkingOrder parkingOrder) {
		BigDecimal price = BigDecimal.ZERO;
		for (MotorcycleCalculateSettlement calculateSettlement : settlements) {
			price = calculateSettlement.getPrice(motorcycle, parkingOrder).add(price);
		}
		return price;
	}

}
