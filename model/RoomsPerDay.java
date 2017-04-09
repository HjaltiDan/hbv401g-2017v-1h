package model;
//import java.util.ArrayList; //For ArrayList, if we need it
import java.time.*;

public class RoomsPerDay {
	private LocalDate day;
	private int availableRooms;
	//	private ArrayList<int> availableRoomsByType;

	public RoomsPerDay(){
		this.availableRooms = 0;
	}
	
	public RoomsPerDay(RoomsPerDay rpd){
//		if(rpd == null)
//			System.exit(0);
		this.day = rpd.getDay();
		this.availableRooms = rpd.getAllAvailableRooms();
	}
	
	public RoomsPerDay(LocalDate day, int availableRooms){
		this.day = day;
		this.availableRooms = availableRooms;
	}
	

	
	
	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public int getAllAvailableRooms(){
		return availableRooms;
	}
	
	public int getAllAvailableRooms(int roomType){
		return availableRooms;
		}

	public void setAvailableRooms(int roomType, int number){
		//Ignoring roomType for now
		this.availableRooms = number;
	}
}
