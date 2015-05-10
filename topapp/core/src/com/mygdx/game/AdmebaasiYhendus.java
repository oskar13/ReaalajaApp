package com.mygdx.game;
import java.sql.*;

public class AdmebaasiYhendus {


	public AdmebaasiYhendus(){

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:skoorid.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS skoorid " +
					"(ID INTEGER PRIMARY KEY," +
					" nimi           VARCHAR(60)    NOT NULL," +
					" skoor 		INT     NOT NULL)"; 
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Table created successfully");

	}



	public void Lisa(String name, int sk) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:skoorid.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "INSERT INTO skoorid (nimi, skoor) " +
					"VALUES ( '"+ name +"', " + sk + " );"; 
			stmt.executeUpdate(sql);

			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	
	
	
	
	
	public void LoeAndmed() {
		
		 Connection c = null;
		    Statement stmt = null;
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:skoorid.db");
				System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM skoorid;" );
		      while ( rs.next() ) {
		         int skoor = rs.getInt("skoor");
		         String  nimi = rs.getString("nimi");
		        
		         System.out.println( "Skoor = " + skoor );
		         System.out.println( "Nimi = " + nimi );
		         System.out.println();
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Operation done successfully");
		
		
	}



}
