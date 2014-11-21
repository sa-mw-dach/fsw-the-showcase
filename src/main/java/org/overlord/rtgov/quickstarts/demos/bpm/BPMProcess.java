package org.overlord.rtgov.quickstarts.demos.bpm;

import java.util.ArrayList;
import java.util.List;

public class BPMProcess {
	
	List<BPMTask> tasks;
	Long processId;
	
	
	public BPMProcess(Long processId) {
		tasks = new ArrayList<BPMTask>();
		this.processId = processId;
	}
	
	public void addTask(BPMTask task) {
		tasks.add(task);
	}
	
	public List<BPMTask> getTasks() {
		return tasks;
	}
	
	public BPMTask getTask(Long id) {
		if(tasks.contains(id)) 
			return tasks.get(tasks.indexOf(id));
		
		return null;			
	}
	
	public String toJSON() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("{");
		
		buffer.append("\"processId\":");
		buffer.append(processId);
		buffer.append(",\"tasks\":[");
		
		for(BPMTask task : tasks)
			buffer.append(task.toJSON()+",");
		
		if(buffer.length() > 0)
			buffer.deleteCharAt(buffer.lastIndexOf(","));
		
		buffer.append("]}");
		
		return buffer.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.equals(processId);
	}
	
	
}
