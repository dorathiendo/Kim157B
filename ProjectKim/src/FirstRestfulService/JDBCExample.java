package FirstRestfulService;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.*;
 
public class JDBCExample {
	
	private static String alluserids = "";
	Connection connection = null;
 
	public JDBCExample() {
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/textbookrental", "ddo",
					"dustindo");
			
			String selectTableSQL = "SELECT user_key from textbookrentalfact";
			Statement statement = (Statement) connection.createStatement();
			ResultSet rs = statement.executeQuery(selectTableSQL);
			while (rs.next()) {
				String userid = rs.getString("user_key");
				System.out.println(userid);
				alluserids += userid + "\n";
			}
			
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
  
	public String getUserIds() {
		return alluserids;
	}
}