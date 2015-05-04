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
	
	private String[] sliceFilters;
	private String[] tableNames = new String[] {"time", "store", "product"};
	private String[] attributes;
 
	public JDBCExample(String timeDim, String storeDim, String productDim, 
			String timeFilter, String storeFilter, String productFilter) {
		
		attributes = new String[] {timeDim, storeDim, productDim};
		sliceFilters = new String[] {timeFilter, storeFilter, productFilter};
		
		System.out.println("---------------");
		System.out.println("time attribute: " + attributes[0]);
		System.out.println("store attribute: " + attributes[1]);
		System.out.println("product attribute: " + attributes[2]);
		System.out.println("time slice: " + sliceFilters[0]);
		System.out.println("store slice: " + sliceFilters[1]);
		System.out.println("product slice: " + sliceFilters[2]);
		
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/grocery", "ddo",
					"dustindo");
			Statement statement = (Statement) connection.createStatement();
			
			String executeSQL = "";
			if(sliceFilters[0].isEmpty() && sliceFilters[1].isEmpty() && sliceFilters[2].isEmpty()){
				executeSQL = createBaseSQL();
			} else {
				for(int i = 0; i < sliceFilters.length; i++){
					if(!sliceFilters[i].isEmpty()){
						executeSQL = slice(tableNames[i], attributes[i], sliceFilters[i]);
					}
				}
			}
			
			ResultSet rs = statement.executeQuery(executeSQL);
			while (rs.next()) {
				String timeAtt = rs.getString(attributes[0]);
				String storeAtt = rs.getString(attributes[1]);
				String prodAtt = rs.getString(attributes[2]);
				
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
		String sql = "SELECT * FROM Sales_fact, store, time, product "
				+ "WHERE sales_fact.store_key = store.store_key AND "
				+ "sales_fact.time_key = time.time_key AND "
				+ "sales_fact.product_key = product.product_key LIMIT 10";
		return sql;
	}
	
	public String slice(String dimension, String attribute, String value){
		String sql = String.format("SELECT * FROM Sales_fact, store, time, product "
				+ "WHERE sales_fact.store_key = store.store_key AND "
				+ "sales_fact.time_key = time.time_key AND "
				+ "sales_fact.product_key = product.product_key AND "
				+ "%s.%s = \"%s\" LIMIT 10", dimension, attribute, value);
		System.out.println(sql);
		return sql;
	}
	
	/**
	 * not used yet
	 * @param dimension1
	 * @param attribute1
	 * @param value1
	 * @param dimension2
	 * @param attribute2
	 * @param value2
	 * @return
	 */
	public String dice(String dimension1, String attribute1, String value1,
					   String dimension2, String attribute2, String value2){
		String sql = String.format("SELECT * FROM Sales_fact, store, time, product "
				+ "WHERE sales_fact.store_key = store.store_key AND "
				+ "sales_fact.time_key = time.time_key AND "
				+ "sales_fact.product_key = product.product_key AND "
				+ "%s.%s = \"%s\" AND %s.%s = \"%s\"" 
				+ "LIMIT 10", dimension1, attribute1, value1, dimension2, attribute2, value2);
		System.out.println(sql);
		return sql;
	}
	
	public String getAllParams(){
		return "You chose: " + attributes[0] + ", " + attributes[1] + ", " + attributes[2];
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