package FirstRestfulService;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/hello")
public class HelloWorldService {
	
	private JDBCExample jdbc = new JDBCExample();
 
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String getMsg(@FormParam("operation") String op, 
						   @FormParam("attribute") String attr) {
 
		String output = "OLAP operation " + op + " on attribute " + attr;
		
		String output2 = jdbc.getUserIds();
 
		return output2;
 
	}
	
}