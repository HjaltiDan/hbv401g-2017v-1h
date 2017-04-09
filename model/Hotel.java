package model;
import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Hotel {
	private int hotelID = -1;
	private String name;
	private boolean[] priceRange;
	private boolean[] openingMonths;
	private String address;
	private ArrayList<RoomsPerDay> freeRoomsPerDate;
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
	public Hotel(int hotelID, String name, boolean[] priceRange, boolean[] openingMonths, String address, 
			ArrayList<RoomsPerDay> freeRoomsPerDate, int rating, boolean[] roomFacilities, int hotelType,
			boolean[] hotelFacilities, int hotelLocation, String nearestCity, String nearestAirport,
			ArrayList<String> nearestSites, ArrayList<String> nearbyDayTours)
	{
		this.hotelID = hotelID;
		this.name = name;
		this.priceRange = priceRange;
		this.openingMonths = openingMonths;
		this.address = address; 
		this.setFreeRoomsPerDate(freeRoomsPerDate);
		this.rating = rating;
		this.roomFacilities = roomFacilities;
		this.hotelType = hotelType;
		this.hotelFacilities = hotelFacilities;
		this.hotelLocation = hotelLocation;
		this.nearestCity = nearestCity;
		this.nearestAirport = nearestAirport;
		this.nearestSites = nearestSites;
		this.nearbyDayTours = nearbyDayTours;
	}
	public Hotel(){}

	public int checkAvailability(LocalDate startDate, LocalDate endDate){
		/*Algorithm: Cycle through all freeRoomsPerDate on the range
		 * defined by startDate and endDate. Return the lowest
		 * number in that range, since that's the highest number
		 * the hotel can guarantee for *every* day during the date range.*/
		long duration = ChronoUnit.DAYS.between(startDate, endDate);
		LocalDate day = startDate;
		int maxPossibleRooms = 0;
		RoomsPerDay dailyRoom = new RoomsPerDay(findRoomDay(startDate));
		if(dailyRoom == null)
			return 0;
		maxPossibleRooms = dailyRoom.getAllAvailableRooms();
		for(int i = 1; i <= duration; i++)
		{
			day = startDate.plusDays(i);
			dailyRoom = new RoomsPerDay(findRoomDay(day));
			if(dailyRoom == null)
				return 0;
			int roomsThatDay = dailyRoom.getAllAvailableRooms();
			if(roomsThatDay < maxPossibleRooms)
				maxPossibleRooms = roomsThatDay;
		}
		return maxPossibleRooms;
	}

	private RoomsPerDay findRoomDay(LocalDate date){
		for(RoomsPerDay e : freeRoomsPerDate)
			if(e.getDay() == date)
				return e;
		return null;
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
	public ArrayList<RoomsPerDay> getFreeRoomsPerDate() {
		return freeRoomsPerDate;
	}
	public void setFreeRoomsPerDate(ArrayList<RoomsPerDay> freeRoomsPerDate) {
		this.freeRoomsPerDate = freeRoomsPerDate;
	}
	

}
