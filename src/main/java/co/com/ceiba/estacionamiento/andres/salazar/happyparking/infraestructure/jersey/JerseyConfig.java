package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.car.CarControllerJersey;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.motorcycle.MotorcycleControllerJersey;

@Configuration
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(CarControllerJersey.class);
		register(MotorcycleControllerJersey.class);
		register(HappyParkingExceptionHandler.class);
		register(UncaughtThrowable.class);
	}

}
