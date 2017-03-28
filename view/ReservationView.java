package view;
import model.*;
import storage.*;
import control.*;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.*;

public class ReservationView extends Observable {

	private JFrame frame;
	private JButton btnConfirm = new JButton();
	int counter = 0;
	private ReservationManager resm = new ReservationManager();


	/**
	 * Launch the application.
	 */
	public void start(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservationView window = new ReservationView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ReservationView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		btnConfirm = new JButton("Confirm");
		
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Entered mouseClicked and event is "+arg0 );
				setChanged();
				//System.out.println("Notifying observers with "+btnConfirm.getText());
				notifyObservers(btnConfirm.getText());
				//counter++;
				//btnConfirm.setText(Integer.toString(counter));
			}
		});
		panel.add(btnConfirm);
	}

	public void displayConfirmation(String txt){
		//System.out.println("Got to displayConfirmation, txt var contains '"+txt+"'");
		//String currBtnText = btnConfirm.getText();
		//System.out.println("Current button text is "+currBtnText);
		btnConfirm.setText(txt);
	}
	
	public void addReservationManager(ReservationManager rm){
		resm = rm;
		
		//System.out.println(rm.writeTempint());
		//System.out.println("View      : adding controller");
		//btnConfirm.addActionListener((ActionListener)rm);	//need instance of controller before can add it as a listener 
	}
	
}
