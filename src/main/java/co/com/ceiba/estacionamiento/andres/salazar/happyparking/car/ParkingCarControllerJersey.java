package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import java.util.ArrayList;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;

@Controller
@Path("/parkinglot/cars")
public class ParkingCarControllerJersey implements ParkingController {
	
	private CarRepository repositoryParkingCar;
	
	@Autowired
    private ObjectFactory<HappyParkingResponse> happyParkingResponseObjectFactory;

	public ParkingCarControllerJersey() {
	}

	@Autowired
	public ParkingCarControllerJersey(CarRepository repositoryParkingCar) {
		this.repositoryParkingCar = repositoryParkingCar;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object getIn() {
		return "Hello from Spring";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object findVehicles() {
		return new ArrayList<String>();
	}
	
	@GET
	@Path("/{vehiclePlate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object findVehicle(@PathParam("vehiclePlate") String vehiclePlateParam) {
		Optional<Car> parkingOrderCarOptional = repositoryParkingCar.findById(vehiclePlateParam);
		
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		if(parkingOrderCarOptional.isPresent()) {
			happyParkingResponse.setHttpStatus(HttpStatus.OK);
			happyParkingResponse.setContent(parkingOrderCarOptional.get());
		}else {
			happyParkingResponse.setHttpStatus(HttpStatus.NO_CONTENT);
		}
		
		return happyParkingResponse;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Object getOut() {
		return "Hello from Spring";
	}

}
