package com.sms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static final String URL = "jdbc:mysql://localhost:3306/sms_db";
	private static final String USER_ID = "root";
	private static final String PASSWORD = "your_password_here";

	public static Connection getConnection() throws SQLException 
	{
		Connection con=DriverManager.getConnection(URL,USER_ID,PASSWORD);
		System.out.println("Database Connected Successully!");
		return con;
	}
}
