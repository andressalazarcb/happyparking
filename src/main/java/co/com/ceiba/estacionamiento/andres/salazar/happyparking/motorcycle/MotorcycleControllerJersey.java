package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;

@Controller
@Path("/parkinglot/motorcycles")
public class MotorcycleControllerJersey implements MotorcycleController {
	
	@Autowired
    private ObjectFactory<HappyParkingResponse> happyParkingResponseObjectFactory;
	
	private MotorcycleService motorcycleService;
	
	private MotorcycleRepositoryMongo motorcycleRepository;
	
	@Autowired
	public MotorcycleControllerJersey(MotorcycleService motorcycleService,
			MotorcycleRepositoryMongo motorcycleRepository) {
		this.motorcycleService = motorcycleService;
		this.motorcycleRepository = motorcycleRepository;
	}
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getIn(Motorcycle motorcycle) {
		Motorcycle motorcycleSaved = motorcycleService.save(motorcycle);
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
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		Status status = Status.NO_CONTENT;
		if(motorcycleRepository.findCountMotorcycleByIsParking(true) > 0) {
			status = Status.OK;
			happyParkingResponse.setStatus(Status.OK.getStatusCode());
			happyParkingResponse.setContent(motorcycleService.findAllMotorcyclesParking());
		}
		
		return Response
			      .status(status)
			      .entity(happyParkingResponse)
			      .build();
	}

}
