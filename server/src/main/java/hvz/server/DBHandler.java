package hvz.server;
import java.io.File;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

public class DBHandler{
	
	public static Connection init(){
		String fileName = "database";
		Properties props = System.getProperties();
		props.setProperty("sqlite.purejava", "true");
		//check to see if folder exists
		File folder = new File("db/");
		if (!folder.exists() || !folder.isDirectory()){
			folder.mkdir();
		}
		
		//See if file already exists or if it needs to be created
		File f = new File("db/" + fileName + ".db");
		if (!f.exists()){
			//Create table from scratch
			Connection c = connect();
			createTable(c);
			System.out.println("First time database set up complete");
			return c;
		}
		else {
			Connection c = connect();
			System.out.println("database loaded");
			return c;
		}
	}
	//game stats end time if game started or not 
	public static void createTable(Connection c) {
		String command = "CREATE TABLE users " + 
				"(username		varchar(25), " + 
				"feedCode 		varchar(25)," + 
				"isZombie	 	int)";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		command = "CREATE TABLE gameStats" + 
				"(endTime 	varchar(25), " +
				"hasBegun	int)";		//1 for true, 0 for false
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		try{
			Statement s = c.createStatement();
			command = "insert into gameStats values('initialEndDate', 0)";
			s.execute(command);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		command = "CREATE TABLE passwords " + 
				"(feedCode		varchar(25), " + 
				"password 		varchar(25))";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		command = "CREATE TABLE tags" + 
				"(tagger		varchar(25), " + 
				"tagged 		varchar(25))";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		command = "CREATE TABLE admins " + 
				"(username		varchar(25), " + 
				"feedCode 		varchar(25))";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}

	}
	
	public static void main (String [] args) throws SQLException{//for testing
		Connection c = connect();
		addPlayer("kyle", 1, "0owqiejflsdkjf", c);
		addPlayer("ben", 1, "12345", c);
		addPlayer("logan", 1, "0owqiejflsdkjf", c);
		addPlayer("paul", 1, "0owqiejflsdkjf", c);
		addPlayer("sam", 1, "0owqiejflsdkjf", c);
		Player s = getPlayer("12345", c);
		System.out.println("the player with id 12345 is: " + s.username);
		Player p = getPlayer("12345", c);
		disconnect(c);
	}
	public static Connection connect(){
		try{
		Class.forName("org.sqlite.JDBC");
			System.out.println("Connecting to Database....");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:db/database.db");
			System.out.println("Connection Successful");
			return conn;
		}
		catch(Exception e){
			System.out.println("Connection failed");
			return null;
		}
	}
	public static void disconnect(Connection c){
		try{
			c.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	public static void addAdmin(String name,  String feed,  Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into admin values(" + "'" +name + "' " + ", '" + feed + "')";
		s.executeUpdate(command);
	}
	public static void addPlayer(String name, int isZombie, String feed,  Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into users values("+ "'" +name + "' " + ", '" + feed + "', " + isZombie + ")";
		s.executeUpdate(command);
	}
	public static void removePlayer(String feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "delete from users where feedcode = '" + feedCode + "'"; 
		s.executeUpdate(command);
	}
	public static void removeAll(Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "delete from user"; 
		s.executeUpdate(command);
	}
	public static void makeZombie(String feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "update users set isZombie = 1 where feedCode = '" + feedCode + "'"; 
		s.executeUpdate(command);	
	}
	public static void makeHuman(String feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "update users set isZombie = 0 where feedCode = '" + feedCode + "'"; 
		s.executeUpdate(command);	
	}
	public static void tag(String tagger, String tagged, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into tags values('" + tagger + "', '" + tagged + "')";
		s.executeUpdate(command);		
	}
	public static void setPassword(String feedCode, String pswd, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into passwords values('" + feedCode + "', " + "'" + pswd + "'" + ")";
		s.executeUpdate(command);
	}
	public static Admin getAdmin(String feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from admins where feedcode = '" + feedCode + "'");
		//no player
		if (!rs.isBeforeFirst())
			return null;
		String name = rs.getString("username");
		String feed = rs.getString("feedCode");
		Admin admin = new Admin(name, feed);
		return admin;
	}
	public static Player getPlayer(String feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from users where feedcode = '" + feedCode + "'");
		//no player
		if (!rs.isBeforeFirst())
			return null;
		String name = rs.getString("username");
		String feed = rs.getString("feedCode");
		Player player = new Player(name, feed);
		return player;
	}
	public static String getPassword(String feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from passwords where feedcode = '" + feedCode + "'");
		String pswd = rs.getString("password");
		return pswd;
	}
	public static  Player [] getAllUsers(Connection c)throws SQLException{
		LinkedList<Player> users = new LinkedList<Player>();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from  users");
		while(rs.next()){
			String username = rs.getString("username");
			String feedcode = rs.getString("feedCode");
			Player user = new Player(username, feedcode);
			users.add(user);
		}
		rs.close();
		s.close();
		Player [] array = users.toArray(new Player[users.size()]);
		return array;
	}
	public static  Admin [] getAllAdmin(Connection c)throws SQLException{
		LinkedList<Admin> admins = new LinkedList<Admin>();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from  admins");
		while(rs.next()){
			String username = rs.getString("username");
			String feedcode = rs.getString("feedCode");
			Admin admin = new Admin(username, feedcode);
			admins.add(admin);
		}
		rs.close();
		s.close();
		Admin [] array = admins.toArray(new Admin[admins.size()]);
		return array;
	}
	public static boolean isStarted(Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select from gameStats where endTime = 'initialEndDate'");
		if (!rs.isBeforeFirst())
			return false;
		int bool = rs.getInt("hasBegun");
		if(bool == 0) return false;
		else return false;
	}
	public static void start (Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "update gameStats set hasBegun = 1 where endTime = 'initialEndDate'";
		s.executeUpdate(command);
	}
}