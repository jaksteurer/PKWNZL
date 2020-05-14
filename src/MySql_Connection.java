import java.sql.Connection;
import java.sql.DriverManager;

public class MySql_Connection {

	public static void main(String[] args) throws Exception {
		getConnection();
	}
	//Testen ob Java mit MySQL-Server verbinden funktioniert
	public static Connection getConnection() throws Exception{
		try {
			String driver = "com.mysql.jdbc.Driver";
										//Server		//datenbank
			String url = "jdbc:mysql://localhost:3306/neuzulassungen";
			String user ="";
			String passwort = "";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url, user, passwort);
			System.out.println("Connected");
			return conn;
		}catch(Exception e) {
			System.out.println(e);
		}		
		return null;
	}
}
