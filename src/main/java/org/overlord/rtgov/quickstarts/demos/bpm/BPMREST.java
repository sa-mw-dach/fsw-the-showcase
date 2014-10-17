package org.overlord.rtgov.quickstarts.demos.bpm;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("bpm")
public interface BPMREST {

	@GET	
	@Produces(MediaType.APPLICATION_JSON)  	
	String getAllTasks();
}
