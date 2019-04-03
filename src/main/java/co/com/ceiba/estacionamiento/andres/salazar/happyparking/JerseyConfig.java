package co.com.ceiba.estacionamiento.andres.salazar.happyparking;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.car.CarControllerJersey;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle.MotorcycleControllerJersey;

@Configuration
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(CarControllerJersey.class);
		register(MotorcycleControllerJersey.class);
		register(HappyParkingException.class);
		register(UncaughtException.class);
	}

}
