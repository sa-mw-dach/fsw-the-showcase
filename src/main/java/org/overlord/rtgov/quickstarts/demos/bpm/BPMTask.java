package org.overlord.rtgov.quickstarts.demos.bpm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.kie.api.task.model.Status;
import org.kie.api.task.model.TaskSummary;
import org.kie.api.task.model.User;

public class BPMTask {

	private Long taskId;
	private String subject;
	private Status status;
	private int priority;
	private List<String> potentialOwners;
	private String name;
	private Date expirationTime;
	private String description;
	private Date createdOn;
	private User createdBy;
	private User actualOwner;
	private Date activationTime;
	
	public BPMTask(Long taskId) {
		this.taskId = taskId;
	}
	
	public String toJSON() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");

		buffer.append("\"taskId\":");
		buffer.append(taskId);
		buffer.append(",");
		
		buffer.append("\"subject\":\"");
		buffer.append(subject);
		buffer.append("\",");
		
		buffer.append("\"description\":\"");
		buffer.append(description);
		buffer.append("\",");
		
		buffer.append("\"status\":\"");
		buffer.append(status.toString());
		buffer.append("\",");

		buffer.append("\"priority\":\"");
		buffer.append(priority);
		buffer.append("\",");
		
		buffer.append("\"name\":\"");
		buffer.append(name);
		buffer.append("\",");
		
		if(expirationTime != null) {
			buffer.append("\"expirationTime\":\"");
			buffer.append(new SimpleDateFormat().format(expirationTime));
			buffer.append("\",");			
		}
		
		if(createdOn != null) {
			buffer.append("\"createdOn\":\"");
			buffer.append(new SimpleDateFormat().format(createdOn));
			buffer.append("\",");			
		}
		
		if(activationTime != null) {
			buffer.append("\"activationTime\":\"");
			buffer.append(new SimpleDateFormat().format(activationTime));
			buffer.append("\",");			
		}
		
		if(createdBy != null) {
			buffer.append("\"createdBy\":\"");
			buffer.append(createdBy.getId());
			buffer.append("\",");			
		}
		
		if(actualOwner != null) {
			buffer.append("\"actualOwner\":\"");
			buffer.append(actualOwner.getId());
			buffer.append("\",");			
		}
		
		buffer.append("\"potentialOwners\":");
		buffer.append("[");
		if(potentialOwners != null) {
			for(String powner : potentialOwners) {
				buffer.append("\"");
				buffer.append(powner);
				buffer.append("\"");
				buffer.append(",");
			}
			if(buffer.length() > 0)
				buffer.deleteCharAt(buffer.lastIndexOf(","));			
		}
		buffer.append("]");
		
		buffer.append("}");
		
		return buffer.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return obj.equals(taskId);
	}

	public Long getId() {
		return taskId;
	}

	public void setProperties(TaskSummary task) {
		this.activationTime 	= task.getActivationTime();
		this.actualOwner 		= task.getActualOwner();
		this.createdBy 			= task.getCreatedBy();
		this.createdOn 			= task.getCreatedOn();
		this.description 		= task.getDescription();
		this.expirationTime 	= task.getExpirationTime();
		this.name 				= task.getName();
		this.potentialOwners 	= task.getPotentialOwners();
		this.priority 			= task.getPriority();
		this.status 			= task.getStatus();
		this.subject 			= task.getSubject();
	}

	public Long getTaskId() {
		return taskId;
	}

	public String getSubject() {
		return subject;
	}

	public Status getStatus() {
		return status;
	}

	public int getPriority() {
		return priority;
	}

	public List<String> getPotentialOwners() {
		return potentialOwners;
	}

	public String getName() {
		return name;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public User getActualOwner() {
		return actualOwner;
	}

	public Date getActivationTime() {
		return activationTime;
	}
	
	

}
