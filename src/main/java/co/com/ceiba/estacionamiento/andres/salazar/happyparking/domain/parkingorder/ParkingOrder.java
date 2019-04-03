package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
@JsonIgnoreProperties(
		value = { "days", "hours", "totalHours" }
		)
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

	@Transient
	public  long  getDays() {
		long totalHours = getTotalHours();
		long limitDayOne = totalHours / 9l;
		if(limitDayOne >= 1) {
			long limitDayTwo = totalHours / 24l;
			if(limitDayTwo >= 1) {
				return limitDayTwo;
			}
		}
		return limitDayOne;
	}

	@Transient
	public long getHours() {
		long days = getDays();
		long totalHours = getTotalHours();
		if(days >= 1) {
			long hoursLeft = totalHours - (24 * days);
			if(hoursLeft < 0) {
				return 0;
			}
			return hoursLeft;
		}
		return totalHours;
	}

	@Transient
	public long getTotalHours() {
		if(startDate != null && endDate != null) {
			return TimeUnit.MILLISECONDS.toHours(endDate - startDate);
		}
		return 0l;
	}

	public void createParkingOrderId(String plate) {
		this.parkingOrderId = this.parkingOrderId + plate;
	}

}
