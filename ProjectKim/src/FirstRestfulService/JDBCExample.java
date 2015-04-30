package FirstRestfulService;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.*;
 
public class JDBCExample {
	
	//all queries:
	String selectAllNames = 
			"SELECT userdimension.name, textbookrentalfact.total "
			+ "from userdimension, textbookrentalfact "
			+ "where userdimension.id = textbookrentalfact.user_key";
	
	private static ArrayList<String> alluserids = new ArrayList<String>();
	private static ArrayList<String> alltotals = new ArrayList<String>();
	private Connection connection = null;
 
	public JDBCExample() {
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/textbookrental", "ddo",
					"dustindo");
			Statement statement = (Statement) connection.createStatement();
			
			ResultSet rs = statement.executeQuery(selectAllNames);
			while (rs.next()) {
				String username = rs.getString("name");
				String total = rs.getString("total");
				alltotals.add(total);
				alluserids.add(username);
			}
			
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
  
	public ArrayList<String> getUserIds() {
		return alluserids;
	}
	public ArrayList<String> getTotals() {
		return alltotals;
	}
	
}