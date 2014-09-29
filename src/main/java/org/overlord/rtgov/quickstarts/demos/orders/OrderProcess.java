package org.overlord.rtgov.quickstarts.demos.orders;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("rest")
public interface OrderProcess {
	
	@GET
	Object start(String message);

	@POST
	@Consumes("application/json")
	String next(String processId);
}
