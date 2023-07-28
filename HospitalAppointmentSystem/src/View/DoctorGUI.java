package View;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;

import Model.*;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class DoctorGUI extends JFrame {

	static Doctor doctor = new Doctor();
	private JPanel w_pane;
	private JTable table_whour;
	private JTable table_doctorAppoint;
	

	private DefaultTableModel whour_model;
	private DefaultTableModel appointModel;
	private Object[] whour_data = null;
	private Object[] d_appointData = null;
	private JPopupMenu d_appointMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DoctorGUI(Doctor doctor) {
		String currDateTime = String.valueOf(LocalDateTime.now());
		LocalDateTime dateTime = LocalDateTime.parse(currDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String currentDateTime = dateTime.format(formatter);
        LocalDateTime currentDate = LocalDateTime.parse(currentDateTime, formatter);



		appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "ID";
		colAppoint[1] = "Patient";
		colAppoint[2] = "Date";
		appointModel.setColumnIdentifiers(colAppoint);
		d_appointData = new Object[3];
		try {
			ArrayList<Appointment> app_list_D = doctor.getAppointmentList_d(doctor);
			for (int i = 0; i < app_list_D.size(); i++) {
				d_appointData[0] = app_list_D.get(i).getId();
				d_appointData[1] = app_list_D.get(i).getPatientName() + " " + app_list_D.get(i).getPatientSurname();
				d_appointData[2] = app_list_D.get(i).getAppDate();
				appointModel.addRow(d_appointData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		whour_model = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Date";
		whour_model.setColumnIdentifiers(colWhour);
		whour_data = new Object[2];

		try {
			ArrayList<WorkHour> workHour_list = doctor.getWhourList();
			for (int i = 0; i < workHour_list.size(); i++) {
				whour_data[0] = workHour_list.get(i).getId();
				whour_data[1] = workHour_list.get(i).getWdate();
				whour_model.addRow(whour_data);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		setTitle("Hospital Appointment System");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);

		JButton exit_btn = new JButton("EXIT");
		exit_btn.setBounds(395, 10, 80, 25);
		exit_btn.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		w_pane.add(exit_btn);
		exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI l_gui = new LoginGUI();
				l_gui.setVisible(true);
				dispose();
			}
		});

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 141, 466, 200);
		w_pane.add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Work Hours", null, panel, null);
		panel.setLayout(null);

		JDateChooser select_date = new JDateChooser();

		select_date.setBounds(6, 6, 130, 20);
		panel.add(select_date);

		JComboBox select_time = new JComboBox();
		select_time.setFont(new Font("Lucida Grande", Font.PLAIN, 8));
		select_time.setModel(new DefaultComboBoxModel(new String[] { "10:00", "10:30", "11:00", "11:30", "12:00",
				"12:30", "13:30", "14:00", "14:30", "15:00", "15:30" }));
		select_time.setBounds(151, 6, 80, 20);
		panel.add(select_time);

		JButton btn_addWhour = new JButton("Add");

		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {
				}
				String time = " " + select_time.getSelectedItem().toString() + ":00";
				String selectDate = date + time;
				LocalDateTime dateTimee = LocalDateTime.parse(selectDate, formatter);
				if (date.length() == 0) {
					JOptionPane.showMessageDialog(null, "Please select a date.");

				} else if (dateTimee.isBefore(currentDate)) {
					JOptionPane.showMessageDialog(null, "Please select a valid date.");

				} else {
					try {
						WorkHour whour = new WorkHour(doctor.getId(), doctor.getName(), doctor.getSurname(), selectDate,
								"a");
						boolean control = doctor.addWhour(whour);
						if (control) {
							updateWhourModel(doctor);
							JOptionPane.showMessageDialog(null, "Added to work hours.");
						} else {
							updateWhourModel(doctor);
							JOptionPane.showMessageDialog(null, "It is already added to work hours.");
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "An error occured. Please try again later.");

					}

				}

			}
		});
		btn_addWhour.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_addWhour.setBounds(243, 6, 80, 22);
		panel.add(btn_addWhour);

		JScrollPane w_scrollwhour = new JScrollPane();
		w_scrollwhour.setBounds(6, 38, 433, 110);
		panel.add(w_scrollwhour);

		table_whour = new JTable(whour_model);
		w_scrollwhour.setViewportView(table_whour);

		JButton btn_deleteWhour = new JButton("Delete");
		btn_deleteWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
					String selWorkhour = table_whour.getModel().getValueAt(selRow, 0).toString();
					int selWorkhourID = Integer.parseInt(selWorkhour);
					try {
						boolean control = doctor.deleteWhour(selWorkhourID);
						if (control) {
							updateWhourModel(doctor);
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "An error occured. Please try again later.");
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a work hour to delete.");

				}

			}
		});
		btn_deleteWhour.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_deleteWhour.setBounds(341, 6, 80, 22);
		panel.add(btn_deleteWhour);

		JPanel doc_apt_panel = new JPanel();
		doc_apt_panel.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("My Appointments", null, doc_apt_panel, null);
		doc_apt_panel.setLayout(null);

		JScrollPane doc_apt_scrollpane = new JScrollPane();
		doc_apt_scrollpane.setBounds(10, 11, 339, 150);
		doc_apt_panel.add(doc_apt_scrollpane);

		JLabel doctor_Logo = new JLabel("");
		if (doctor.getSex().equals("male")) {
			doctor_Logo = new JLabel(new ImageIcon("doctorM.png"));
		} else {
			doctor_Logo = new JLabel(new ImageIcon("doctorW.png"));
		}
		doctor_Logo.setBounds(25, 27, 80, 82);
		w_pane.add(doctor_Logo);

		JLabel doctor_name_label = new JLabel("");
		doctor_name_label.setHorizontalAlignment(SwingConstants.LEFT);
		doctor_name_label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		doctor_name_label.setBounds(138, 30, 220, 22);
		w_pane.add(doctor_name_label);
		doctor_name_label.setText(doctor.getName() + " " + doctor.getSurname());

		JLabel clinic_label = new JLabel("Speciality : " + doctor.getClinic());
		clinic_label.setHorizontalAlignment(SwingConstants.LEFT);
		clinic_label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		clinic_label.setBounds(138, 55, 220, 22);
		w_pane.add(clinic_label);

		JLabel hospital_label = new JLabel("Hospital : " + doctor.getHospital());
		hospital_label.setHorizontalAlignment(SwingConstants.LEFT);
		hospital_label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		hospital_label.setBounds(138, 80, 220, 22);
		w_pane.add(hospital_label);

		d_appointMenu = new JPopupMenu();
		JMenuItem deleteMenu = new JMenuItem("Delete");
		d_appointMenu.add(deleteMenu);

		table_doctorAppoint = new JTable(appointModel);
		table_doctorAppoint.setComponentPopupMenu(d_appointMenu);
		table_doctorAppoint.getColumnModel().getColumn(0).setPreferredWidth(2);
		table_doctorAppoint.getColumnModel().getColumn(1).setPreferredWidth(7);

		table_doctorAppoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				try {
					int selectedRow = table_doctorAppoint.rowAtPoint(point);
					table_doctorAppoint.setRowSelectionInterval(selectedRow, selectedRow);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		doc_apt_scrollpane.setViewportView(table_doctorAppoint);

		JButton doc_apt_deleteButton = new JButton("Delete");
		doc_apt_deleteButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		doc_apt_deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selRow = table_doctorAppoint.getSelectedRow();
				if (selRow >= 0) {
					String selAppointment = table_doctorAppoint.getModel().getValueAt(selRow, 0).toString();
					int selAppID = Integer.parseInt(selAppointment);
					String selDate = table_doctorAppoint.getModel().getValueAt(selRow, 2).toString();

					try {
						boolean control = doctor.deleteAppointment(selAppID, selDate);
						if (control) {
							updateDAppointModel(doctor);
							updateWhourModel(doctor);
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "An error occured. Please try again later.");
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a appointment to delete.");

				}

			}
		});
		doc_apt_deleteButton.setBounds(359, 11, 89, 23);
		doc_apt_panel.add(doc_apt_deleteButton);

	}

	public void updateWhourModel(Doctor doctor) {
		DefaultTableModel clear_model = (DefaultTableModel) table_whour.getModel();
		clear_model.setRowCount(0);
		try {
			ArrayList<WorkHour> whourlist = doctor.getWhourList();
			for (int i = 0; i < whourlist.size(); i++) {
				whour_data[0] = whourlist.get(i).getId();
				whour_data[1] = whourlist.get(i).getWdate();
				whour_model.addRow(whour_data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateDAppointModel(Doctor doctor) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doctorAppoint.getModel();
		clearModel.setRowCount(0);
		try {
			ArrayList<Appointment> app_list = doctor.getAppointmentList_d(doctor);
			for (int i = 0; i < app_list.size(); i++) {
				d_appointData[0] = app_list.get(i).getId();
				d_appointData[1] = app_list.get(i).getPatientName() + " " + app_list.get(i).getPatientSurname();
				d_appointData[2] = app_list.get(i).getAppDate();
				appointModel.addRow(d_appointData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}