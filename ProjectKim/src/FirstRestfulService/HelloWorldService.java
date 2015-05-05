package FirstRestfulService;

import java.util.List;

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
			@FormParam("product_filter") String productSlice,
			@FormParam("dimension") List<String> dimensions) {

		JDBCExample jdbc = new JDBCExample(timeAttr, storeAttr, productAttr,
				timeSlice, storeSlice, productSlice);
		String[] facts = { "dollar_sales", "unit_sales", "dollar_cost",
				"customer_count" };
		boolean timeDimChecked = false;
		boolean storeDimChecked = false;
		boolean productDimChecked = false;
		System.out.println("dimensions checked: " + dimensions);

		for (String d : dimensions) {
			if (d.equals("time")) {
				timeDimChecked = true;
			} else if (d.equals("store")) {
				storeDimChecked = true;
			} else if (d.equals("product")) {
				productDimChecked = true;
			}
		}

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

		if (timeDimChecked) {
			resultTable += "<td>" + timeAttr + "</td>";
		}
		if (storeDimChecked) {
			resultTable += "<td>" + storeAttr + "</td>";
		}
		if (productDimChecked) {
			resultTable += "<td>" + productAttr + "</td>";
		}

		for (String f : facts) {
			resultTable += "<td>" + f + "</td>";
		}
		resultTable += "</tr>";

		resultTable += "<tr><form action=\"../api/output\" method=\"post\">"
				+ "<input type=\"hidden\" name=\"time_attribute\" value=\"" + timeAttr + "\">"
				+ "<input type=\"hidden\" name=\"store_attribute\" value=\"" + storeAttr + "\">"
				+ "<input type=\"hidden\" name=\"product_attribute\" value=\"" + productAttr + "\">";

		if (timeDimChecked) {
			resultTable += "<td><input type=\"checkbox\" name=\"dimension\" value=\"time\" style=\"display:none;\" checked>"
			+ sliceInput("time", timeAttr) + "</td>";
		}
		if (storeDimChecked) {
			resultTable += "<td><input type=\"checkbox\" name=\"dimension\" value=\"store\" style=\"display:none;\" checked>" 
			+ sliceInput("store", storeAttr) + "</td>";
		}
		if (productDimChecked) {
			resultTable += "<td><input type=\"checkbox\" name=\"dimension\" value=\"product\" style=\"display:none;\" checked>" 
			+ sliceInput("product", productAttr) + "</td>";
		}
		
		resultTable += "<td><input type=\"submit\" value=\"Submit\"></td>"
				+ "</form></tr>";

		for (int i = 0; i < jdbc.getTimeCol().size(); i++) {
			resultTable += "<tr>";
			if (timeDimChecked) {
				resultTable += "<td>" + jdbc.getTimeCol().get(i) + "</td>";
			}
			if (storeDimChecked) {
				resultTable += "<td>" + jdbc.getStoreCol().get(i) + "</td>";
			}
			if (productDimChecked) {
				resultTable += "<td>" + jdbc.getProductCol().get(i) + "</td>";
			}
			resultTable += "<td>" + jdbc.getDollarSalesCol().get(i) + "</td>";
			resultTable += "<td>" + jdbc.getUnitSalesCol().get(i) + "</td>";
			resultTable += "<td>" + jdbc.getDollarCostCol().get(i) + "</td>";
			resultTable += "<td>" + jdbc.getCustomerCountCol().get(i) + "</td>";
			resultTable += "</tr>";
		}
		
		resultTable += "</table>";

		String returnLink = "<a href=\"../index.html\">Return</a>";

		String title = jdbc.getAllParams() + ". Here are the top 100 results.";
		jdbc.reset();
		timeDimChecked = false;
		storeDimChecked = false;
		productDimChecked = false;

		return title + olapTable + resultTable + returnLink;
	}

	public String timeDropdown() {
		String t = "<td>"
				+ "<input type=\"checkbox\" name=\"dimension\" value=\"time\" checked>"
				+ "<select name=\"time_attribute\">"
				+ "<option value=\"day_of_week\">Day of Week</option>"
				+ "<option value=\"day_number_in_month\">Day number in Month</option>"
				+ "<option value=\"quarter\">Quarter</option>"
				+ "<option value=\"year\">Year</option>" + "</select>";
		t += "</td>";
		return t;
	}

	public String storeDropdown() {
		String t = "<td><input type=\"checkbox\" name=\"dimension\" value=\"store\" checked>" 
				+ "<select name=\"store_attribute\">"
				+ "<option value=\"city\">City</option>"
				+ "<option value=\"store_county\">County</option>"
				+ "<option value=\"store_state\">State</option>"
				+ "<option value=\"sales_region\">Region</option>"
				+ "</select>";
		t += "</td>";
		return t;
	}

	public String productDropdown() {
		String t = "<td><input type=\"checkbox\" name=\"dimension\" value=\"product\" checked>" 
				+ "<select name=\"product_attribute\">"
				+ "<option value=\"brand\">Brand</option>"
				+ "<option value=\"subcategory\">Subcategory</option>"
				+ "<option value=\"category\">Category</option>"
				+ "<option value=\"department\">Department</option>"
				+ "</select>";
		t += "</td>";
		return t;
	}

	public String sliceInput(String dimension, String attribute) {
		String dropdown = "";
		switch (dimension) {
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