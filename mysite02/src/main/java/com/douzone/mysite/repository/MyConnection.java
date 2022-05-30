package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
	
	
	public Connection getConnection() throws SQLException {
		Connection connection = null;
		String ip = "192.168.10.33:3306";
		String schema="webdb";
		String id="webdb";
		String password="webdb";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			
			String url = "jdbc:mysql://"+ip+"/"+schema+"?charset=utf8";
			connection = DriverManager.getConnection(url, id, password);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return connection;
	}
	
}
