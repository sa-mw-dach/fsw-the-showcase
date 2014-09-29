package org.overlord.rtgov.quickstarts.demos.bpm;

import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.switchyard.component.bpm.runtime.BPMProcessEventListener;
import org.switchyard.event.EventPublisher;

public class BPMListener extends BPMProcessEventListener {

	public BPMListener(EventPublisher eventPublisher) {
		super(eventPublisher);
	}
	
	@Override
	public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
		super.beforeNodeTriggered(event);
	}
	
}
