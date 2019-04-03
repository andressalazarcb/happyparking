package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ParkingOrderFactory implements FactoryBean<ParkingOrder>{

	@Override
	public ParkingOrder getObject() throws Exception {
		long time = System.currentTimeMillis();
		ParkingOrder parkingOrder = new ParkingOrder();
		parkingOrder.setParkingOrderId(time+"_");
		parkingOrder.setActive(true);
		parkingOrder.setStartDate(System.currentTimeMillis());
		return parkingOrder;
	}

	@Override
	public Class<?> getObjectType() {
		return ParkingOrder.class;
	}
	
	@Override
    public boolean isSingleton() {
        return false;
    }

}
