package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MotorcycleFactory implements FactoryBean<Motorcycle>{

	@Override
	public Motorcycle getObject() throws Exception {
		Motorcycle currentMotorcycle = new Motorcycle();
		currentMotorcycle.setParking(true);
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
