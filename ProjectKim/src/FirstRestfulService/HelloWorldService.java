package FirstRestfulService;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
 
@Path("/output")
public class HelloWorldService {
	
	private JDBCExample jdbc = new JDBCExample();
	
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	public String olapOperation(@FormParam("operation") String op, 
						   		@FormParam("attribute") String attr) {
		
		String choices = "OLAP operation " + op + " on attribute " + attr + "<br>";
		String html = "";
		
		switch(op){
			case "base":
				ArrayList<String> userNames = jdbc.getUserIds();
				ArrayList<String> totals = jdbc.getTotals();
				html = "<table border=\"1\">"
						+ "<tr>"
						+ "<td>Name</td>"
						+ "<td>Totals</td>"
						+ "</tr>";
				for(int i = 0; i < userNames.size(); i++){
					html += "<tr>"
							+ "<td>" + userNames.get(i) + "</td>"
							+ "<td>" + totals.get(i) + "</td>"
							+ "</tr>";
				}
				html += "</table>";
				break;
			case "rollup":
				break;
			case "drilldown":
				break;
			case "slice":
				break;
			case "dice":
				break;
		}
		
		html += "<a href=\"../index.html\">Return</a>";
		
		return choices + html;
	}
	
}