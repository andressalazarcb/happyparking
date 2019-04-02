package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ParkingOrder {

	@Id
	private String parkingOrderId;
	private Long startDate;
	private Long endDate;
	private boolean active;
	private BigDecimal price;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getParkingOrderId() {
		return parkingOrderId;
	}

	public void setParkingOrderId(String parkingOrderId) {
		this.parkingOrderId = parkingOrderId;
	}

}
