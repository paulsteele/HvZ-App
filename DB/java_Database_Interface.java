import java.sql.*;

public class java_Database_Interface{
	public static  Connection connect(){
		try{
			Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			return conn;
		}
		catch(Exception e){
			System.out.println("Connection failed");
			return null;
		}
	}
	public static void addPlayer(int id, Connection c){}
	public static void removePlayer(int id, Connection c){}
	public static void makeZombie(int id, Connection c){}
	public static void makeHuman(int id, Connection c){}
	//Statement s = c.createStatement();
	
	public static void main (String []args){//for testing
		Connection c = connect();
	}
}