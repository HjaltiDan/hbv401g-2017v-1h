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

		
		ReservationView rv = new ReservationView();
		ReservationManager rm = new ReservationManager();

		rm.addReservationView(rv);
		rv.addReservationManager(rm);
		
		rv.addObserver(rm);
		
		
		
		UserStorage testUserStorage = new UserStorage();
		//testUserStorage.testConnect();
		testUserStorage.selectAll();
	}


	
}
