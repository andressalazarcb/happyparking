package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.motorcycle;

import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.application.VehicleParking;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.HappyParkingResponse;

@Controller
@Path("/parkinglot/motorcycles")
public class MotorcycleControllerJersey {
	
	@Autowired
    private ObjectFactory<HappyParkingResponse> happyParkingResponseObjectFactory;
	
	private VehicleParking<Motorcycle> vehicleParking;

	@Autowired
	public MotorcycleControllerJersey(VehicleParking<Motorcycle> vehicleParking) {
		this.vehicleParking = vehicleParking;
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getIn(MotorcycleRequestJersey requestJersey) {
		Motorcycle motorcycle = getMotorcycleFromMotorcycleRequestJersey(requestJersey);
		Motorcycle motorcycleSaved = vehicleParking.getInVehicleToParkingLot(motorcycle);
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		happyParkingResponse.setStatus(Status.CREATED.getStatusCode());
		happyParkingResponse.setContent(motorcycleSaved);
		return Response
			      .status(Status.CREATED)
			      .entity(happyParkingResponse)
			      .type(MediaType.APPLICATION_JSON)
			      .build();
	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllVehiclesParking() {
		Status status = Status.NO_CONTENT;
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		Stream<Motorcycle> stream = vehicleParking.findVehiclesThatAreParking();
		if(stream != null) {
			status = Status.OK;
			happyParkingResponse.setStatus(Status.OK.getStatusCode());
			happyParkingResponse.setContent(vehicleParking.findVehiclesThatAreParking());
		}
		return Response
			      .status(status)
			      .entity(happyParkingResponse)
			      .build();
	}


	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOut(MotorcycleRequestJersey requestJersey) {
		Motorcycle motorcycle = getMotorcycleFromMotorcycleRequestJersey(requestJersey);
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		Motorcycle motorcycleOut = vehicleParking.getOutVehicleOfParkingLot(motorcycle.getPlate());
		happyParkingResponse.setContent(motorcycleOut);
		happyParkingResponse.setStatus(Status.OK.getStatusCode());
		return Response
			      .status(Status.OK)
			      .entity(happyParkingResponse)
			      .build();
	}
	
	@GET
	@Path("{plate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findVehicleByPlate(@PathParam("plate") String plate) {
		Status status = Status.NO_CONTENT;
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		Motorcycle moto = vehicleParking.findVehicleByPlate(plate);
		if(moto != null) {
			status = Status.OK;
			happyParkingResponse.setStatus(Status.OK.getStatusCode());
			happyParkingResponse.setContent(moto);
		}
		return Response.status(status).entity(happyParkingResponse).build();
	}
	
	private Motorcycle getMotorcycleFromMotorcycleRequestJersey(MotorcycleRequestJersey requestJersey) {
		Motorcycle motorcycle = new Motorcycle();
		motorcycle.setCc(requestJersey.getCc());
		motorcycle.setPlate(requestJersey.getPlate());
		return motorcycle;
	}

}
