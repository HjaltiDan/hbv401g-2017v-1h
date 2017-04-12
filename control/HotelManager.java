package control;
import model.*;

import storage.*;
import view.*;
import view.ReservationView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.*;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
public class HotelManager implements Observer
{
	private SearchView sv;
	private ResultsView rv;
	private ArrayList<Hotel> hotels;
	private ArrayList<Hotel> searchedHotels = new ArrayList<Hotel>();
	private HotelStorage allHotels;
	private ReservationManager reservationManager;
	
	public HotelManager()
	{	}
	
	/* Header: This HotelManager(b) constructor runs loadAllHotels() if b==true
	 * We needed this extra constructor because sometimes we want to create a new,
	 * temporary HotelManager without loading all the data (f.ex. in ReservationView).
	 * If we load the data every single time we initialize the constructor, we start
	 * running into trouble: The ResultSet contains double entries, some of them are
	 * null values, etc.
	 * So we call "new HotelManager(true)" if and only if we want it to load
	 * all the date from our database (basically, in the main function in
	 * ProgramManager). Anywhere else, such as in our views, we simply call
	 * "new HotelManager()" or "HotelManager m;"
	 */	
	public HotelManager(boolean initialize){
		if(initialize){
			allHotels = new HotelStorage();
			loadAllHotels();
		}
}
	
	//public void initializeHotels(){
//	}
	
	public void addSearchView(SearchView sv)
	{
		this.sv = sv;
	}
	public void addResultsView(ResultsView rv)
	{
		this.rv = rv;
	}
	public void addReservationManager(ReservationManager rm)
	{
		this.reservationManager = rm;
	}
	
	public void update(Observable o, Object arg){
		
		//if(o instanceof ReservationView)
			//System.out.println("Caught ReservationView");
	
		//((ReservationView)(o)).displayConfirmation(send);
	
	}

	/* Header: searchHotel(p) looks for all hotels matching p (which must be size & capacity 16).
	 *	The ArrayList called "parameters" should be of length 16. It contains all possible search conditions:
	 * (1) start date (type LocalDate is very much preferred; though we can handle Date with a bit of ugly conversion) 
	 * (2) end date (also type LocalDate)
	 * (3) number of guests (int)
	 * (4) hotel name (String)
	 * (5) price range (boolean[], length 5)
	 * (6) opening months (boolean[], length 12)
	 * (7) address (String) 
	 * (8) ratings (boolean[], length 5)
	 * (9) room facilities (boolean[], length 6)
	 * (10) hotel type (boolean[], length 5)
	 * (11) hotel facilities (boolean[], length 6)
	 * (12) hotel area location (int[], no specific length, but all numbers should be three-digit area codes)   
	 * (13) nearest city (String, no specific length)
	 * (14) nearest airport (String, no specific length)
	 * (15) nearest sites (String[], no specific length)
	 * (16) nearest day tours (String[], no specific length)  
	 * 
	 * If a search condition was not specified by the user (meaning the user doesn't care about
	 * that particular condition - location, pricing, rating, etc.) then its corresponding position
	 * in our ArrayList will hold a NULL value, and will be ignored in our search.
	 *    The only exceptions to this are the start date, end date and number of guests: 
	 *    If any of these are NULL, our search will simply return no results 
	 *    (rather than throw exceptions and destabilize the entire program).  
	 * 
	 * Also, since we've already loaded all our hotels into the "hotels" ArrayList, we can keep all
	 * the code here - rather than having to descend into HotelStorage and perform a bunch of
	 * horrible, liable-to-break SQL conversions there.
	 */
	public ArrayList<Hotel> searchHotel(ArrayList parameters)
	{
		boolean match = false;
		searchedHotels.clear();
		
		/* First, let's make sure we got the number of parameters we need.
		 * If not, we return an empty search array right away.*/
		if(parameters.size() < 16)
			return searchedHotels;
		
		for(Hotel h : hotels){
			/*Let's assume this hotel is a match until we find out otherwise*/
			match = true;
			
			/*Also, we _could_ run all these checks with lines like
			 * 	if( (!(parameters.get(0) == null)) && 	(!(parameters.get(0).equals(h.getName()))) )
			 * but that kind of code is really cryptic. So let's use temporary variables
			 * instead, for each of the parameters.*/

			//First, second and third parameters:
			//startDate, endDate, and number of guests
			LocalDate startDate = (LocalDate)parameters.get(0);
			LocalDate endDate = (LocalDate)parameters.get(1);
			int numberOfGuests = (int)parameters.get(2);
			if( (startDate == null)	|| (endDate == null) || (numberOfGuests < 1) )
				match = false;
			if(numberOfGuests > h.checkAvailability(startDate, endDate))
				match = false;
			
			//Fourth parameter: name
			/* Yes, we're using "!=" in a string comparison, instead of the ".equals()" method. 
			 * That's because we're not comparing the string to another string, but checking 
			 * whether the string's pointer is set to null - and if it is, calling one of its
			 * methods might cause a NullPointerException. */
			String name = (String)parameters.get(3);
			if( (name != null) && !(name.equals(h.getName())) )
					match = false;
			
			//Fifth parameter: pricing options
			boolean[] hotelPrices = h.getPriceRange();
			boolean[] searchPrices = (boolean[])(parameters.get(4));
			if (searchPrices != null)
				/*The user chose something in this category. Remember that we only have to
				 * match _one_ of the criteria he chose, not all of them.*/
				if (!findMatch(hotelPrices, searchPrices))
					match = false;
			
			//Sixth parameter: opening months
			boolean[] hotelOpeningMonths = h.getOpeningMonths();
			boolean[] searchOpeningMonths = (boolean[])(parameters.get(5));
			if (searchOpeningMonths != null)
				if (!findMatch(hotelOpeningMonths, searchOpeningMonths))
					match = false;
			
			//Seventh parameter: address
			String address = (String)parameters.get(6);
			if( (address != null) && !(address.equals(h.getName())) )
					match = false;
			
			//Eight parameter: rating
			/* Let's be a little careful here. A hotel's rating is a single int value. But the
			 * user is allowed to search for more than one rating option. So what we get is a
			 * boolean array of possible ratings, and we're going to match our hotel's rating
			 * to the value in the array's corresponding _position_. For example, if our hotel
			 * is rated 5 stars out of 5, we check whether array[4] is '1' (it's [4] rather than
			 * [5] because arrays are zero-indexed).  
			 *   
			 * This way, we also get around the parameter complication that int values can't 
			 * be NULL; it doesn't affect us, because boolean arrays can.
			 */
			boolean[] searchRatings = (boolean[])(parameters.get(7));
			if( searchRatings != null )
			{
				int hotelRating = h.getRating();
				if( !(searchRatings[hotelRating-1]) )
					match = false;
			}
			
			//Ninth parameter: room facilities
			boolean[] hotelRoomFacilities = h.getRoomFacilities();
			boolean[] searchRoomFacilities = (boolean[])(parameters.get(8));
			if (searchRoomFacilities != null)
				if (!findMatch(hotelRoomFacilities, searchRoomFacilities))
					match = false;
			
			//Tenth parameter: hotel type
			//Handled same way as ratings
			boolean[] searchTypes = (boolean[])(parameters.get(9));
			if( searchTypes!= null )
			{
				int hotelType = h.getHotelType();
				if( !(searchTypes[hotelType-1]) )
					match = false;
			}
		
			//Eleventh parameter: hotel facilities
			boolean[] hotelFacilities = h.getHotelFacilities();
			boolean[] searchFacilities = (boolean[])(parameters.get(10));
			if (searchFacilities != null)
				if (!findMatch(hotelFacilities, searchFacilities))
					match = false;
	
			
			//Twelth parameter: hotel area location
			int hotelLocation = h.getHotelLocation();
			int[] searchLocations = (int[])(parameters.get(11));
			if( searchLocations != null){
				boolean foundLocation = false;
				for(int i : searchLocations)
				{
					if(i == hotelLocation)
						foundLocation = true;
				}
				if(!foundLocation)
					match = false;
			}

			//Thirteenth parameter: nearest city
			String searchCity = (String)parameters.get(12);
			if( (searchCity != null) && !(searchCity.equals(h.getNearestCity())) )
					match = false;

			//Fourteenth parameter: nearest airport
			String searchAirport = (String)parameters.get(13);
			if( (searchAirport != null) && !(searchAirport.equals(h.getNearestAirport())) )
					match = false;
			
			//Fifteenth parameter: nearest sites
			//N.B: A hotel now has only ONE nearest site, not many
			String searchSite = (String)(parameters.get(14));
			if( (searchSite != null) && !(searchSite.equals(h.getNearestSite())) )
				match = false;
			
			//Sixteenth parameter: nearest day tour
			//N.B: A hotel now has only ONE nearest site, not many
			String searchTour = (String)(parameters.get(15));
			if( (searchTour != null) && !(searchTour.equals(h.getNearbyDayTour())) )
				match = false;
			
			if(match)
				searchedHotels.add(h);
		}
		return searchedHotels;
	}

	private boolean findMatch(boolean[] a, boolean[] b)
	{
		boolean foundMatch = false;
		for(int i=0; i<a.length; i++)
		{
   		if( a[i] && b[i] )
   			foundMatch = true;
		}
   return foundMatch;
	}
	
	private void loadAllHotels()
	{
		this.hotels = (ArrayList<Hotel>)(allHotels.selectAll());
		//System.out.println("Went into loadAllHotels()");
		//System.out.println("Number of loaded hotels is "+hotels.size());
	}

	public int hotelCount(){
	return hotels.size();
	}

	public void reserveRoomsForConfirmedReservation(Hotel hotel, LocalDate startDate, LocalDate endDate, int numberOfGuests)
	/* We would make this function protected if it were only our program - it's usually called by
	 * ReservationManager, which is in the same Control package as HotelManager, and which only calls
	 * this function once it has made a valid reservation. But since this class need to provide
	 * outside access, we're making the function public. */
	{
		for(Hotel h : hotels)
		{
			if(h.isEqual(hotel))
				h.decreaseAvailability(startDate, endDate, numberOfGuests);
		}
	}
	
	public ArrayList<Hotel> searchHotel(LocalDate startDate, LocalDate endDate, int guests)
	{
		searchedHotels.clear();
		//System.out.println("Size of hotels in HotelManager is "+hotels.size());
		for(Hotel h : hotels){
			/*System.out.println("Hotel "+h.getName()+" has maximum "+h.checkAvailability(startDate, endDate)
			+" free rooms between the dates "+startDate+" and "+endDate);*/
			//System.out.println("Hotel "+h.getName()+" has maximum "+h.numberofRoomDays()+" roomDays available.");
			//System.out.println("Availability is "+h.checkAvailability(startDate, endDate));
			if(guests <= h.checkAvailability(startDate, endDate))
			{
				//System.out.println("Added hotel "+h.getName());
				searchedHotels.add(h);
			}
		}
		//System.out.println("Number of search results (searchedHotels) is "+searchedHotels.size());
		return searchedHotels;
	}
	
	
//This is a searchHotel method that takes in Date objects, not LocalDate
//Shown for reference in case we need to implement the Date->Localdate
//conversion again, for example if anyone outside 1H needs it
/*
	public ArrayList<Hotel> searchHotel(Date startDate, Date endDate, int guests)
{
	searchedHotels.clear();
	LocalDate sD = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	LocalDate eD = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	for(Hotel h : hotels){
		if(guests <= h.checkAvailability(sD, eD))
			searchedHotels.add(h);
	}
	return searchedHotels;
}
*/

	}

