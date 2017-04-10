package view;
import model.*;
import storage.*;
import control.*;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jdatepicker.impl.*;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Calendar;
import java.util.Date;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import com.toedter.components.JLocaleChooser;
import com.toedter.components.JSpinField;
import com.toedter.components.JTitlePanel;
import javax.swing.border.CompoundBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class SearchView extends Observable {

	private JFrame frame;
	private JPanel contentPane;
	private JComboBox comboStartDay;
	JComboBox comboStartMonth;
	private JLabel lblStartDate = new JLabel();
	private JComboBox comboStartYear;
	private JPanel basicSearchPanel;
	private JComboBox comboEndDay;
	private JComboBox comboEndMonth;
	private JComboBox comboEndYear;
	private JLabel lblEndDate;
	private JLabel lblHowMany;
	private JTextField textFieldHowMany;
	private JButton btnSearch;
	JCheckBox chckbxAdvancedOptions;
	JPanel advancedSearchPanel;

	private HotelManager hotelManager;
	private ArrayList searchChoices = new ArrayList();
	private ArrayList<Hotel> searchResults = new ArrayList();
	private Calendar startDate;
	private Calendar endDate;
	private int numberOfGuests;
	private boolean[] priceRange = new boolean[5];
	
	/**
	 * Create the frame.
	 */
	public SearchView() {
		frame = new JFrame();
		frame.setBounds(100, 100, 619, 455);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(50, 50, 300, 100));
		frame.getContentPane().add(contentPane, BorderLayout.CENTER);
		
		//setContentPane(contentPane);
		contentPane.setLayout(null);
		
		basicSearchPanel = new JPanel();
		basicSearchPanel.setBounds(12, 13, 489, 121);
		contentPane.add(basicSearchPanel);
		basicSearchPanel.setLayout(null);
		
		comboStartDay = new JComboBox();
		comboStartDay.setBounds(12, 35, 41, 22);
		basicSearchPanel.add(comboStartDay);
		comboStartDay.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboStartDay.setSelectedIndex(0);
		comboStartDay.setMaximumRowCount(5);
		
		comboStartMonth = new JComboBox();
		comboStartMonth.setBounds(65, 35, 41, 22);
		basicSearchPanel.add(comboStartMonth);
		comboStartMonth.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		
		comboStartYear = new JComboBox();
		comboStartYear.setBounds(118, 35, 55, 22);
		basicSearchPanel.add(comboStartYear);
		comboStartYear.setModel(new DefaultComboBoxModel(new String[] {"2017", "2018", "2019", "2020", "2021", "2022", "2023"}));
		lblStartDate.setBounds(54, 13, 68, 16);
		basicSearchPanel.add(lblStartDate);
		
		lblStartDate.setText("Start Date");
		
		comboEndDay = new JComboBox();
		comboEndDay.setBounds(229, 35, 50, 22);
		comboEndDay.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		basicSearchPanel.add(comboEndDay);
		
		comboEndMonth = new JComboBox();
		comboEndMonth.setBounds(306, 35, 50, 22);
		comboEndMonth.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		basicSearchPanel.add(comboEndMonth);
		
		comboEndYear = new JComboBox();
		comboEndYear.setBounds(389, 35, 50, 22);
		comboEndYear.setModel(new DefaultComboBoxModel(new String[] {"2017", "2018", "2019", "2020", "2021", "2022", "2023"}));
		basicSearchPanel.add(comboEndYear);
		
		lblEndDate = new JLabel("End date");
		lblEndDate.setBounds(293, 13, 56, 16);
		basicSearchPanel.add(lblEndDate);
		
		lblHowMany = new JLabel("How many?");
		lblHowMany.setBounds(12, 92, 75, 16);
		basicSearchPanel.add(lblHowMany);
		
		textFieldHowMany = new JTextField();
		textFieldHowMany.setBounds(86, 89, 35, 22);
		basicSearchPanel.add(textFieldHowMany);
		textFieldHowMany.setColumns(10);
		
		chckbxAdvancedOptions = new JCheckBox("Advanced options");
		chckbxAdvancedOptions.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(chckbxAdvancedOptions.isSelected())
					advancedSearchPanel.setVisible(true);
				else
					advancedSearchPanel.setVisible(false);
			}
		});
		chckbxAdvancedOptions.setBounds(221, 88, 130, 25);
		basicSearchPanel.add(chckbxAdvancedOptions);
		
		btnSearch = new JButton("Search!");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setChanged();
				notifyObservers();
				/*
				//Part of working code, commenting it out
				//while we test DB functionality
				gatherSelectedOptions();
				searchChoices.clear();
				searchChoices.add(comboEndYear.getSelectedItem());
				
				searchResults = hotelManager.searchHotel(searchChoices);
				*/
				
				//testSearchFunction() - remove this once we're satisfied
				//that the DB functionality works
				testSearchFunction();
				
			}
		});
		
		
		
		
		btnSearch.setBounds(380, 88, 97, 25);
		basicSearchPanel.add(btnSearch);
		
		advancedSearchPanel = new JPanel();
		advancedSearchPanel.setBounds(22, 147, 479, 248);
		contentPane.add(advancedSearchPanel);
		advancedSearchPanel.setLayout(null);
		advancedSearchPanel.setVisible(false);
		
		JLabel lblPriceRange = new JLabel("Price per night");
		lblPriceRange.setBounds(12, 13, 88, 16);
		advancedSearchPanel.add(lblPriceRange);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("0-5000kr");
		chckbxNewCheckBox.setBounds(8, 38, 113, 25);
		advancedSearchPanel.add(chckbxNewCheckBox);
		
		JCheckBox chckbxkr = new JCheckBox("5-10.000kr");
		chckbxkr.setBounds(8, 64, 113, 25);
		advancedSearchPanel.add(chckbxkr);
		
		JCheckBox chckbxkr_1 = new JCheckBox("10-20.000kr");
		chckbxkr_1.setBounds(8, 94, 113, 25);
		advancedSearchPanel.add(chckbxkr_1);
		
		JCheckBox chckbxkr_2 = new JCheckBox("20-30.000kr");
		chckbxkr_2.setBounds(8, 124, 113, 25);
		advancedSearchPanel.add(chckbxkr_2);
		
		JCheckBox chckbxKr = new JCheckBox("30.000+ kr");
		chckbxKr.setBounds(8, 158, 113, 25);
		advancedSearchPanel.add(chckbxKr);
	}

	public void setVisible(boolean state){
		frame.setVisible(state);
		contentPane.setVisible(state);
		//comboStartMonth.setVisible(state);
		//lblNewLabel.setVisible(true);
		//lblNewLabel.setText("Show me now");
		//comboStartDay.setVisible(state);
		contentPane.repaint();	
	}
	
	public void addHotelManager(HotelManager hm)
	{
		this.hotelManager = hm;
		/*System.out.println("We're in addHotelManager. We've copied hm to hotelManager."
				+"The number of hotels in hm is "+hm.hotelCount()+". The number of hotels"
				+" in this.hotelManager is "+hotelManager.hotelCount());
				*/
	}
	
	private void gatherSelectedOptions(){
		searchChoices.clear();
		startDate.set(comboStartYear.getSelectedIndex(), comboStartMonth.getSelectedIndex(), comboStartDay.getSelectedIndex());
		
		//comboStartDay
		/*private Date startDate;
	private Date endDate;
	private int numberOfGuests;
	private boolean[] priceRange = new boolean[5];*/
	}

	private void testSearchFunction(){
		ArrayList searchParameters = new ArrayList(16);
		ArrayList<Hotel> searchResults;
		LocalDate startDate = LocalDate.of(2017, 06, 02);
		LocalDate endDate = LocalDate.of(2017, 06, 04);
		int guests = 10;
		
		//Let's search for...
		//Any hotel at all, for 10 people, on June 2-3rd.
		//searchResults = new ArrayList<Hotel>(hotelManager.searchHotel(startDate, endDate, guests));

		searchParameters.add(startDate);
		searchParameters.add(endDate);
		searchParameters.add(guests);
		for(int i = 3; i<16; i++)
			searchParameters.add(null);
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
		 * */
		
		searchResults = new ArrayList<Hotel>(hotelManager.searchHotel(searchParameters));
		//searchResults = new ArrayList<Hotel>(hotelManager.searchHotel(startDate, endDate, guests));
		//System.out.println("Size of searchResults is "+searchResults.size());
		for(Hotel h : searchResults)
			System.out.println("Found hotel "+(String)(h.getName()));

		
		
		/*
		//Part of working code, commenting it out
		//while we test DB functionality
		gatherSelectedOptions();
		searchChoices.clear();
		searchChoices.add(comboEndYear.getSelectedItem());
		
		searchResults = hotelManager.searchHotel(searchChoices);
		*/
		
	}

}
