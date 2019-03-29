package co.com.ceiba.estacionamiento.andres.salazar.happyparking.parking;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;

@Controller
@Path("/parkinglot/cars")
public class ControllerParkingCarJersey implements ControllerParking {

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
		return "Hello from Spring";
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Object getOut() {
		return "Hello from Spring";
	}

}
