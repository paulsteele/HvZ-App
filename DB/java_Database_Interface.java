import java.sql.*;

public class java_Database_Interface{
	public static void main(String [] args){
		try{
			Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		}
		catch(Exception e){}
	}
}