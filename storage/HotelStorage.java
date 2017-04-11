package storage;
import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class HotelStorage {
	
	private ArrayList<Hotel> hotels;
	private ArrayList<RoomsPerDay> allFreeRooms;
	private RoomsPerDay roomOnDate;
	private Date date;
	private LocalDate selectedDay;
	private int availableRooms;
	private DateTimeFormatter formatter;
	
	
	public HotelStorage(){
		hotels = new ArrayList<Hotel>();
		allFreeRooms = new ArrayList<RoomsPerDay>();
		formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
		//formatter = formatter.withLocale( putAppropriateLocaleHere );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
	}
	
	private Connection connect() 
	{
		String url = "jdbc:sqlite:lib/1H.db";
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection(url);
	   }
		catch (SQLException e) 
		{
	        System.out.println(e.getMessage());
	   }
		return conn;
	}
	
	public ArrayList<Hotel> selectAll()
	{
		hotels.clear();
		String sqlAllHotels = "SELECT * FROM Hotels";
		//String sqlAllHotels = "SELECT * FROM Hotels WHERE hotelID IS NOT null";
		try 
		(Connection conn = this.connect();
		Statement stmtHotels = conn.createStatement(); 
		ResultSet rsHotels = stmtHotels.executeQuery(sqlAllHotels);)
		{
	      while (rsHotels.next())
	      {
	      	Hotel h = new Hotel();
	      	allFreeRooms.clear();
	      	int hotelID = rsHotels.getInt("hotelID");
	      	String sqlAllRoomsPerDay = "SELECT * FROM RoomsPerDay WHERE hotelID = "+hotelID;
	      	h.setHotelID(hotelID);
	      		      	
	      	h.setName(rsHotels.getString("name"));
	      	
	      	boolean[] priceRange = new boolean[5];
	      	String pR = rsHotels.getString("priceRange");
	      	for(int i=0; i<pR.length(); i++){
	      		if(pR.charAt(i)=='1')
	      			priceRange[i]=true;
	      		else
	      			priceRange[i]=false; }
	      	h.setPriceRange(priceRange);
	      	
	      	boolean[] openingMonths = new boolean[12];
	      	String oM = rsHotels.getString("openingMonths");
	      	for(int i=0; i<oM.length(); i++){
	      		if(oM.charAt(i)=='1')
	      			openingMonths[i]=true;
	      		else
	      			openingMonths[i]=false; }
	      	h.setOpeningMonths(openingMonths);
	      	
	      	h.setAddress(rsHotels.getString("address"));
	      	
	      	Statement stmtRooms = conn.createStatement(); 
	   		ResultSet rsRooms = stmtRooms.executeQuery(sqlAllRoomsPerDay);
	   		while (rsRooms.next())
	   		{
	   			String stringDate = rsRooms.getString("date");
	   			selectedDay = LocalDate.parse(stringDate);
	   			//Old code when we were using Date rather than LocalDate:
	   			//date = rsRooms.getDate("date");
	   			//selectedDay = date.toLocalDate();
	   			availableRooms = rsRooms.getInt("availableRooms");
	   			roomOnDate = new RoomsPerDay(selectedDay, availableRooms);
	   			allFreeRooms.add(roomOnDate);
	   			//System.out.println("Hotel "+h.getName()+" has "+availableRooms+" rooms available on date "+selectedDay);
	   		}
	   		//System.out.println("Hotel "+h.getName()+ " has "+allFreeRooms.size()+" freeRoom entries");
	   		h.setFreeRoomsPerDate(allFreeRooms);
	      	
	   		h.setRating(rsHotels.getInt("rating"));
	   		
	   		boolean[] roomFacilities = new boolean[6];
	      	String rF = rsHotels.getString("roomFacilities");
	      	for(int i=0; i<rF.length(); i++){
	      		if(rF.charAt(i)=='1')
	      			roomFacilities[i]=true;
	      		else
	      			roomFacilities[i]=false; }
	      	h.setRoomFacilities(roomFacilities);
	      	
	      	h.setHotelType(rsHotels.getInt("hotelType"));
	      	
	      	boolean[] hotelFacilities = new boolean[6];
	      	String hF = rsHotels.getString("roomFacilities");
	      	for(int i=0; i<hF.length(); i++){
	      		if(hF.charAt(i)=='1')
	      			hotelFacilities[i]=true;
	      		else
	      			hotelFacilities[i]=false; }
	      	h.setHotelFacilities(hotelFacilities);
	      	
	      	h.setHotelLocation(rsHotels.getInt("hotelLocation"));
	      	
	      	h.setNearestCity(rsHotels.getString("nearestCity"));
	      	
	      	h.setNearestAirport(rsHotels.getString("nearestAirport"));
	      	
	      	h.setNearestSite(rsHotels.getString("nearestSites"));
	      	
	      	h.setNearbyDayTour(rsHotels.getString("nearbyDayTours"));
	      	
	      	/*
	      	//Old code, for reference. Meant for multiple strings.
	      	String siteList = rsHotels.getString("nearestSites");
	      	ArrayList<String> siteArray = new ArrayList<String>(Arrays.asList(siteList.split(", ")));
	      	h.setNearestSites(siteArray);
	      	
	      	String tourList = rsHotels.getString("nearbyDayTours");
	      	ArrayList<String> tourArray = new ArrayList<String>(Arrays.asList(tourList.split(", ")));
	      	h.setNearestDayTours(tourArray);
	      	*/
	      	
	      	hotels.add(h);
	      } //while (rsRooms.next())
		} //try block
		catch (SQLException e)
		{
			System.out.println("Error in SQL Selectall()");
			System.out.println(e.getMessage());
      }
		
		//Test code for selectAll
		//System.out.println("Finished selectAll(), have loaded "+hotels.size()+" hotels into the ArrayList");
		
		return hotels;
	} //selectAll()
	
}
