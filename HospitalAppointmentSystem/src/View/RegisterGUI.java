package View;
import Model.*;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.SystemColor;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	
	
	private Patient patient = new Patient();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setTitle("Hospital Appointment System");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Patient Registration");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		lblNewLabel.setBounds(28, 24, 152, 41);
		w_pane.add(lblNewLabel);
		
		JLabel name_lbl = new JLabel("Name                    :");
		name_lbl.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		name_lbl.setBounds(28, 122, 130, 28);
		w_pane.add(name_lbl);
		
		JTextField name_txt_field = new JTextField();
		name_txt_field.setBounds(173, 126, 127, 23);
		w_pane.add(name_txt_field);
		name_txt_field.setColumns(10);
		
		JLabel surname_lbl = new JLabel("Surname                : ");
		surname_lbl.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		surname_lbl.setBounds(28, 163, 130, 28);
		w_pane.add(surname_lbl);
		
		JTextField surname_txt_field = new JTextField();
		surname_txt_field.setColumns(10);
		surname_txt_field.setBounds(173, 167, 127, 23);
		w_pane.add(surname_txt_field);
		
		JLabel pswrd_label = new JLabel("Password               :");
		pswrd_label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		pswrd_label.setBounds(28, 208, 130, 28);
		w_pane.add(pswrd_label);
		
		JLabel id_label = new JLabel("Id                          :");
		id_label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		id_label.setBounds(28, 83, 130, 28);
		w_pane.add(id_label);
		
		JPasswordField password_field = new JPasswordField();
		password_field.setBounds(173, 212, 127, 23);
		w_pane.add(password_field);
		
		
		
		
		JRadioButton male_button = new JRadioButton("M");
		male_button.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		male_button.setHorizontalAlignment(SwingConstants.CENTER);
		male_button.setBounds(163, 312, 61, 23);
		w_pane.add(male_button);
		
		
		
		JRadioButton female_button = new JRadioButton("F");
		female_button.setHorizontalAlignment(SwingConstants.CENTER);
		female_button.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		female_button.setBounds(239, 312, 61, 23);
		w_pane.add(female_button);
		
		
		ButtonGroup group = new ButtonGroup();
		group.add(female_button);
		group.add(male_button);
		
		JTextField id_txt_field = new JTextField();
		id_txt_field.setColumns(10);
		id_txt_field.setBounds(173, 87, 127, 23);
		w_pane.add(id_txt_field);
		
		JLabel sex_lbl = new JLabel("Sex");
		sex_lbl.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		sex_lbl.setBounds(219, 293, 45, 13);
		w_pane.add(sex_lbl);
		
		JCheckBox show_pswrd_check = new JCheckBox("show password");
		show_pswrd_check.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 10));
		show_pswrd_check.setBounds(306, 214, 93, 21);
		w_pane.add(show_pswrd_check);
		show_pswrd_check.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(show_pswrd_check.isSelected()) 
					password_field.setEchoChar((char)0);
				else
					password_field.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
			}
			
		});
		
		
		
		JButton exit_btn = new JButton("EXIT");
		exit_btn.setBackground(UIManager.getColor("Button.background"));
		exit_btn.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		exit_btn.setBounds(400, 10, 80, 22);
		w_pane.add(exit_btn);
		
			
		
		exit_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LoginGUI l_gui = new LoginGUI();
				l_gui.setVisible(true);
				dispose();
			}
			
		});
		
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setBackground(UIManager.getColor("Button.background"));
		btnSignUp.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btnSignUp.setBounds(375, 269, 100, 28);
		w_pane.add(btnSignUp);
		
		JButton clear_button_1 = new JButton("Clear All");
		clear_button_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		clear_button_1.setBackground(SystemColor.menu);
		clear_button_1.setBounds(375, 310, 100, 28);
		w_pane.add(clear_button_1);
		
		btnSignUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (name_txt_field.getText().length() == 0 || surname_txt_field.getText().length() == 0 || 
						password_field.getText().length() == 0 || id_txt_field.getText().length() == 0 || group.getSelection() == null) {
					
					JOptionPane.showMessageDialog(null,"Please fill in all fields!");
				}
				else {
					String temp_sex = group.getSelection().getActionCommand();
					String sex = (temp_sex == "M") ? "male" : "female"; 
					boolean success = patient.register(id_txt_field.getText(),name_txt_field.getText(),surname_txt_field.getText(),password_field.getText(),sex);
					if (success) {
						JOptionPane.showMessageDialog(null,"Your registration has been completed");
						LoginGUI login = new LoginGUI();
						login.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, JOptionPane.ERROR_MESSAGE);
						
					}
				}
				
					
				
			}
			
		});
		

	}
}
