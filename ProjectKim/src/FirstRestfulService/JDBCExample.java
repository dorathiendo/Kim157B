package FirstRestfulService;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.*;
 
public class JDBCExample {
	
	
	private static ArrayList<String> timeColumn = new ArrayList<String>();
	private static ArrayList<String> storeColumn = new ArrayList<String>();
	private static ArrayList<String> productColumn = new ArrayList<String>();
	private static ArrayList<String> dollarSalesCol = new ArrayList<String>();
	private static ArrayList<String> unitSalesCol = new ArrayList<String>();
	private static ArrayList<String> dollarCostCol = new ArrayList<String>();
	private static ArrayList<String> customerCountCol = new ArrayList<String>();
	private Connection connection = null;
	
	private String timeAttribute = "";
	private String storeAttribute = "";
	private String productAttribute = "";
 
	public JDBCExample(String timeDim, String storeDim, String productDim) {
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/grocery", "ddo",
					"dustindo");
			Statement statement = (Statement) connection.createStatement();
			
			timeAttribute = timeDim;
			storeAttribute = storeDim;
			productAttribute = productDim;
			
			ResultSet rs = statement.executeQuery(createBaseSQL());
			while (rs.next()) {
				String timeAtt = rs.getString(timeAttribute);
				String storeAtt = rs.getString(storeAttribute);
				String prodAtt = rs.getString(productAttribute);
				
				//facts
				String ds = rs.getString("dollar_sales");
				String us = rs.getString("unit_sales");
				String dc = rs.getString("dollar_cost");
				String cc = rs.getString("customer_count");
				
				timeColumn.add(timeAtt);
				storeColumn.add(storeAtt);
				productColumn.add(prodAtt);
				
				dollarSalesCol.add(ds);
				unitSalesCol.add(us);
				dollarCostCol.add(dc);
				customerCountCol.add(cc);
			}
			
			
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
	
	public void reset(){
		timeColumn.removeAll(timeColumn);
		storeColumn.removeAll(storeColumn);
		productColumn.removeAll(productColumn);
	}
	
	public String createBaseSQL(){
		String sql = "SELECT * FROM Sales_fact, store, time_grocery, product "
				+ "WHERE sales_fact.store_key = store.store_key AND "
				+ "sales_fact.time_key = time_grocery.time_key AND "
				+ "sales_fact.product_key = product.product_key LIMIT 10";
		return sql;
	}
	
	public String getAllParams(){
		return "You chose: " + timeAttribute + ", " + storeAttribute + ", " + productAttribute;
	}
	
	public ArrayList<String> getTimeCol(){
		return timeColumn;
	}
	public ArrayList<String> getStoreCol(){
		return storeColumn;
	}
	public ArrayList<String> getProductCol(){
		return productColumn;
	}
	
	//Facts
	public ArrayList<String> getDollarSalesCol(){
		return dollarSalesCol;
	}
	public ArrayList<String> getUnitSalesCol(){
		return unitSalesCol;
	}
	public ArrayList<String> getDollarCostCol(){
		return dollarCostCol;
	}
	public ArrayList<String> getCustomerCountCol(){
		return customerCountCol;
	}
}