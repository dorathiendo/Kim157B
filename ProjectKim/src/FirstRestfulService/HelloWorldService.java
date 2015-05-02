package FirstRestfulService;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
 
@Path("/output")
public class HelloWorldService {

	@POST
	@Produces(MediaType.TEXT_HTML)
	public String olapOperation(@FormParam("time_attribute") String timeDim, 
								@FormParam("store_attribute") String storeDim, 
								@FormParam("product_attribute") String productDim) {
		
		JDBCExample jdbc = new JDBCExample(timeDim, storeDim, productDim);
		String[] facts = {"dollar_sales", "unit_sales", "dollar_cost", "customer_count"};

		String table = "<table border = \"1\">";
		
		//olap operations
				table += "<tr>";
				table += "<form action=\"../api/output\" method=\"post\">";
				table += timeDropdown();
				table += storeDropdown();
				table += productDropdown();
				table += "<td><input type=\"submit\" value=\"Submit\"></td></form>";
				table += "</tr>";
		
		table += "<tr>"
				+ "<td>" + timeDim + "</td>"
				+ "<td>" + storeDim + "</td>"
				+ "<td>" + productDim + "</td>";
		
		for(String f : facts){
			table += "<td>" + f + "</td>";
		}
		
		table += "</tr>";
		
		for(int i = 0; i < jdbc.getTimeCol().size(); i++){
			table += "<tr>";
			table += "<td>" + jdbc.getTimeCol().get(i) + "</td>";
			table += "<td>" + jdbc.getStoreCol().get(i) + "</td>";
			table += "<td>" + jdbc.getProductCol().get(i) + "</td>";
			table += "<td>" + jdbc.getDollarSalesCol().get(i) + "</td>";
			table += "<td>" + jdbc.getUnitSalesCol().get(i) + "</td>";
			table += "<td>" + jdbc.getDollarCostCol().get(i) + "</td>";
			table += "<td>" + jdbc.getCustomerCountCol().get(i) + "</td>";
			table += "</tr>";
		}
	    table += "</table>";
		
		String returnLink = "<a href=\"../index.html\">Return</a>";
		
		jdbc.reset();
		return jdbc.getAllParams() + table + returnLink;
	}
	
	public String timeDropdown(){
		String t = "<td>"
				+ "<select name=\"time_attribute\">" 
				+ "<option value=\"date\">Date</option>"
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
	
	
}