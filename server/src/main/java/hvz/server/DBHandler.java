package hvz.server;
import java.sql.*;
import java.util.LinkedList;

public class DBHandler{
	public static void main (String [] args) throws SQLException{//for testing
		Connection c = connect();
		addPlayer("kyle", 1, "0owqiejflsdkjf", c);
		addPlayer("ben", 1, "12345", c);
		addPlayer("logan", 1, "0owqiejflsdkjf", c);
		addPlayer("paul", 1, "0owqiejflsdkjf", c);
		addPlayer("sam", 1, "0owqiejflsdkjf", c);
		Player s = getPlayer("12345", c);
		System.out.println("the player with id 12345 is: " + s.username);
	}
	public static Connection connect(){
		try{
		Class.forName("org.sqlite.JDBC");
			System.out.println("Connecting to Database....");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			System.out.println("Connection Successful");
			return conn;
		}
		catch(Exception e){
			System.out.println("Connection failed");
			return null;
		}
	}
	public static void addAdmin(String name,  String feed,  Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into admin values(" + "'" +name + "' " + ", " + feed + ")";
		s.executeUpdate(command);
	}
	public static void addPlayer(String name, int isZombie, String feed,  Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into users values("+ "'" +name + "' " + ", " + feed + ", " + isZombie + ")";
		s.executeUpdate(command);
	}
	public static void removePlayer(String feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "delete from users where feedcode = " + feedCode; 
		s.executeUpdate(command);
	}
	public static void removeAll(Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "delete from user"; 
		s.executeUpdate(command);
	}
	public static void makeZombie(String feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "update users set isZombie = 1 where feedCode = " + feedCode; 
		s.executeUpdate(command);	
	}
	public static void makeHuman(String feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "update users set isZombie = 0 where feedCode = " + feedCode; 
		s.executeUpdate(command);	
	}
	public static void tag(String tagger, String tagged, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into tags values(" + tagger + ", " + tagged + ")";
		s.executeUpdate(command);		
	}
	public static void setPassword(String feedCode, String pswd, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into passwords values(" + feedCode + ", " + "'" + pswd + "'" + ")";
		s.executeUpdate(command);
	}
	public static Admin getAdmin(String feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from admins where feedcode = " + feedCode);
		String name = rs.getString("username");
		String feed = rs.getString("feedCode");
		Admin admin = new Admin(name, feed);
		return admin;
	}
	public static Player getPlayer(String feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from users where feedcode = " + feedCode);
		String name = rs.getString("username");
		String feed = rs.getString("feedCode");
		Player player = new Player(name, feed);
		return player;
	}
	public static String getPassword(int feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from users where feedcode = " + feedCode);
		String pswd = rs.getString("password");
		return pswd;
	}
	public static LinkedList <User> getAllUsers(Connection c)throws SQLException{
		LinkedList<User> users = new LinkedList<User>();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from  users");
		while(rs.next()){
			String username = rs.getString("username");
			String feedcode = rs.getString("feedCode");
			User user = new User(username, feedcode, false);
			users.add(user);
		}
		rs.close();
		s.close();
		return users;
	}
	public static LinkedList <Admin> getAllAdmin(Connection c)throws SQLException{
		LinkedList<Admin> admins = new LinkedList<Admin>();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from  users");
		while(rs.next()){
			String username = rs.getString("username");
			String feedcode = rs.getString("feedCode");
			Admin admin = new Admin(username, feedcode);
			admins.add(admin);
		}
		rs.close();
		s.close();
		return admins;
	}
}