import java.sql.*;

public class java_Database_Interface{
	public static void main (String [] args) throws SQLException{//for testing
		Connection c = connect();
		addPlayer(12, "kyle", 12, 3, c);
	}
	public static Connection connect(){
		try{
			Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			return conn;
		}
		catch(Exception e){
			System.out.println("Connection failed");
			return null;
		}
	}
	public static void addPlayer(int id, String name, int isZombie, int feed,  Connection c) throws SQLException{
			Statement s = c.createStatement();
			String command = "insert into user values(" + id + ", " + "'" +name + "'" + "," + feed + "," + isZombie + ")";
			System.out.println(command);
			ResultSet set = s.executeQuery(command);
	}
	public static void removePlayer(int id, Connection c)throws SQLException{}
	public static void makeZombie(int id, Connection c)throws SQLException{}
	public static void makeHuman(int id, Connection c)throws SQLException{}
	public static void tag(int tagger, int tagged, Connection c)throws SQLException{}
	public static void addPassword(int id, String pswd, Connection c)throws SQLException{}
	

}