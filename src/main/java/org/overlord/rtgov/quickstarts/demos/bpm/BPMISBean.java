package org.overlord.rtgov.quickstarts.demos.bpm;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.namespace.QName;

import org.kie.api.task.model.TaskSummary;
import org.switchyard.component.bean.Service;
import org.switchyard.component.bpm.runtime.BPMTaskService;
import org.switchyard.component.bpm.runtime.BPMTaskServiceRegistry;

@ManagedBean(name="adminDesk")
@SessionScoped
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
		if(buffer.length() > 0)
			buffer.deleteCharAt(buffer.lastIndexOf(","));

		buffer.insert(0,"{'processIds':[");
		buffer.append("]}");
		
		return buffer.toString();
	}

	
}
