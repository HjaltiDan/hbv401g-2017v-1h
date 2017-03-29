package control;
import model.*;
import storage.*;
import view.*;
import view.ReservationView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class HotelManager implements Observer {

	private SearchView sv;
	private ResultsView rv;
	private ArrayList<Hotel> hotels;
	
	
	public void addSearchView(SearchView sv){
		//System.out.println("Adding r to ReservationManager");
		this.sv = sv;
	}
	
	public void addResultsView(ResultsView rv){
		//System.out.println("Adding r to ReservationManager");
		this.rv = rv;
	}
	
	public void update(Observable o, Object arg){
		
		//if(o instanceof ReservationView)
			//System.out.println("Caught ReservationView");
	
		//((ReservationView)(o)).displayConfirmation(send);
	
	}
	
	public ArrayList<Hotel> searchHotel(ArrayList parameters){
		return hotels;
	}

	
	}


