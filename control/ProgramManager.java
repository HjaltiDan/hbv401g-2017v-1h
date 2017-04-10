package control;
import model.*;
import storage.*;
import view.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.event.*;

public class ProgramManager {

	public static void main(String[] args) {

		SearchView searchView = new SearchView();
		ResultsView resultsView = new ResultsView();
		ReservationView reservationView = new ReservationView();
		
		HotelManager hotelManager = new HotelManager(true);
		ReservationManager reservationManager = new ReservationManager();

		
		
		reservationView.setVisible(false);
		searchView.setVisible(true);
		
		
		reservationManager.addReservationView(reservationView);
		reservationView.addReservationManager(reservationManager);
		
		hotelManager.addResultsView(resultsView);
		resultsView.addHotelManager(hotelManager);
		
		hotelManager.addSearchView(searchView);
		searchView.addHotelManager(hotelManager);
		
		reservationView.addObserver(reservationManager);
		resultsView.addObserver(hotelManager);
		searchView.addObserver(hotelManager);

	}

	
}
