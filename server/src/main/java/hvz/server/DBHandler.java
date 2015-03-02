package hvz.server;
import java.sql.*;
public class DBHandler{
	public static void main (String [] args) throws SQLException{//for testing
		Connection c = connect();
		addPlayer(0, "k", 1, 3, c);
		addPlayer(2, "y", 2, 5, c);
		addPlayer(2, "l", 3, 7, c);
		addPlayer(2, "e", 4, 8, c);
		addAdmin(3, "admin name", 20, c);
		setPassword(2,5,"Pswd",c);
		removePlayer(3, c);
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
	public static void addAdmin(int id, String name,  int feed,  Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into admin values(" + id + ", " + "'" +name + "' " + ", " + feed + ")";
		s.executeUpdate(command);
	}
	public static void addPlayer(int id, String name, int isZombie, int feed,  Connection c) throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into user values(" + id + ", " + "'" +name + "' " + ", " + feed + ", " + isZombie + ")";
		s.executeUpdate(command);
	}
	public static void removePlayer(int feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "delete from user where feedcode = " + feedCode; 
		s.executeUpdate(command);
	}
	public static void removeAll(Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "delete from user"; 
		s.executeUpdate(command);
	}
	public static void makeZombie(int feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "update user set isZombie = 1 where feedCode = " + feedCode; 
		s.executeUpdate(command);	
	}
	public static void makeHuman(int feedCode, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "update user set isZombie = 0 where feedCode = " + feedCode; 
		s.executeUpdate(command);	
	}
	public static void tag(int tagger, int tagged, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into tags values(" + tagger + ", " + tagged + ")";
		s.executeUpdate(command);		
	}
	public static void setPassword(int index, int feedCode, String pswd, Connection c)throws SQLException{
		Statement s = c.createStatement();
		String command = "insert into password values(" + index + ", " + feedCode + ", " + "'" + pswd + "'"+ ")";
		s.executeUpdate(command);
	}
}