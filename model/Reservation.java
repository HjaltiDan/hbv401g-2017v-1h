package model;
import java.util.*;

public class Reservation {

	private int reservationID;
	private Hotel hotel;
	private User user;
	private Date startDate;
	private Date endDate;
	private int numberOfPeople;
	private int[] numberOfRoomsByType;
	private boolean hasBeenPaid;
	public int getReservationID() {
		return reservationID;
	}
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getNumberOfPeople() {
		return numberOfPeople;
	}
	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}
	public int[] getNumberOfRoomsByType() {
		return numberOfRoomsByType;
	}
	public void setNumberOfRoomsByType(int[] numberOfRoomsByType) {
		this.numberOfRoomsByType = numberOfRoomsByType;
	}
	public boolean isHasBeenPaid() {
		return hasBeenPaid;
	}
	public void setHasBeenPaid(boolean hasBeenPaid) {
		this.hasBeenPaid = hasBeenPaid;
	}
	
	
	
}
