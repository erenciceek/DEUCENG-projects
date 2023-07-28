package View;

import java.awt.EventQueue;
import Model.*;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;


public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField doctorId_textField;
	private JTextField patientId_textField;
	private JPasswordField patientPassword_fld;
	private JPasswordField doctorPassword_fld;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setTitle("Hospital Appointment System");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(255, 255, 255));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_logo = new JLabel(new ImageIcon("mhrs.png"));
		lbl_logo.setBounds(212, 21, 70, 70);
		w_pane.add(lbl_logo);
		
		JLabel lblNewLabel = new JLabel("Welcome to the hospital appointment system!");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		lblNewLabel.setBounds(71, 101, 371, 20);
		w_pane.add(lblNewLabel);
		
		JTabbedPane w_tabbedpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabbedpane.setBounds(10, 144, 466, 209);
		w_pane.add(w_tabbedpane);
		
		JPanel loginDoctor = new JPanel();
		loginDoctor.setBackground(new Color(255, 255, 255));
		w_tabbedpane.addTab("Patient Login", null, loginDoctor, null);
		loginDoctor.setLayout(null);
		
		JButton patientSign_btn = new JButton("Sign Up");
		patientSign_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		
		patientSign_btn.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		patientSign_btn.setBackground(UIManager.getColor("Button.shadow"));
		patientSign_btn.setBounds(41, 121, 168, 40);
		loginDoctor.add(patientSign_btn);
		
		JLabel lbl_ID_P = new JLabel("Your ID    : ");
		lbl_ID_P.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		lbl_ID_P.setBackground(Color.WHITE);
		lbl_ID_P.setBounds(21, 30, 95, 24);
		loginDoctor.add(lbl_ID_P);
		
		patientId_textField = new JTextField();
		patientId_textField.setColumns(10);
		patientId_textField.setBackground(Color.WHITE);
		patientId_textField.setBounds(141, 30, 187, 28);
		loginDoctor.add(patientId_textField);
		
		JLabel lbl_password_P = new JLabel("Password : ");
		lbl_password_P.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		lbl_password_P.setBackground(Color.WHITE);
		lbl_password_P.setBounds(21, 76, 95, 24);
		loginDoctor.add(lbl_password_P);
		
		
		patientPassword_fld = new JPasswordField();
		patientPassword_fld.setBounds(141, 76, 187, 28);
		loginDoctor.add(patientPassword_fld);
		
		
	
		JButton patientLog_btn = new JButton("Log In");
		patientLog_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(patientId_textField.getText().length() == 0 || patientPassword_fld.getText().length() == 0) {
					JOptionPane.showMessageDialog(null,"Please fill in all fields!");
				}
				else {
					try {
						Connection c = dbConnection.connDb();
						Statement st = c.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM patient");
						boolean isCorrect = false;
						while(rs.next()) {
							if(isCorrect == false && patientId_textField.getText().equals(rs.getString("ID")) && patientPassword_fld.getText().equals(rs.getString("password"))) {
								Patient patient = new Patient();
								patient.setId(rs.getString("ID"));
								patient.setName(rs.getString("name"));
								patient.setSurname(rs.getString("surname"));
								patient.setPassword(rs.getString("password")); 
								patient.setSex(rs.getString("sex"));
						
								
								PatientGUI pGUI = new PatientGUI(patient);
								pGUI.setVisible(true);
								dispose();
								rs.close();
								st.close();
								isCorrect = true;
								break;
							}
							
						}
						if(isCorrect == false) {
							JOptionPane.showMessageDialog(null,"The user with the entered data could not be found.");
						}
					}catch(SQLException e1) {
						e1.printStackTrace();
					}
				}
					
			
				
			}
		});
		patientLog_btn.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		patientLog_btn.setBackground(UIManager.getColor("Button.shadow"));
		patientLog_btn.setBounds(252, 121, 168, 40);
		loginDoctor.add(patientLog_btn);
		
		
		
		JPanel loginPatient = new JPanel();
		loginPatient.setBackground(new Color(255, 255, 255));
		w_tabbedpane.addTab("Doctor Login", null, loginPatient, null);
		loginPatient.setLayout(null);
		
		JLabel lbl_ID_D = new JLabel("Your ID    : ");
		lbl_ID_D.setBackground(new Color(255, 255, 255));
		lbl_ID_D.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		lbl_ID_D.setBounds(21, 28, 95, 24);
		loginPatient.add(lbl_ID_D);
		
		JLabel lbl_password_D = new JLabel("Password : ");
		lbl_password_D.setBackground(new Color(255, 255, 255));
		lbl_password_D.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 17));
		lbl_password_D.setBounds(21, 76, 95, 24);
		loginPatient.add(lbl_password_D);
		
		doctorId_textField = new JTextField();
		doctorId_textField.setBackground(new Color(255, 255, 255));
		doctorId_textField.setBounds(141, 30, 187, 28);
		loginPatient.add(doctorId_textField);
		doctorId_textField.setColumns(10);
		
		JButton doctorLog_btn = new JButton("Log In");
		doctorLog_btn.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		doctorLog_btn.setBackground(UIManager.getColor("Button.shadow"));
		doctorLog_btn.setBounds(41, 121, 380, 40);
		loginPatient.add(doctorLog_btn);
		
		doctorPassword_fld = new JPasswordField();
		doctorPassword_fld.setBounds(141, 76, 187, 28);
		loginPatient.add(doctorPassword_fld);
		doctorLog_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(doctorId_textField.getText().length() == 0 || doctorPassword_fld.getText().length() == 0) {
					JOptionPane.showMessageDialog(null,"Please fill in all fields!");
				}
				else {
					try {
						Connection c = dbConnection.connDb();
						Statement st = c.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM doctor");
						boolean isCorrect = false;
						while(rs.next()) {
							if(isCorrect == false && doctorId_textField.getText().equals(rs.getString("ID")) && doctorPassword_fld.getText().equals(rs.getString("password"))) {
								Doctor doctor = new Doctor();
								doctor.setId(rs.getString("ID"));
								doctor.setName(rs.getString("name"));
								doctor.setSurname(rs.getString("surname"));
								doctor.setPassword(rs.getString("password")); 
								doctor.setSex(rs.getString("sex"));
								doctor.setClinic(rs.getString("clinic"));
								doctor.setHospital(rs.getString("hospital"));
							//	System.out.println(doctor.getName()+"\n" + doctor.getSurname()+"\n"
								//		+ doctor.getId()+"\n" + doctor.getPassword() + "\n"+doctor.getSex());
								
								DoctorGUI dGUI = new DoctorGUI(doctor);
								dGUI.setVisible(true);
								dispose();
								isCorrect = true;
								rs.close();
								break;
							}
							
						}
						if(isCorrect == false) {
							JOptionPane.showMessageDialog(null,"The user with the entered data could not be found.");
						}
					}catch(SQLException e1) {
						e1.printStackTrace();
					}
				}
					
			}
			
		});
	}
}