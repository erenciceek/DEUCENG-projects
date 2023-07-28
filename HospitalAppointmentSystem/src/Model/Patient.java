package Model;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Patient extends User{
	
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public Patient(String Id,String name,String surname,String password,String sex) {
		super(Id,name,surname,password,sex);
	}
	
	public Patient() {
		
	}
	
	
	public boolean register(String id, String name, String surname,String password,String sex) {
		int key = 0;
		boolean duplicate = false;
		String query = "INSERT INTO patient" + "(id,name,surname,password,sex) VALUES" + " (?,?,?,?,?)";
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM patient WHERE id='" + id + "'");
			while (rs.next()) {
				duplicate = true;
				System.out.println("There is a patient with this id in the system.");
				break;
			}
			if (!duplicate) {

				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, id);
				preparedStatement.setString(2, name);
				preparedStatement.setString(3, surname);
				preparedStatement.setString(4, password);
				preparedStatement.setString(5, sex);

				preparedStatement.executeUpdate();
				key = 1;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Appointment> getAppointmentList_p(Patient patient) throws SQLException {
		ArrayList<Appointment> list = new ArrayList<>();
		Appointment obj;

		try {
			Connection con = dbConnection.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM Appointment WHERE patient_id = '" + patient.getId() + "'");
	
			while (rs.next()) {
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctorID(rs.getString("doctor_id"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setDoctorSurname(rs.getString("doctor_surname"));
				obj.setPatientID(rs.getString("patient_id"));
				obj.setPatientName(rs.getString("patient_name"));
				obj.setPatientSurname(rs.getString("patient_surname"));
				obj.setAppDate(rs.getString("app_date"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
		}


		return list;
	}
	
	
	
	
	public boolean addAppointment(String doctor_id, String doctor_name,String doctor_surname,
			String patient_id, String patient_name,String patient_surname,String appDate) {
		
		Connection con = conn.connDb();
		int key = 0;
		String query = "INSERT INTO Appointment" + "(doctor_id,doctor_name,doctor_surname,patient_id,patient_name,"
				+ "patient_surname,app_date) VALUES"
				+ " (?,?,?,?,?,?,?)";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, doctor_id);
			preparedStatement.setString(2, doctor_name);
			preparedStatement.setString(3, doctor_surname);
			preparedStatement.setString(4, patient_id);
			preparedStatement.setString(5, patient_name);
			preparedStatement.setString(6, patient_surname);
			preparedStatement.setString(7, appDate);

			preparedStatement.executeUpdate();
			key = 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1) {
			return true;
		} else {
			return false;
		}
		

	}
	
	
	public boolean deleteAppointment(int app_id,String date) {
		Connection con = conn.connDb();
		int key = 0;
		String doctor_id = getDoctorID(app_id);

		try {
			st = con.createStatement();
			String query1 = "DELETE FROM Appointment WHERE id = "+ app_id;
			String query2 = "UPDATE workhour SET status = 'a' WHERE doctor_id = '" + doctor_id + "' AND wdate = '" + date + "'";

			preparedStatement = con.prepareStatement(query1);
			preparedStatement.executeUpdate();

			preparedStatement = con.prepareStatement(query2);
			preparedStatement.executeUpdate();
			key = 1;
			
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (key == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	
	private String getDoctorID(int app_id) {
		Connection con = conn.connDb();
		String str = "";
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT doctor_id FROM Appointment WHERE id = "+ app_id);				
			str = rs.getString("doctor_id");
			
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	
	public boolean updateWhourStatus(String doctor_id, String wdate) {
		int key = 0;
		String query = "UPDATE workhour SET status = ? WHERE doctor_id = ? AND wdate = ?";
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, "na");
			preparedStatement.setString(2, doctor_id);
			preparedStatement.setString(3, wdate);
			preparedStatement.executeUpdate();
			key = 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1) {
			return true;
		} else {
			return false;
		}
	}
	


}