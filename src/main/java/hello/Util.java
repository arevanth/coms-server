/**
 * 
 */
package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author revanth
 *
 */
public class Util {
	
	public static Long register(String name, String email, String password, String type)
	{
		String insertSQL = "INSERT INTO users VALUES (NULL,'" + name + "','" + email + "','" + password + "'," + type + ")";
		System.out.println(insertSQL);
		Long result = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coms","root","password");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(insertSQL);
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			result = rs.getLong(1);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public static LoginResponse login(String email, String password)
	{
		LoginResponse response = null;
		String selectSQL = "SELECT name, email, type FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coms","root","password");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSQL);
			
			while(rs.next())
			{
				response = new LoginResponse(rs.getString(1),rs.getString(2),rs.getString(3));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return response;
	}
	
	public static Long saveIp(String email, String ip)
	{
		Long result = null;
		String selectSQL = "SELECT idusers FROM users WHERE email = '" + email + "'";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coms","root","password");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSQL);
			int id = 0;
			
			while(rs.next())
			{
				id = rs.getInt(1);
			}
			
			if(id > 0)
			{
				String insertSQL = "INSERT INTO ip(id,user,ip) VALUES (NULL," + id + ",'" + ip + "')";
				System.out.println(insertSQL);
				stmt = conn.createStatement();
				stmt.executeUpdate(insertSQL);
				rs = stmt.getGeneratedKeys();
				rs.next();
				result = rs.getLong(1);
			}
			
			
		}
		catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
		}	
		return result;
	}
	
	public static List<String> getAllIp(String email)
	{
		System.out.println(email);
		List<String> results = new ArrayList<String>();
		String selectSQL = "SELECT idusers FROM users WHERE email = '" + email + "'";
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coms","root","password");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSQL);
			int id = 0;
			
			while(rs.next())
			{
				id = rs.getInt(1);
			}
			
			if(id > 0)
			{
				String selectIpSQL = "SELECT ip FROM ip where user = " + id;
				System.out.println(selectIpSQL);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(selectIpSQL);
				
				while(rs.next())
				{
					results.add(rs.getString(1));
				}
			}
			
			
		}
		catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
		}	
		return results;

	}
	
	public static boolean setCondition(ConditionRequest request)
	{
		boolean result = false;
		
		String selectSQL = "SELECT idusers FROM users WHERE email = '" + request.email + "'";
		
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coms","root","password");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSQL);
			int id = 0;
			
			while(rs.next())
			{
				id = rs.getInt(1);
			}
			if(id > 0)
			{
				String insertSQL = "INSERT INTO ip(open,close) VALUES (" + request.open + "," + request.close + ") WHERE user =" + id + " AND ip ='" + request.ip + "'";
				stmt.executeUpdate(insertSQL);
				rs = stmt.getGeneratedKeys();
				while(rs.next())
					result = true;
			}
			
		}
		
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
}
