package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import javax.ws.rs.core.Response.Status;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class HappyParkingResponse {
	
	private int status;
	private Object content;
	
	public Object getContent() {
		return content;
	}
	
	public void setContent(Object content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
