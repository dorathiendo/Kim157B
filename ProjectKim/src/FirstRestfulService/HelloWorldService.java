package FirstRestfulService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
 
@Path("/output")
public class HelloWorldService {

	@POST
	@Produces(MediaType.TEXT_HTML)
	public String olapOperation(@FormParam("time_attribute") String timeAttr, 
								@FormParam("store_attribute") String storeAttr, 
								@FormParam("product_attribute") String productAttr,
								@FormParam("time_filter") String timeSlice,
								@FormParam("store_filter") String storeSlice,
								@FormParam("product_filter") String productSlice) {
		
		JDBCExample jdbc = new JDBCExample(timeAttr, storeAttr, productAttr, timeSlice, storeSlice, productSlice);
		String[] facts = {"dollar_sales", "unit_sales", "dollar_cost", "customer_count"};

		String olapTable = "<table border = \"1\">";
		olapTable += "<tr>";
		olapTable += "<form action=\"../api/output\" method=\"post\">";
		olapTable += timeDropdown();
		olapTable += storeDropdown();
		olapTable += productDropdown();
		olapTable += "<input type=\"hidden\" name=\"time_filter\" value=\"\">"
				+ "<input type=\"hidden\" name=\"store_filter\" value=\"\">"
				+ "<input type=\"hidden\" name=\"product_filter\" value=\"\">";
		olapTable += "<td><input type=\"submit\" value=\"Submit\"></td></form>";
		olapTable += "</tr>";
		olapTable += "<tr>";
		olapTable += "</table>";
				
		String resultTable = "<table border = \"1\">";
		
		resultTable += "<tr>"
				+ "<td>" + timeAttr + "</td>"
				+ "<td>" + storeAttr + "</td>"
				+ "<td>" + productAttr + "</td>";
		
		for(String f : facts){
			resultTable += "<td>" + f + "</td>";
		}
		resultTable += "</tr>";
		
		resultTable += "<tr><form action=\"../api/output\" method=\"post\">"
				+ "<input type=\"hidden\" name=\"time_attribute\" value=\"" + timeAttr + "\">" 
				+ "<input type=\"hidden\" name=\"store_attribute\" value=\"" + storeAttr + "\">"
				+ "<input type=\"hidden\" name=\"product_attribute\" value=\"" + productAttr + "\">"
				+ "<td>" + sliceDropDown("time", timeAttr) + "</td>"
				+ "<td>" + sliceDropDown("store", storeAttr) + "</td>"
				+ "<td>" + sliceDropDown("product", productAttr) + "</td>"
				+ "<td><input type=\"submit\" value=\"Submit\"></td>"
				+ "</form></tr>";
		
		for(int i = 0; i < jdbc.getTimeCol().size(); i++){
			resultTable += "<tr>";
			resultTable += "<td>" + jdbc.getTimeCol().get(i) + "</td>";
			resultTable += "<td>" + jdbc.getStoreCol().get(i) + "</td>";
			resultTable += "<td>" + jdbc.getProductCol().get(i) + "</td>";
			resultTable += "<td>" + jdbc.getDollarSalesCol().get(i) + "</td>";
			resultTable += "<td>" + jdbc.getUnitSalesCol().get(i) + "</td>";
			resultTable += "<td>" + jdbc.getDollarCostCol().get(i) + "</td>";
			resultTable += "<td>" + jdbc.getCustomerCountCol().get(i) + "</td>";
			resultTable += "</tr>";
		}
		resultTable += "</table>";
		
		String returnLink = "<a href=\"../index.html\">Return</a>";
		
		jdbc.reset();
		return jdbc.getAllParams() + olapTable + resultTable + returnLink;
	}
	
	public String timeDropdown(){
		String t = "<td>"
				+ "<select name=\"time_attribute\">" 
				+ "<option value=\"day_of_week\">Day of Week</option>"
				+ "<option value=\"day_number_in_month\">Day number in Month</option>"
				+ "<option value=\"quarter\">Quarter</option>"
				+ "<option value=\"year\">Year</option>"
				+ "</select>";
		t += "</td>";
		return t;
	}
	
	public String storeDropdown(){
		String t = "<td>"
				+ "<select name=\"store_attribute\">"
				+ "<option value=\"city\">City</option>"
				+ "<option value=\"store_county\">County</option>"
				+ "<option value=\"store_state\">State</option>"
				+ "<option value=\"sales_region\">Region</option>"
				+ "</select>";
		t += "</td>";
		return t;
	}
	
	public String productDropdown(){
		String t = "<td>"
				+ "<select name=\"product_attribute\">"
				+ "<option value=\"brand\">Brand</option>"
				+ "<option value=\"subcategory\">Subcategory</option>"
				+ "<option value=\"category\">Category</option>"
				+ "<option value=\"department\">Department</option>"
				+ "</select>";
		t += "</td>";
		return t;
	}
	
	public String sliceDropDown(String dimension, String attribute){
		String dropdown = "";
		switch(dimension){
			case "time":
				dropdown = "<input type=\"text\" name=\"time_filter\">";
				break;
			case "store":
				dropdown = "<input type=\"text\" name=\"store_filter\">";
				break;
			case "product":
				dropdown = "<input type=\"text\" name=\"product_filter\">";
				break;
		}
		return dropdown;
	}
	
}