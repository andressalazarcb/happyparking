package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car.settlement;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@Component
public class CarSettlementContext implements CarSettlement{
	
	private List<CarCalculateSettlement> settlements;
	
	@Autowired
	public CarSettlementContext(List<CarCalculateSettlement> settlements) {
		this.settlements = settlements;
	}

	@Override
	public BigDecimal calculate(Car carFound, ParkingOrder parkingOrder) {
		BigDecimal price = BigDecimal.ZERO;
		for (CarCalculateSettlement calculateSettlement : settlements) {
			price = calculateSettlement.getPrice(carFound, parkingOrder).add(price);
		}
		return price;
	}

}
