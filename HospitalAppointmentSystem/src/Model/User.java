package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class User {
	private String Id,name,surname,password,sex;

	dbConnection conn = new dbConnection();

	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	
	public User(String Id,String name,String surname,String password,String sex) {
		this.Id = Id;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.sex= sex;
	}
	
	public User() {
		
	}

	
	public ArrayList<String> getClinicList() throws SQLException {
		ArrayList<String> list = new ArrayList<>();
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT DISTINCT clinic FROM Doctor");
			while (rs.next()) {
				list.add(rs.getString("clinic"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	
	public ArrayList<String> getHospitalList() throws SQLException {
		ArrayList<String> list = new ArrayList<>();
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT DISTINCT hospital FROM Doctor");
			while (rs.next()) {
				list.add(rs.getString("hospital"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		return list;
	}
	
	
	public ArrayList<User> getClinicDoctorList(String clinic_name) throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT id,name,surname,password,sex FROM Doctor WHERE clinic = '"  + clinic_name + "'");
			while (rs.next()) {
				obj = new User(rs.getString("id"),rs.getString("name"),rs.getString("surname"),
						rs.getString("password"),rs.getString("sex"));
				list.add(obj);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	
	public ArrayList<User> getHospitalDoctorList(String hospital_name) throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT id,name,surname,password,sex FROM Doctor WHERE hospital = '"  + hospital_name+ "'");
			while (rs.next()) {
				obj = new User(rs.getString("id"),rs.getString("name"),rs.getString("surname"),
						rs.getString("password"),rs.getString("sex"));
				list.add(obj);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	
	
	
	
	public ArrayList<User> getDoctorList_h_c(String hospital_name,String clinic_name) throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT id,name,surname,password,sex FROM Doctor WHERE clinic = '"  + clinic_name 
					+ "' AND hospital = '" + hospital_name + "'");
			while (rs.next()) {
				obj = new User(rs.getString("id"),rs.getString("name"),rs.getString("surname"),
						rs.getString("password"),rs.getString("sex"));
				list.add(obj);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
	

	
	public String getId() {
		return Id;
	}

	public void setId(String Id) {
		this.Id = Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
