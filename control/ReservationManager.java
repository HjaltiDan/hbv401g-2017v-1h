package control;
import model.*;
import storage.*;
import view.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.*;

public class ReservationManager implements ActionListener, Observer {

	ReservationView rv;
	int tempint = 0;
	
	
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
