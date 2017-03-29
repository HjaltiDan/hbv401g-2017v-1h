package view;
import control.*;
import java.util.Observable;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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


public class ResultsView extends Observable {

	private HotelManager hm;
	
	private JFrame frame;
	private JPanel contentPane;
	


	/**
	 * Create the frame.
	 */
	public ResultsView() {
		frame = new JFrame();
		frame.setBounds(100, 100, 619, 455);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 583, 180);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNameOfHotel = new JLabel("Name of the Hotel");
		lblNameOfHotel.setBounds(10, 11, 97, 14);
		panel.add(lblNameOfHotel);
		
		JButton btnReserveARoom = new JButton("Reserve a Room");
		btnReserveARoom.setBounds(462, 146, 111, 23);
		panel.add(btnReserveARoom);
	}

	public void addHotelManager(HotelManager hm)
	{
		this.hm = hm;
	}
}
