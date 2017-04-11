package storage;
import model.*;
import control.*;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;


public class ReservationStorage {

	private Reservation res;
	private Hotel hotel;
	private User user;
	
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
	
	
	public int insert(Reservation r)
	{
		this.res = r;
		this.hotel = r.getHotel();
		int hotelID = hotel.getHotelID();
		int userID = 1; //Change this when we start saving users
		String start = (res.getStartDate()).toString();
		String end = (res.getEndDate()).toString();
		int guests = r.getNumberOfPeople();
		boolean isPaid = true; //Change this if we start checking whether reservations are prepaid
		String insertString = ("INSERT into Reservations(hotelID, userID, startDate, endDate, numberOfPeople, hasBeenPaid) "
				+"VALUES ("+hotelID+", "+userID+", '"
				+start+"', '"+end+"', "+guests+", "+1+")");
		//System.out.println(insertString);
		
		try 
		{Connection conn = this.connect();
		Statement insertStatement = conn.createStatement(); 
		int y = insertStatement.executeUpdate(insertString);
		conn.close();}

		catch (SQLException e)
		{
			System.out.println("Error in SQL Selectall()");
			System.out.println(e.getMessage());
      }
		
		
		
		return 0; //!!Make insert return the ID given when we register the reservation
	}
	
	
}
