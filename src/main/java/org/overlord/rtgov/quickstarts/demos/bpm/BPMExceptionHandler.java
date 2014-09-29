package org.overlord.rtgov.quickstarts.demos.bpm;

import javax.xml.namespace.QName;

import org.jbpm.services.task.wih.ExternalTaskEventListener;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.task.model.Task;
import org.switchyard.component.bpm.runtime.BPMTaskService;
import org.switchyard.component.bpm.runtime.BPMTaskServiceRegistry;

public class BPMExceptionHandler extends ExternalTaskEventListener implements WorkItemHandler {

	private BPMTaskService _taskService;

	public BPMExceptionHandler() {
        _taskService = BPMTaskServiceRegistry.getTaskService(null, new QName("helpdesk", "HelpDeskService"));
        
	}
	
	@Override
	public void afterTaskCompletedEvent(Task task) {
		System.out.println("Task done ...");
		super.afterTaskCompletedEvent(task);		
	}
	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("--- thats a workitem! ---"+workItem.getName());
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub

	}
	
	

}
