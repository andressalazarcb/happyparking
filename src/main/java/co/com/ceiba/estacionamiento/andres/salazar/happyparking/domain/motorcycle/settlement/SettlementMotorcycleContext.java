package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.settlement;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.settlement.CalculateSettlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.settlement.Settlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

@Component
class SettlementMotorcycleContext implements Settlement<Motorcycle> {
	
	private List<CalculateSettlement<Motorcycle>> settlements;

	@Autowired
	public SettlementMotorcycleContext(List<CalculateSettlement<Motorcycle>> settlements) {
		this.settlements = settlements;
	}
	
	public BigDecimal calculate(Motorcycle motorcycle, ParkingOrder parkingOrder) {
		BigDecimal price = BigDecimal.ZERO;
		for (CalculateSettlement<Motorcycle> calculateSettlement : settlements) {
			price = calculateSettlement.getPrice(motorcycle, parkingOrder).add(price);
		}
		return price;
	}

}
