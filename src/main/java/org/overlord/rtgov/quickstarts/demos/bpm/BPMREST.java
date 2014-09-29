package org.overlord.rtgov.quickstarts.demos.bpm;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("bpm")
public interface BPMREST {

	@GET
	String getAllTasks();
}
