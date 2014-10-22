package org.overlord.rtgov.quickstarts.demos.bpm;

import java.util.ArrayList;
import java.util.List;

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

		List<BPMProcess> processes =  new ArrayList<BPMProcess>();
		
		StringBuffer buffer = new StringBuffer();
		for(TaskSummary task : taskRegistry.getTasksAssignedByGroup("users", "en-UK")) {
			BPMProcess process;
			boolean isOldTask = false;
			boolean isNewTask = true;
			
			if(processes.contains(task.getProcessInstanceId())) 
				process = processes.get(processes.indexOf(task.getProcessInstanceId()));		
			else {
				process = new BPMProcess(task.getProcessInstanceId());
				processes.add(process);
			}
			
			BPMTask bpmTask;
			isOldTask = process.getTasks().contains(task.getId());
			isNewTask = !isOldTask;
			
			if(isOldTask) 
				bpmTask = process.getTask(task.getId());
			else
				bpmTask = new BPMTask(task.getId());
			
			bpmTask.setProperties(task);			
			
			if(isNewTask)
				process.addTask(bpmTask);			
		}
		
		buffer.append("{\"processes\":[");
		
		for(BPMProcess process : processes) {
			buffer.append(process.toJSON());
			buffer.append(",");			
		}
		if(buffer.length() > 0)
			buffer.deleteCharAt(buffer.lastIndexOf(","));
		
		buffer.append("]}");
		return buffer.toString();
	}

	
}
