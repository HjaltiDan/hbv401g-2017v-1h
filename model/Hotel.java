package model;
import java.util.*;

public class Hotel {
	private int hotelID;
	private String name;
	private boolean[] priceRange;
	private boolean[] openingMonths;
	private String address;
	//Vantar private freeRoomsPerDate = ArrayList[RoomsPerDay]
	//því við eigum eftir að implementa RoomsPerDay klasann
	private int rating;
	private boolean[] roomFacilities;
	private int hotelType;
	private boolean[] hotelFacilities;
	private int hotelLocation;
	private String nearestCity;
	private String nearestAirport;
	private ArrayList<String> nearestSites;
	private ArrayList<String> nearbyDayTours;
	
	
	public Hotel(int hotelID){
		this.hotelID = hotelID;
	}
	
	public Hotel(){
		
	}
	
	public int getHotelID() {
		return hotelID;
	}
	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean[] getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(boolean[] priceRange) {
		this.priceRange = priceRange;
	}

	public boolean[] getOpeningMonths() {
		return openingMonths;
	}

	public void setOpeningMonths(boolean[] openingMonths) {
		this.openingMonths = openingMonths;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean[] getRoomFacilities() {
		return roomFacilities;
	}

	public void setRoomFacilities(boolean[] roomFacilities) {
		this.roomFacilities = roomFacilities;
	}

	public int getHotelType() {
		return hotelType;
	}

	public void setHotelType(int hotelType) {
		this.hotelType = hotelType;
	}

	public boolean[] getHotelFacilities() {
		return hotelFacilities;
	}

	public void setHotelFacilities(boolean[] hotelFacilities) {
		this.hotelFacilities = hotelFacilities;
	}

	public String getNearestCity() {
		return nearestCity;
	}

	public void setNearestCity(String nearestCity) {
		this.nearestCity = nearestCity;
	}

	public String getNearestAirport() {
		return nearestAirport;
	}

	public void setNearestAirport(String nearestAirport) {
		this.nearestAirport = nearestAirport;
	}

	public int getHotelLocation() {
		return hotelLocation;
	}

	public void setHotelLocation(int hotelLocation) {
		this.hotelLocation = hotelLocation;
	}

	public ArrayList<String> getNearestSites() {
		return nearestSites;
	}

	public void setNearestSites(ArrayList<String> nearestSites) {
		this.nearestSites = nearestSites;
	}

	public ArrayList<String> getNearbyDayTours() {
		return nearbyDayTours;
	}

	public void setNearbyDayTours(ArrayList<String> nearbyDayTours) {
		this.nearbyDayTours = nearbyDayTours;
	}
	
}
