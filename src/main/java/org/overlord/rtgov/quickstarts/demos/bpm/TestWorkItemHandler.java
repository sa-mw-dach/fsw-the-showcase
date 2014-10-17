package org.overlord.rtgov.quickstarts.demos.bpm;

import java.util.logging.Logger;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class TestWorkItemHandler implements WorkItemHandler {
	
	public TestWorkItemHandler() {
	}
	
    private static final Logger LOG	= Logger.getLogger(TestWorkItemHandler.class.getName());

	
	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		LOG.info("--------------> WIH :"+workItem.getName());
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {

	}

}
