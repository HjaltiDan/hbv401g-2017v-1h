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
public class HotelManager implements Observer {

	private SearchView sv;
	private ResultsView rv;
	private ArrayList<Hotel> hotels;
	private ArrayList<Hotel> searchedHotels;
	private HotelStorage allHotels = new HotelStorage();
	
	public HotelManager(){
		loadAllHotels();
	}
	
	public void addSearchView(SearchView sv)
	{
		this.sv = sv;
	}
	public void addResultsView(ResultsView rv)
	{
		this.rv = rv;
	}
	
	public void update(Observable o, Object arg){
		
		//if(o instanceof ReservationView)
			//System.out.println("Caught ReservationView");
	
		//((ReservationView)(o)).displayConfirmation(send);
	
	}
	
	public ArrayList<Hotel> searchHotel(ArrayList parameters)
	/* The ArrayList called "parameters" should be of length 16. It contains all possible search conditions:
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
	 * (13) nearest city (String[], no specific length)
	 * (14) nearest airport (String[], no specific length)
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
	{
		boolean match = false;
		searchedHotels.clear();
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
			 * whether the string's pointer is set to null. (And if it is, calling methods 
			 * on it would be a bad idea anyway, since that's liable to cause a NullPointerException).
			 */
			String name = (String)parameters.get(3);
			if( (name != null) && !(name.equals(h.getName())) )
					match = false;
			
			//Fifth parameter: pricing options
			boolean[] hotelPrices = h.getPriceRange();
			boolean[] searchPrices = (boolean[])(parameters.get(4));
			if (searchPrices != null)
				/*The user chose something in this category. Remember, though, that we only have to
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
			/* Let's be a little careful here. A hotel's rating is a numerical int value. But the
			 * user is allowed to search for more than one rating option. So what we get is a
			 * boolean array of possible ratings, and we're going to match our hotel's rating
			 * to the value in the array's corresponding _position_. For example, if our hotel
			 * is rated 5 stars, we check whether array[4] is '1' (remember that arrays are zero-indexed).  
			 *   
			 * This way, we also get around the fact that int values can't be NULL; it doesn't affect
			 * us, because boolean arrays can.
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

			/* 
	 * (13) nearest city (String[], no specific length)
	 * (14) nearest airport (String[], no specific length)
	 * (15) nearest sites (String[], no specific length)
	 * (16) nearest day tours (String[], no specific length)   */

			
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
		this.hotels = new ArrayList<Hotel>(allHotels.selectAll());
	}

	public ArrayList<Hotel> searchHotel(LocalDate startDate, LocalDate endDate, int guests)
	{
		searchedHotels.clear();
		for(Hotel h : hotels){
			if(guests <= h.checkAvailability(startDate, endDate))
				searchedHotels.add(h);
		}
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

