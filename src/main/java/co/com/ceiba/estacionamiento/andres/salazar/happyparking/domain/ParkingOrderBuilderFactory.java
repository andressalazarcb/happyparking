package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ParkingOrderBuilderFactory implements ParkingOrderBuilder {
	
	private ParkingOrder parkingOrder;

	public ParkingOrderBuilderFactory() {
		Long time = System.currentTimeMillis();
		this.parkingOrder = new ParkingOrder();
		this.parkingOrder.setStartDate(time);
		this.parkingOrder.setActive(true);
	}

	@Override
	public void buildStartDate(Long startDate) {
		this.parkingOrder.setStartDate(startDate);

	}

	@Override
	public void buildendDate(Long endDate) {
		this.parkingOrder.setEndDate(endDate);

	}

	@Override
	public void buildActive(boolean active) {
		this.parkingOrder.setActive(active);

	}

	@Override
	public ParkingOrder getParkingOrder() {
		return this.parkingOrder;
	}

}
