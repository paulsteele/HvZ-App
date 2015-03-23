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
	public static void createTable(Connection c) {
		String command = "CREATE TABLE users " + 
				"(username		varchar(25), " + 
				"feedCode 		varchar(40)," + 
				"isZombie	 	int)" +
				"gameCode		varchar(25)";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		command = "CREATE TABLE gameStats" + 
				"(endTime 	varchar(25), " +
				"hasBegun	int, " +
				"gameCode	varchar(25))";		//1 for true, 0 for false
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
				"(feedCode		varchar(40), " + 
				"password 		varchar(25), " + 
				"gameCode		varchar(25))";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		command = "CREATE TABLE tags" + 
				"(tagger		varchar(40), " + 
				"tagged 		varchar(40), " +
				"gameCode		varchar(25))";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		command = "CREATE TABLE admins " + 
				"(username		varchar(25), " + 
				"feedCode 		varchar(40), " +
				"gameCode		varchar(25))";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		command = "CREATE TABLE games" + 
						"(gameCode	varchar(25))";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		command = "CREATE TABLE missions " + 
						"(gameCode	varchar(25) " +
						"humanObjective	varchar(500), " +
						"zombieObective	varchar(500), " +
						"isCompleted		int)";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
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
	public static void addAdmin(String name,  String feed, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into admins values(" + "'" +name + "' " + ", '" + feed + "' , '" + gameCode + "')";
		s.executeUpdate(command);
	}
	public static void addPlayer(String name, int isZombie, String feed, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into users values('" +name + "' " + ", '" + feed + "', " + isZombie + ", '" + gameCode+ "')";
		s.executeUpdate(command);
	}
	public static void removePlayer(String feedCode, String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "delete from users where feedcode = '" + feedCode + "' " +  "AND gameCode = " + "'" + gameCode + "'"; 
		s.executeUpdate(command);
	}
	public static void removeAll(String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "delete from users where gameCode = " + gameCode ; 
		s.executeUpdate(command);
	}
	public static void makeZombie(String feedCode, String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "update users set isZombie = 1 where feedCode = '" + feedCode + "'" + 
		"and gameCode = '" + gameCode + "'"; 
		s.executeUpdate(command);	
	}
	public static void makeHuman(String feedCode, String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "update users set isZombie = 0 where feedCode = '" + feedCode + "'" +
		"and gameCode = '" + gameCode + "'";  
		s.executeUpdate(command);	
	}
	public static void tag(String tagger, String tagged, String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into tags values('" + tagger + "', '" + tagged + "', '" + gameCode + "')";
		s.executeUpdate(command);		
	}
	public static void setPassword(String feedCode, String pswd, String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into passwords values('" + feedCode + "', " + "'" + pswd + "', '" + gameCode + "')";
		s.executeUpdate(command);
	}
	public static Admin getAdmin(String feedCode, String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from admins where feedcode = '" + feedCode + "' AND gameCode = '" + gameCode + "'");
		//no player
		if (!rs.isBeforeFirst())
			return null;
		String name = rs.getString("username");
		String feed = rs.getString("feedCode");
		Admin admin = new Admin(name, feed);
		return admin;
	}
	public static Player getPlayer(String feedCode, String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from users where feedcode = '" + feedCode + "' AND gameCode = '" + gameCode + "'");
		//no player
		if (!rs.isBeforeFirst())
			return null;
		String name = rs.getString("username");
		String feed = rs.getString("feedCode");
		Player player = new Player(name, feed);
		return player;
	}
	public static String getPassword(String feedCode, String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from passwords where feedcode = '" + feedCode + "' AND gameCode = '" + gameCode + "'");
		String pswd = rs.getString("password");
		return pswd;
	}
	public static  Player [] getAllUsers(String gameCode, Connection c)throws SQLException{
		LinkedList<Player> users = new LinkedList<Player>();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from  users where gameCode = '" + gameCode + "'");
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
	public static  Admin [] getAllAdmin(String gameCode, Connection c)throws SQLException{
		LinkedList<Admin> admins = new LinkedList<Admin>();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from  admins where gameCode = '" + gameCode + "'");
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
	public static String [] getAllGames(Connection c) throws SQLException{
		LinkedList<String> games = new LinkedList<String>();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from  games");
		while(rs.next()){
			String gc = rs.getString("gameCode");
			games.add(gc);
		}
		rs.close();
		s.close();
		String [] gameArray = games.toArray(new String[games.size()]);
		return gameArray;
	}
	public static boolean isStarted(String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from gameStats where endTime = 'initialEndDate'");
		if (!rs.isBeforeFirst())
			return false;
		int bool = rs.getInt("hasBegun where gameCode = '" + gameCode + "'");
		if(bool == 0) return false;
		else return true;//do you ever return true?
	}
	public static void start (String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "update gameStats set hasBegun = 1 where gameCode = '" + gameCode + "'";
		s.executeUpdate(command);
	}
	public static void newGame(String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into games( '" + gameCode + "')";
		s.executeUpdate(command);
	}
	public static boolean isGamecodeTaken(String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from games");
		while(rs.next()){
			String code = rs.getString("gameCode");
			if(code.equals(gameCode))
				return true;
			else continue;
		}
		return false;
	}
	public static boolean isUsernameTaken(String username, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from users where gameCode = '" + gameCode + "'");
		while(rs.next()){
			String name = rs.getString("username");
			if(username.equals(name))
				return true;
			else continue;
		}
		return false;	
	}
	public static void changeGamecode(String feedCode, String newGame, String oldGame, Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "update users set gameCode = '" + newGame + "' where feedCode = '" + feedCode + "' and gameCode = '" + oldGame + "'" ;   
		s.executeUpdate(command);	
	}
}
