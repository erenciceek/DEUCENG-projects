package Model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class WorkHour {
	private int id;
	private String doctor_id;
	private String doctor_name,doctor_surname, wdate, status;
	dbConnection conn = new dbConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	
	public WorkHour(String doctor_id, String doctor_name,String doctor_surname,String wdate, String status) {
		this.doctor_id = doctor_id;
		this.doctor_name = doctor_name;
		this.doctor_surname = doctor_surname;
		this.wdate = wdate;
		this.status = status;
	}
	

	public WorkHour() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<WorkHour> getWhourList(String doctor_id) throws SQLException {
		ArrayList<WorkHour> list = new ArrayList<>();

		WorkHour obj;
		try {
			Connection con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM workhour WHERE status = 'a' AND doctor_id = '" + doctor_id + "'");

			while (rs.next()) {
				obj = new WorkHour();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getString("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setStatus(rs.getString("status"));
				obj.setWdate(rs.getString("wdate"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	public String getDoctor_surname() {
		return doctor_surname;
	}


	public void setDoctor_surname(String doctor_surname) {
		this.doctor_surname = doctor_surname;
	}
}
