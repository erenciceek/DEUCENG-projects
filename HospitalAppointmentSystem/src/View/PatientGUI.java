package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.*;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.lang.*;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.DefaultComboBoxModel;

public class PatientGUI extends JFrame {

	private JPanel w_pane;
	private static Patient patient = new Patient();

	private JTable table_doctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable table_whour;
	private WorkHour whour = new WorkHour();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private String selectDoctorID = null;
	private String selectDoctorName = null;
	private String selectDoctorSurname = null;
	private JTable table_appoint;
	private DefaultTableModel appointModel;
	private Object[] appointData = null;

	

	private JPopupMenu appointMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientGUI frame = new PatientGUI(patient);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public PatientGUI(Patient patient) throws SQLException {

		doctorModel = new DefaultTableModel();
		Object[] colDoctor = new Object[3];
		colDoctor[0] = "ID";
		colDoctor[1] = "Name";
		colDoctor[2] = "Surname";
		doctorModel.setColumnIdentifiers(colDoctor);
		doctorData = new Object[3];

		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Date";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];

		appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "ID";
		colAppoint[1] = "Doctor";
		colAppoint[2] = "Date";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[3];
		ArrayList<Appointment> app_list_p = patient.getAppointmentList_p(patient);
		for (int i = 0; i < app_list_p.size(); i++) {
			appointData[0] = app_list_p.get(i).getId();
			appointData[1] = app_list_p.get(i).getDoctorName();
			appointData[2] = app_list_p.get(i).getAppDate();
			appointModel.addRow(appointData);
		}

		setTitle("Hospital Appointment System");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 713, 463);
		w_pane = new JPanel();
		w_pane.setBackground(UIManager.getColor("Menu.background"));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblWelcome = new JLabel("Welcome " + patient.getName() + " " + patient.getSurname());
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblWelcome.setBounds(15, 17, 278, 21);
		w_pane.add(lblWelcome);

		JButton btnExt = new JButton("EXIT");

		btnExt.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnExt.setBounds(595, 12, 89, 25);
		w_pane.add(btnExt);
		btnExt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI l_gui = new LoginGUI();
				l_gui.setVisible(true);
				dispose();
			}
		});

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setFont(new Font("Tahoma", Font.BOLD, 14));
		w_tab.setBounds(10, 61, 674, 349);
		w_pane.add(w_tab);

		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(UIManager.getColor("Menu.background"));
		w_tab.addTab("Make an Appointment", null, w_appointment, null);
		w_appointment.setLayout(null);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 23, 239, 284);
		w_appointment.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);

		JLabel doctor_list_lbl = new JLabel("Doctor List");
		doctor_list_lbl.setBounds(10, 0, 126, 25);
		doctor_list_lbl.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		w_appointment.add(doctor_list_lbl);

		JLabel clinic_lbl = new JLabel("Clinic");
		clinic_lbl.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		clinic_lbl.setBounds(262, 17, 126, 25);
		w_appointment.add(clinic_lbl);

		JComboBox select_clinic = new JComboBox();
		select_clinic.setModel(new DefaultComboBoxModel(new String[] { "Select a Clinic" }));
		select_clinic.setBounds(262, 43, 150, 22);
		for (int i = 0; i < patient.getClinicList().size(); i++) {
			select_clinic.addItem(patient.getClinicList().get(i));
		}

		w_appointment.add(select_clinic);

		JLabel hospital_lbl = new JLabel("Hospital");
		hospital_lbl.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		hospital_lbl.setBounds(262, 72, 126, 25);
		w_appointment.add(hospital_lbl);

		JComboBox select_Hospital = new JComboBox();
		select_Hospital.setModel(new DefaultComboBoxModel(new String[] { "Select a Hospital" }));
		select_Hospital.setBounds(262, 96, 150, 22);

		for (int i = 0; i < patient.getHospitalList().size(); i++) {
			select_Hospital.addItem(patient.getHospitalList().get(i));
		}

		w_appointment.add(select_Hospital);

		select_Hospital.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_clinic.getSelectedIndex() == 0 && select_Hospital.getSelectedIndex() != 0) {
					String hospital_name = String.valueOf(select_Hospital.getSelectedItem());
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);

					try {
						ArrayList<User> list = patient.getHospitalDoctorList(hospital_name);

						if (list.size() == 0)
							JOptionPane.showMessageDialog(null,
									"No doctor found matching the selected criteria. "
											+ "Please choose again or try again later.",
									"WARNING", JOptionPane.INFORMATION_MESSAGE);
						for (int i = 0; i < list.size(); i++) {
							doctorData[0] = list.get(i).getId();
							doctorData[1] = list.get(i).getName();
							doctorData[2] = list.get(i).getSurname();
							doctorModel.addRow(doctorData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else if (select_clinic.getSelectedIndex() != 0 && select_Hospital.getSelectedIndex() != 0) {

					String clinic_name = String.valueOf(select_clinic.getSelectedItem());
					String hospital_name = String.valueOf(select_Hospital.getSelectedItem());

					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);

					try {
						ArrayList<User> list = patient.getDoctorList_h_c(hospital_name, clinic_name);

						if (list.size() == 0)
							JOptionPane.showMessageDialog(null,
									"No doctor found matching the selected criteria. "
											+ "Please choose again or try again later.",
									"WARNING", JOptionPane.INFORMATION_MESSAGE);

						for (int i = 0; i < list.size(); i++) {
							doctorData[0] = list.get(i).getId();
							doctorData[1] = list.get(i).getName();
							doctorData[2] = list.get(i).getSurname();
							doctorModel.addRow(doctorData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else if (select_clinic.getSelectedIndex() != 0 && select_Hospital.getSelectedIndex() == 0) {
					String clinic_name = String.valueOf(select_clinic.getSelectedItem());
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);

					try {
						ArrayList<User> list = patient.getClinicDoctorList(clinic_name);

						if (list.size() == 0)
							JOptionPane.showMessageDialog(null,
									"No doctor found matching the selected criteria. "
											+ "Please choose again or try again later.",
									"WARNING", JOptionPane.INFORMATION_MESSAGE);

						for (int i = 0; i < list.size(); i++) {
							doctorData[0] = list.get(i).getId();
							doctorData[1] = list.get(i).getName();
							doctorData[2] = list.get(i).getSurname();
							doctorModel.addRow(doctorData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
				}

			}

		});

		select_clinic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_clinic.getSelectedIndex() != 0 && select_Hospital.getSelectedIndex() == 0) {
					String clinic_name = String.valueOf(select_clinic.getSelectedItem());
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);

					try {
						ArrayList<User> list = patient.getClinicDoctorList(clinic_name);

						if (list.size() == 0)
							JOptionPane.showMessageDialog(null,
									"No doctor found matching the selected criteria. "
											+ "Please choose again or try again later.",
									"WARNING", JOptionPane.INFORMATION_MESSAGE);

						for (int i = 0; i < list.size(); i++) {
							doctorData[0] = list.get(i).getId();
							doctorData[1] = list.get(i).getName();
							doctorData[2] = list.get(i).getSurname();
							doctorModel.addRow(doctorData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else if (select_clinic.getSelectedIndex() != 0 && select_Hospital.getSelectedIndex() != 0) {

					String clinic_name = String.valueOf(select_clinic.getSelectedItem());
					String hospital_name = String.valueOf(select_Hospital.getSelectedItem());

					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);

					try {
						ArrayList<User> list = patient.getDoctorList_h_c(hospital_name, clinic_name);

						if (list.size() == 0)
							JOptionPane.showMessageDialog(null,
									"No doctor found matching the selected criteria. "
											+ "Please choose again or try again later.",
									"WARNING", JOptionPane.INFORMATION_MESSAGE);

						for (int i = 0; i < list.size(); i++) {
							doctorData[0] = list.get(i).getId();
							doctorData[1] = list.get(i).getName();
							doctorData[2] = list.get(i).getSurname();
							doctorModel.addRow(doctorData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else if (select_clinic.getSelectedIndex() == 0 && select_Hospital.getSelectedIndex() != 0) {
					String hospital_name = String.valueOf(select_Hospital.getSelectedItem());
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);

					try {
						ArrayList<User> list = patient.getHospitalDoctorList(hospital_name);

						if (list.size() == 0)
							JOptionPane.showMessageDialog(null,
									"No doctor found matching the selected criteria. "
											+ "Please choose again or try again later.",
									"WARNING", JOptionPane.INFORMATION_MESSAGE);

						for (int i = 0; i < list.size(); i++) {
							doctorData[0] = list.get(i).getId();
							doctorData[1] = list.get(i).getName();
							doctorData[2] = list.get(i).getSurname();
							doctorModel.addRow(doctorData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
				}

			}

		});

		JLabel lbldoctorsec = new JLabel("Select Doctor");
		lbldoctorsec.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lbldoctorsec.setBounds(267, 171, 129, 14);
		w_appointment.add(lbldoctorsec);

		JButton btn_selDoc = new JButton("Select");
		btn_selDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table_whour.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null,
							"A suitable date has not been found. Please make a choice again or try again later",
							"WARNING", JOptionPane.INFORMATION_MESSAGE);
				}
				int row = table_doctor.getSelectedRow();
				if (row >= 0) {
					String value = table_doctor.getModel().getValueAt(row, 0).toString();
					DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0);

					try {
						ArrayList<WorkHour> list = whour.getWhourList(value);
						for (int i = 0; i < list.size(); i++) {
							whourData[0] = list.get(i).getId();
							whourData[1] = list.get(i).getWdate();
							whourModel.addRow(whourData);

						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table_whour.setModel(whourModel);
					selectDoctorID = value;
					selectDoctorName = table_doctor.getModel().getValueAt(row, 1).toString();
					selectDoctorSurname = table_doctor.getModel().getValueAt(row, 2).toString();

				} else {
					JOptionPane.showMessageDialog(null, "Please select a doctor!", "Warning",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btn_selDoc.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_selDoc.setBounds(267, 191, 129, 23);
		w_appointment.add(btn_selDoc);

		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(420, 23, 239, 284);
		w_appointment.add(w_scrollWhour);

		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		table_whour.getColumnModel().getColumn(0).setPreferredWidth(5);

		JLabel lbl_hours = new JLabel("Available Hours");
		lbl_hours.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_hours.setBounds(420, 0, 126, 25);
		w_appointment.add(lbl_hours);

		JButton btn_addAppoint = new JButton("OK");
		btn_addAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				
				if (selRow >= 0) {
					String date = table_whour.getModel().getValueAt(selRow, 1).toString();
					try {
						boolean control = patient.addAppointment(selectDoctorID, selectDoctorName, selectDoctorSurname,
								patient.getId(), patient.getName(), patient.getSurname(), date);
						if (control) {
							JOptionPane.showMessageDialog(null, "Appointment created successfully!");
							patient.updateWhourStatus(selectDoctorID, date);
							updateWhourModel(selectDoctorID);
							updateAppointModel(patient);

						} else {
							JOptionPane.showMessageDialog(null, "Appointment could not be created, try again!",
									"WARNING", JOptionPane.ERROR_MESSAGE);
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a date!", "WARNING",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		btn_addAppoint.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_addAppoint.setBounds(267, 224, 129, 23);
		w_appointment.add(btn_addAppoint);

		JButton clear_btn = new JButton("Clear All");
		clear_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select_Hospital.setSelectedIndex(0);
				select_clinic.setSelectedIndex(0);
				DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
				clearModel.setRowCount(0);
			}
		});

		clear_btn.setFont(new Font("Tahoma", Font.BOLD, 14));
		clear_btn.setBounds(267, 258, 129, 23);
		w_appointment.add(clear_btn);

		JPanel w_appoint = new JPanel();
		w_appoint.setBackground(new Color(255, 255, 255));
		w_tab.addTab("My Appointments", null, w_appoint, null);
		w_appoint.setLayout(null);

		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 11, 550, 296);
		w_appoint.add(w_scrollAppoint);

		appointMenu = new JPopupMenu();
		JMenuItem deleteMenu = new JMenuItem("Delete");
		appointMenu.add(deleteMenu);

		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String selDate = (String) table_appoint.getValueAt(table_appoint.getSelectedRow(), 2);
					String selAppointment = table_appoint.getValueAt(table_appoint.getSelectedRow(), 0).toString();
					int selAppID = Integer.parseInt(selAppointment);
					patient.deleteAppointment(selAppID, selDate);
					updateAppointModel(patient);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		table_appoint = new JTable(appointModel);
		table_appoint.setComponentPopupMenu(appointMenu);
		table_appoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				try {
					int selectedRow = table_appoint.rowAtPoint(point);
					table_appoint.setRowSelectionInterval(selectedRow, selectedRow);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		w_scrollAppoint.setViewportView(table_appoint);

		JButton patient_apt_delBttn = new JButton("Delete");
		patient_apt_delBttn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String selAppointment = table_appoint.getModel().getValueAt(table_appoint.getSelectedRow(), 0)
							.toString();
					int selAppID = Integer.parseInt(selAppointment);
					String selDate = (String) table_appoint.getModel().getValueAt(table_appoint.getSelectedRow(), 2);
					patient.deleteAppointment(selAppID, selDate);
					updateAppointModel(patient);

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Please select an appointment to delete.");
				}
			}
		});
		patient_apt_delBttn.setBounds(570, 11, 89, 23);
		w_appoint.add(patient_apt_delBttn);
	}

	
	
	public void updateAppointModel(Patient patient) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
		clearModel.setRowCount(0);
		ArrayList<Appointment> list = patient.getAppointmentList_p(patient);
		
		try {
			for (int i = 0; i < list.size(); i++) {
				appointData[0] = list.get(i).getId();
				appointData[1] = list.get(i).getDoctorName();
				appointData[2] = list.get(i).getAppDate();
				appointModel.addRow(appointData);

			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "dostum bir şeyleri yanlış yapıyorsun.");
		}
	}
	
	

	public void updateWhourModel(String doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		ArrayList<WorkHour> list = whour.getWhourList(doctor_id);
		for (int i = 0; i < list.size(); i++) {
			whourData[0] = list.get(i).getId();
			whourData[1] = list.get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}

}