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
				"gameCode		varchar(25)," +
				"lastTag		datetime)";
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
				"gameCode		varchar(25)," +
				"zombieTag		int)"; //1 for zombie tag, 0 for human tag
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
		command = "CREATE TABLE complaints " + 
			"(ccode			varchar(25), " + 
			"sender	 		varchar(40), " +
			"message	 	varchar(40), " +
			"gameCode		varchar(25))";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		command = "CREATE TABLE games" + 
						"(isEnded 	int, " +
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
				command = "CREATE TABLE maps " + 
						"(gameCode	varchar(25), " +
						"map		BLOB)";
		try {
			Statement s = c.createStatement();
			s.executeUpdate(command);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
				command = "CREATE TABLE cooldowns " + 
						"(locked 		datetime," +
						"feedCode		varchar(25)," +
						"gameCode       varchar(25))";
						
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
		String command = "insert into users values('" +name + "' " + ", '" + feed + "', " + isZombie + ", '" + gameCode+ "', datetime('now'))";
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
	// format for time YYYY-MM-DD HH:MM:SS.SSS
	public static void tag(String tagger, String tagged, String gameCode, int isZombie, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into tags values('" + tagger + "', '" + tagged + "', '" + gameCode + "', " + isZombie + ")";
		s.executeUpdate(command);
		command = "update users set lastTag = datetime('now') where feedCode = '" + tagger + "' and gameCode = '" + gameCode + "'";
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
		if (rs.getInt("isZombie") == 1){
			player.isZombie = true;
		}
		else{
			player.isZombie = false;
		}
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
		if (rs.getInt("isZombie") == 1){
			player.isZombie = true;
		}
		else{
			player.isZombie = false;
		}
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
			if (rs.getInt("isZombie") == 1){
				user.isZombie = true;
			}
			else{
				user.isZombie = false;
			}
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
		String command = "insert into games values(0, 0 ,'" + gameCode + "', '" + name + "', '" + creator + "')";
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
		s = c.createStatement();
		rs = s.executeQuery("select * from admins");
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
		s = c.createStatement();
		rs = s.executeQuery("select * from admins where gameCode = '" + gamecode + "'");
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
		String command = "insert into missions values('" +gameCode + "' " + ", '" + humanObjective + "', '" + zombieObjective + "', 0, '" + title+ "')";
		s.executeUpdate(command);
	}
	public static void completedMission(String gameCode, String title, Connection c) throws SQLException {
		Statement s = c.createStatement();
		String command = "update missions set isCompleted = 1, where gameCode = " + gameCode + "and title = " + title + "'";
		s.executeUpdate(command);		
	}
	public static void deleteReviveCode(String reviveCode, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "delete from reviveCodes where reviveCode  = '" + reviveCode + "' " +  "AND gameCode = " + "'" + gameCode + "'"; 
		s.executeUpdate(command);
	}
	public static void addReviveCode(String reviveCode, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into reviveCodes values('" +reviveCode + "' ,'" + gameCode + "')";
		s.executeUpdate(command);
	}
	public static boolean validateReviveCode(String reviveCode, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from reviveCodes where reviveCode  = '" + reviveCode + "' and gameCode = '" + gameCode + "'");
		if (!rs.isBeforeFirst())
			return false;
		else{
			return true;
		}
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
	public static Mission getMissionByTitle(String title, String gameCode, Connection c) throws SQLException{ 
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from missions where gameCode = '" + gameCode + "' and title = '" + title + "'");
		if (!rs.isBeforeFirst())
			return null;
		String ho = rs.getString("humanObjective");
		String zo = rs.getString("zombieObective");
		int isCompleted = rs.getInt("isCompleted");
		Mission m = new Mission(gameCode, title, zo, ho, isCompleted);
		return m;
	}
	public static Mission [] getAllMissions(String gameCode, Connection c) throws SQLException{
		LinkedList<Mission> missions = new LinkedList<Mission>();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from  missions where gameCode = '" + gameCode + "'");
		while(rs.next()){
			String title = rs.getString("title");
			String ho = rs.getString("humanObjective");
			String zo = rs.getString("zombieObective");
			String gc = rs.getString("gameCode");
			int isCompleted = rs.getInt("isCompleted");
			Mission m = new Mission(gc, title, zo, ho, isCompleted);
			missions.add(m);
		}
		rs.close();
		s.close();
		Mission [] missionArray = missions.toArray(new Mission[missions.size()]);
		return missionArray;
	}
	public static String [] getAllReviveCodes(String gameCode, Connection c) throws SQLException{
		LinkedList<String> codes = new LinkedList<String>();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from  reviveCodes where gameCode = '" + gameCode + "'");
		while(rs.next()){
			String code = rs.getString("reviveCode");
			codes.add(code);
		}
		rs.close();
		s.close();
		String [] codeArray = codes.toArray(new String[codes.size()]);
		return codeArray;
	}
	public static boolean checkEnded(String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from games where gameCode = '" + gameCode + "'");
		if (!rs.isBeforeFirst())
			return false;
		int bool = rs.getInt("isEnded");
		if(bool == 0) return false;
		else return true;
	}
	public static void end (String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "update games set isEnded = 1 where gameCode = '" + gameCode + "'";
		s.executeUpdate(command);
	}
	public static int countHumans(String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select count(*) from users where gameCode = '" + gameCode + "' and isZombie = 0");
		int count;
		rs.next();
		count = rs.getInt(1);
		rs.close();
		s.close();
		return count;
	}
	public static int countZombies(String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select count(*) from users where gameCode = '" + gameCode + "' and isZombie = 1");
		int count;
		rs.next();
		count = rs.getInt(1);
		rs.close();
		s.close();
		return count;
	}
	public static int countZombieTags(String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select count(*) from tags where gameCode = '" + gameCode + "' and zombieTag = 0");
		int count;
		rs.next();
		count = rs.getInt(1);
		rs.close();
		s.close();
		return count;
	}
	public static int countHumanTags(String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select count(*) from tags where gameCode = '" + gameCode + "' and zombieTag = 1");
		int count;
		rs.next();
		count = rs.getInt(1);
		rs.close();
		s.close();
		return count;
	}
	public static void setcooldown(String feedCode, String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into cooldowns values(datetime('now', '+10 minutes'), '" + feedCode + "','" + gameCode + "')";
		s.executeUpdate(command);
		s.close();
	}
	public static int getcooldown(String feedCode, String gameCode, Connection c)throws SQLException{
		Statement st = c.createStatement();
		ResultSet res = st.executeQuery("select locked from  cooldowns where feedCode = '" + feedCode + "' and gameCode = '" + gameCode + "'");
		if (!res.isBeforeFirst()){
			st = c.createStatement();
			res = st.executeQuery("SELECT datetime('now')");
		}
		String date = res.getString(1);
		Statement st3 = c.createStatement();
		ResultSet res3 = st3.executeQuery("SELECT strftime('%s','now') - strftime('%s','" + date + "');");
		int dif = res3.getInt(1);
		res.close();
		res3.close();
		st.close();
		st3.close();
		if (dif >= 0){
			st = c.createStatement();
			st.executeUpdate("DELETE FROM cooldowns WHERE feedCode='" + feedCode + "' AND gameCode = '" + gameCode + "';");
		}
		return dif;
	}
	
	public static void createComplaint(String ccode, String sender, String message, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into complaints values('" + ccode + "', '" + sender + "', '" + message + "', '" + gameCode + "')";
		s.executeUpdate(command);
	}
	
	public static void deleteComplaint(String ccode, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "delete from complaints where ccode = '" + ccode + "' " +  "AND gameCode = " + "'" + gameCode + "'"; 
		s.executeUpdate(command);
	}
	
	public static Complaint getComplaint(String ccode, String gameCode, Connection c) throws SQLException{
    	Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from complaints where ccode = '" + ccode + "' AND gameCode = '" + gameCode +"'");
		if (!rs.isBeforeFirst())
			return null;
		String message = rs.getString("message");
		String sender = rs.getString("sender");
		Complaint comp = new Complaint(ccode,  sender,  message,  gameCode);
		return comp;
    }
	
	public static  Complaint [] getAllComplaints(String gameCode, Connection c)throws SQLException{
		LinkedList<Complaint> complaints = new LinkedList<Complaint>();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from  complaints where gameCode = '" + gameCode + "'");
		while(rs.next()){
			String ccode = rs.getString("ccode");
			String sender = rs.getString("sender");
			String message = rs.getString("message");
			Complaint complaint = new Complaint(ccode, sender, message, gameCode);
			complaints.add(complaint);
		}
		rs.close();
		s.close();
		Complaint [] array = complaints.toArray(new Complaint[complaints.size()]);
		return array;
	}
	public static void setPicture(byte [] blob, String gameCode, Connection c)throws SQLException{
		Statement ss = c.createStatement();
		ResultSet rs = ss.executeQuery("select map from maps where gameCode = '" + gameCode + "'");
		if (!rs.isBeforeFirst()){
			PreparedStatement s = null;
			String command = "insert into maps(gameCode, map) VALUES(?,?)";
			s = c.prepareStatement(command);
			s.setString(1, gameCode);
			s.setBytes(2, blob);
			s.executeUpdate();
			s.close();
			ss.close();
			rs.close();
			return;
		}
		else{
			PreparedStatement s = null;
			//update users set isZombie = 0 where feedCode
			String command = "update maps set map = ? where gameCode = ?";
			s = c.prepareStatement(command);
			s.setBytes(1, blob);
			s.setString(2, gameCode);
			s.executeUpdate();
			s.close();
			ss.close();
			rs.close();
			return;
		}
		

	}
	public static byte []  getPicture(String gameCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select map from maps where gameCode = '" + gameCode + "'");
		if (!rs.isBeforeFirst())
			return null;
		byte [] map = rs.getBytes("map");
		s.close();
		rs.close();
		return map;
	}
	public static boolean validateCCode(String ccode, String gameCode, Connection c) throws SQLException{
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("select * from complaints where ccode  = '" + ccode + "' and gameCode = '" + gameCode + "'");
		if (!rs.isBeforeFirst()){
			s.close();
			rs.close();
			return false;
		}
		else{
			s.close();
			rs.close();
			return true;
		}

	}
}



