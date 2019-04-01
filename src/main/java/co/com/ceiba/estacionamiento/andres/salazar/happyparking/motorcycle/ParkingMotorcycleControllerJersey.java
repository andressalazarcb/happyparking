package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;

@Controller
@Path("/parkinglot/motorcycles")
public class ParkingMotorcycleControllerJersey implements ParkingMotorcycleController {
	
	@Autowired
    private ObjectFactory<HappyParkingResponse> happyParkingResponseObjectFactory;
	
	private MotorcycleService motorcycleService;
	
	@Autowired
	public ParkingMotorcycleControllerJersey(MotorcycleService motorcycleService) {
		this.motorcycleService = motorcycleService;
	}

	public ParkingMotorcycleControllerJersey() {
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Object getIn(Motorcycle motorcycle) {
		Motorcycle motorcycleSaved = motorcycleService.save(motorcycle);
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		happyParkingResponse.setHttpStatus(HttpStatus.CREATED);
		happyParkingResponse.setContent(motorcycleSaved);
		return happyParkingResponse;
	}

	public Object findVehicles() {
		return null;
	}

	public Object getOut() {
		return null;
	}

	public Object findVehicle(String vehiclePlateParam) {
		return null;
	}

}
