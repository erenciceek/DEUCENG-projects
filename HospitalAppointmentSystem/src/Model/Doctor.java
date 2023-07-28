package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.*;

public class Doctor extends User {
	private String clinic;
	private String hospital;
	
	@SuppressWarnings("static-access")
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	
	
	public Doctor(String ID,String name,String surname,String password,String sex,String clinic,String hospital) {
		super(ID,name,surname,password,sex);
		this.clinic = clinic;
		this.hospital = hospital;

		
		
	}
	
	public Doctor() {
		
	}

	
	public boolean addWhour(WorkHour whour) {
		int key = 0;
		int count = 0;
		String query = "INSERT INTO workhour" + "(doctor_id,doctor_name,doctor_surname,wdate,status) VALUES" + "(?,?,?,?,?)"; 
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM workhour WHERE status='a' AND doctor_id = '" + whour.getDoctor_id()+ "' AND wdate = '" + whour.getWdate()+ "'");
			while(rs.next()) {
				
				count++;
				rs.close();
				break;
			}
			if(count == 0) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1,whour.getDoctor_id() );
				preparedStatement.setString(2, whour.getDoctor_name());
				preparedStatement.setString(3, whour.getDoctor_surname());
				preparedStatement.setString(4, whour.getWdate());
				preparedStatement.setString(5, whour.getStatus());
				
				preparedStatement.executeUpdate();
				key = 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(key == 1)
			return true;
		else
			return false;
		
	}
	public boolean deleteWhour(int id) throws SQLException{
		
		int key = 0;
		String query = "DELETE FROM workhour WHERE id = " + String.valueOf(id);
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.executeUpdate();
			key = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else 
			return false;
	}
	
	

	
	
	
	@SuppressWarnings("static-access")
	public ArrayList<WorkHour> getWhourList() throws SQLException{
		String currDateTime = String.valueOf(LocalDateTime.now());
		LocalDateTime dateTime = LocalDateTime.parse(currDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String currentDateTime = dateTime.format(formatter);
        LocalDateTime currentDate = LocalDateTime.parse(currentDateTime, formatter);
		ArrayList<WorkHour> list = new ArrayList<>();
		WorkHour obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM workhour WHERE status = 'a' AND doctor_id = '" + getId() + "'");
			
			while (rs.next()) {
				String workhourDate = rs.getString("wdate");
				LocalDateTime date = LocalDateTime.parse(workhourDate, formatter);
				if(date.isBefore(currentDate) || date.isEqual(currentDate)) {
					continue;
				}else {
					obj = new WorkHour();
					obj.setId(rs.getInt ("id"));
					obj.setDoctor_id(rs.getString("doctor_id"));
					obj.setDoctor_name(rs.getString("doctor_name"));
					obj.setStatus(rs.getString("status"));
					obj.setWdate(rs.getString("wdate"));				
					list.add(obj);
				}
			}
			rs.close();
			st.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	

	
	@SuppressWarnings("static-access")
	public boolean deleteAppointment(int app_id,String date) {
		Connection con = conn.connDb();
		int key = 0;
		try {
			st = con.createStatement();
			String query1 = "DELETE FROM Appointment WHERE id = "+ app_id;
			String query2 = "UPDATE workhour SET status = 'a' WHERE doctor_id = '" + getId() + "' AND wdate = '" + date + "'";

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
	
	
	
	
	@SuppressWarnings("static-access")
	public  ArrayList<Appointment> getAppointmentList_d(Doctor doctor) throws SQLException {
		ArrayList<Appointment> list = new ArrayList<>();
		Appointment obj;
		
		try {
			Connection con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM Appointment WHERE doctor_id = '" + doctor.getId() + "'");
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
	
	

	public String getClinic() {
		return clinic;
	}

	public void setClinic(String clinic) {
		this.clinic = clinic;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
}