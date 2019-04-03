package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.settlement;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.CalculateSettlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Settlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

@Component
public class SettlementCarContext implements Settlement<Car>{
	
	private List<CalculateSettlement<Car>> settlements;
	
	@Autowired
	public SettlementCarContext(List<CalculateSettlement<Car>> settlements) {
		this.settlements = settlements;
	}

	@Override
	public BigDecimal calculate(Car carFound, ParkingOrder parkingOrder) {
		BigDecimal price = BigDecimal.ZERO;
		for (CalculateSettlement<Car> calculateSettlement : settlements) {
			price = calculateSettlement.getPrice(carFound, parkingOrder).add(price);
		}
		return price;
	}

}
