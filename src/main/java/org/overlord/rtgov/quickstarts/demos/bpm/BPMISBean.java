package org.overlord.rtgov.quickstarts.demos.bpm;

import java.util.List;

import javax.xml.namespace.QName;

import org.kie.api.task.model.TaskSummary;
import org.switchyard.component.bean.Service;
import org.switchyard.component.bpm.runtime.BPMTaskService;
import org.switchyard.component.bpm.runtime.BPMTaskServiceRegistry;

@Service(BPMIS.class)
public class BPMISBean implements BPMIS {

	private BPMTaskService taskRegistry;

	@Override
	public String getAllTasks() {
		taskRegistry = BPMTaskServiceRegistry.getTaskService(null, new QName("urn:switchyard-quickstart-demo:orders:0.1.0","OrderProcess"));				
		
		StringBuffer buffer = new StringBuffer();
		for(TaskSummary task : taskRegistry.getTasksAssignedByGroup("users", "en-UK")) {
			buffer.append("'"+task.getProcessInstanceId()+"',");
		}			
		/*
		for(TaskSummary task : taskRegistry.getActiveTasks()) {
			buffer.append("'"+task.getProcessInstanceId()+"',");
		}			
		for(TaskSummary task : taskRegistry.getArchivedTasks()) {
			buffer.append("'"+task.getProcessInstanceId()+"',");
		}			
		for(TaskSummary task : taskRegistry.getCompletedTasks()) {
			buffer.append("'"+task.getProcessInstanceId()+"',");
		}			
		for(TaskSummary task : taskRegistry.getTasksAssignedAsBusinessAdministrator("kai", "en-UK")) {
			buffer.append("'"+task.getProcessInstanceId()+"',");
		}			
		for(TaskSummary task : taskRegistry.getTasksAssignedAsPotentialOwner("kai", "en-UK")) {
			buffer.append("'"+task.getProcessInstanceId()+"',");
		}			
		for(TaskSummary task : taskRegistry.getTasksAssignedAsExcludedOwner("kai", "en-UK")) {
			buffer.append("'"+task.getProcessInstanceId()+"',");
		}
		*/
		if(buffer.length() > 0)
			buffer.deleteCharAt(buffer.lastIndexOf(","));

		buffer.insert(0,"{'processIds':[");
		buffer.append("]}");
		
		return buffer.toString();
	}

	
}
