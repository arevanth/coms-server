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
		String selectSQL = "SELECT idusers, name, email, type FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coms","root","password");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSQL);
			
			while(rs.next())
			{
				response = new LoginResponse(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return response;
	}
	
	public static boolean saveIp(int userId, String ip)
	{
		if(checkIfAlreadyExists(userId, ip))
			return true;
		
		boolean result = false;
		String insertSQL = "INSERT INTO ip(id,user,ip) VALUES (NULL," + userId + ",'" + ip + "')";
		System.out.println(insertSQL);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coms","root","password");
			Statement stmt = conn.createStatement();
			
			stmt = conn.createStatement();
			stmt.executeUpdate(insertSQL);
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			result = true;	
			
		}
		catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
		}	
		return result;
	}
	
	private static boolean checkIfAlreadyExists(int userId, String ip)
	{
		
		boolean result = false;
		String selectSQL = "SELECT id FROM ip WHERE user = '" + userId + "' AND ip='" + ip + "'";
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coms","root","password");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSQL);
			
			while(rs.next())
			{
				result = true;
			}
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<String> getAllIp(GetIpRequest request)
	{
		List<String> results = new ArrayList<String>();
		
		String selectSQL = "SELECT idusers FROM users WHERE idusers = '" + request.userId + "' AND type=" + request.type;
		
		System.out.println(selectSQL);
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
				String selectIpSQL = "";
				if(request.type.equals("1"))
					selectIpSQL = "SELECT ip FROM ip where user = " + id;
				else
					selectIpSQL = "SELECT ip FROM ip where consent = " + id;
				
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
				String insertSQL = "UPDATE ip SET open=" + request.open +", close=" + request.close + " WHERE user =" + id + " AND ip ='" + request.ip + "'";
				System.out.println(insertSQL);
				stmt.executeUpdate(insertSQL);
				result = true;
			}
			
		}
		
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<String> getEmail(String email)
	{
		String selectSQL = "SELECT email FROM users WHERE email = '" + email + "' AND type = 0";
		List<String> results = new ArrayList<String>();
		
		System.out.println(selectSQL);
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coms","root","password");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSQL);
			
			while(rs.next())
			{
				results.add(rs.getString(1));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return results;
	}
	
	public static boolean giveConsent(ConsentRequest request)
	{
		String selectSQL = "SELECT idusers FROM users WHERE email = '" + request.email + "' AND type = 0";
		String id = "";
		boolean result = false;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coms","root","password");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSQL);
			
			while(rs.next())
			{
				id = rs.getString(1);
			}
			
			String updateSQL = "UPDATE ip SET consent =" + id + " WHERE user =" + request.userId + " AND ip ='" + request.ip + "'";
			stmt.executeUpdate(updateSQL);
			result = true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return result;
	}
	
}
