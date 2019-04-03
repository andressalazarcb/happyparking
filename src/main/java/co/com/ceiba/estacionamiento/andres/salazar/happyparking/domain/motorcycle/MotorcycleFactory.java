package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.VehicleType;

@Component
public class MotorcycleFactory implements FactoryBean<Motorcycle>{

	@Override
	public Motorcycle getObject() throws Exception {
		Motorcycle currentMotorcycle = new Motorcycle();
		currentMotorcycle.setParking(true);
		currentMotorcycle.setType(VehicleType.MOTORCYCLE.getValue());
		return currentMotorcycle;
	}

	@Override
	public Class<?> getObjectType() {
		return Motorcycle.class;
	}
	
	@Override
    public boolean isSingleton() {
        return false;
    }

}
