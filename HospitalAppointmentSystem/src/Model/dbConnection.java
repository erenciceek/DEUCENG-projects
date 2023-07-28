package Model;

import java.sql.*;

public class dbConnection {
	static Connection c = null;
	
	
	public static Connection connDb() {
		String url = "jdbc:sqlite:C://sqlite_db/hospital.db";
		try {
			c = DriverManager.getConnection(url);
			return c;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return c;
	}
}	
