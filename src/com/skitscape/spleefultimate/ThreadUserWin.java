package com.skitscape.spleefultimate;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.Connection;

public class ThreadUserWin implements Runnable {

	private String userName;
	
	public ThreadUserWin(String uN) {
		this.userName = uN;
	}

	@Override
	public void run() {
		String host = MainConfiguration.sqlDetails.get("host");
		String user = MainConfiguration.sqlDetails.get("user");
		String pass = MainConfiguration.sqlDetails.get("pass");
		String db = MainConfiguration.sqlDetails.get("db");
		
		String url = "jdbc:mysql://" + host + "/" + db;
		try {
			Connection con = DriverManager.getConnection(url, user, pass);
			PreparedStatement st = con.prepareStatement("SELECT * FROM spleefLeaderboards WHERE uN=?");
			st.setString(1, user);
			st.executeUpdate();
		} catch (Exception e) {}
	}

}
