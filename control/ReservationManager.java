package control;
import model.*;
import storage.*;
import view.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.*;
import java.time.LocalDate;

public class ReservationManager implements ActionListener, Observer {

	ReservationView rv;
	HotelManager hotelManager;
	int tempint = 0;
	private Hotel hotel;
	private LocalDate startDate, endDate;
	private int numberOfGuests;
	private Reservation newReservation;
	private ReservationStorage allReservations = new ReservationStorage();
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("Caught actionperformed");
		//System.out.println (e.getActionCommand());
	}
	
	public void addReservationView(ReservationView r){
		//System.out.println("Adding r to ReservationManager");
		this.rv = r;
	}

	public void addHotelManager(HotelManager hm)
	{
		this.hotelManager = hm;
	}
	
	public int addReservation(Hotel hotel, LocalDate startDate, LocalDate endDate, int numberOfGuests)
	{
		int reservationID;
		this.hotel = hotel;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numberOfGuests = numberOfGuests;
		
		newReservation = new Reservation(this.hotel, this.startDate, this.endDate, this.numberOfGuests);
		reservationID = allReservations.insert(newReservation);
		//Create instance of Reservation. Save it with ReservationStorage.insert(), have insert()
		//figure out its unique reservationID and either return it, or (way more work) call a.
		//separate search function. Then take that reservationID, and return it from this function,
		//so that we can send it up to ReervationView and display it there. 
		//DON'T FORGET to ... if we need ... use setReservationID. Probably don't need it.
		
		return (reservationID);
	}
	
	
	public void update(Observable o, Object arg){
		//System.out.println("ReservationManager caught an update!");
		//System.out.println("The arg is "+arg);
		String send = "IsConfirmed";
		//if(o instanceof ReservationView)
			//System.out.println("Caught ReservationView");
		//((ReservationView)(o)).displayConfirmation(send);
		rv.displayConfirmation(send);
	}
	
	public int writeTempint() {
		return tempint;
	}
}
