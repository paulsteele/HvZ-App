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
				"isZombie	 	int, " +
				"gameCode		varchar(25))";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		command = "CREATE TABLE reviveCodes" + 
				"(reviveCode varchar(25), " +
				"gameCode	varchar(25))" ;//1 for true, 0 for false
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		command = "CREATE TABLE passwords " + 
				"(username		varchar(40), " + 
				"password 		varchar(25))";
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
						"(endTime 	varchar(25), " +
						"hasBegun	int, " +
						"gameCode	varchar(25)," +
						"name		varchar(25)," +
						"creator	varchar(25))";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		command = "CREATE TABLE missions " + 
						"(gameCode	varchar(25), " +
						"humanObjective	varchar(500), " +
						"zombieObective	varchar(500), " +
						"isCompleted	int, " +
						"title			varchar(25))";
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
	public static void setPassword(String username, String pswd, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into passwords values('" + username + "', " + "'" + pswd + "')";
		s.executeUpdate(command);
	}
	public static Admin getAdminU(String username, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from admins where username = '" + username + "'");
		//no player
		if (!rs.isBeforeFirst())
			return null;
		String name = rs.getString("username");
		String feed = rs.getString("feedCode");
		Admin admin = new Admin(name, feed);
		admin.gamecode = rs.getString("gameCode");
		return admin;
	}
	public static Player getPlayerU(String username, Connection c)throws SQLException{//get player by username
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from users where username = '" + username + "'");
		//no player
		if (!rs.isBeforeFirst())
			return null;
		String name = rs.getString("username");
		String feed = rs.getString("feedCode");
		Player player = new Player(name, feed);
		player.gamecode = rs.getString("gameCode");
		return player;
	}
	public static Admin getAdminFG(String feedCode, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from admins where feedCode = '" + feedCode + "' and gameCode = '" + gameCode + "'");
		//no player
		if (!rs.isBeforeFirst())
			return null;
		String name = rs.getString("username");
		String feed = rs.getString("feedCode");
		Admin admin = new Admin(name, feed);
		admin.gamecode = rs.getString("gameCode");
		return admin;
	}
	public static Player getPlayerFG(String feedCode, String gameCode, Connection c)throws SQLException{//get player by feed and game code
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from users where feedCode = '" + feedCode + "' and gameCode = '" + gameCode + "'");
		//no player
		if (!rs.isBeforeFirst())
			return null;
		String name = rs.getString("username");
		String feed = rs.getString("feedCode");
		Player player = new Player(name, feed);
		player.gamecode = rs.getString("gameCode");
		return player;
	}
	public static String getPassword(String username, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from passwords where username = '" + username + "'");
		String pswd = rs.getString("password");
		return pswd;
	}
	public static  Player [] getAllUsers(String gameCode, Connection c)throws SQLException{
		LinkedList<Player> users = new LinkedList<Player>();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from users where gameCode = '" + gameCode + "'");
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
	public static Game [] getAllGames(Connection c) throws SQLException{
		LinkedList<Game> games = new LinkedList<Game>();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from  games");
		while(rs.next()){
			String creator = rs.getString("creator");
			String name = rs.getString("name");
			String gc = rs.getString("gameCode");
			Game g = new Game(gc, name, creator);
			games.add(g);
		}
		rs.close();
		s.close();
		Game [] gameArray = games.toArray(new Game[games.size()]);
		return gameArray;
	}
	public static boolean isStarted(String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from games where gameCode = '" + gameCode + "'");
		if (!rs.isBeforeFirst())
			return false;
		int bool = rs.getInt("hasBegun");
		if(bool == 0) return false;
		else return true;//do you ever return true?
	}
	public static void start (String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "update games set hasBegun = 1 where gameCode = '" + gameCode + "'";
		s.executeUpdate(command);
	}
	public static void newGame(String gameCode, String name, String creator, Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into games values('end', 0 ,'" + gameCode + "', '" + name + "', '" + creator + "')";
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
	public static boolean isUsernameTaken(String username, Connection c) throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from users");
		while(rs.next()){
			String name = rs.getString("username");
			if(username.equals(name))
				return true;
			else continue;
		}
		return false;	
	}
	public static boolean isFeedcodeTaken(String feedcode, String gamecode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from users where gameCode = '" + gamecode + "'");
		while(rs.next()){
			String code = rs.getString("gameCode");
			if(feedcode.equals(code))
				return true;
			else continue;
		}
		return false;	
	}
	public static void changeGamecode(String username, String newGame, boolean isAdmin, Connection c) throws SQLException{
		if(isAdmin == false){
			Statement s = c.createStatement();
			String command = "update users set gameCode = '" + newGame + "' where username = '" + username + "'"; 
			s.executeUpdate(command);
		}
		else{
			Statement s = c.createStatement();
			String command = "update admins set gameCode = '" + newGame + "' where username = '" + username + "'";   
			s.executeUpdate(command);
		}
	}
	public static void addMission(String gameCode, String humanObjective, String zombieObjective, int isCompleted, String title, Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into missions values('" +gameCode + "' " + ", '" + humanObjective + "', " + zombieObjective + ", 0, '" + title+ "')";
		s.executeUpdate(command);
	}
	public static void completedMission(String gameCode, String title, Connection c) throws SQLException {
		Statement s = c.createStatement();
		String command = "update missions set isCompleted = 1, where gameCode = " + gameCode + "and title = " + title + "'";
		s.executeUpdate(command);		
	}
	public static void deleteReviveCode(String reviveCode, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into users reviveCodes('" +reviveCode + "' ,'" + gameCode + "'";
		s.executeUpdate(command);
	}
	public static void addReviveCode(String reviveCode, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "delete from reviveCodes where reviveCode  = '" + reviveCode + "' " +  "AND gameCode = " + "'" + gameCode + "'"; 
		s.executeUpdate(command);
	}
	public static boolean validateReviveCode(String reviveCode, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from reviveCodes where reviveCode  = '" + reviveCode + "' and gameCode = '" + gameCode + "'");
		if (!rs.isBeforeFirst())
			return false;
		return true;
	}	
	public static void changeFeedCode(String username, String newFeedCode, boolean isAdmin, Connection c) throws SQLException{
		if(isAdmin == false){
			Statement s = c.createStatement();
			String command = "update users set feedCode = '" + newFeedCode + "' where username = '" + username + "'";  
			s.executeUpdate(command);	
		}
		else{
			Statement s = c.createStatement();
			String command = "update admins set feedCode = '" + newFeedCode + "' where username = '" + username + "'";
			s.executeUpdate(command);	
		}
	}
}
