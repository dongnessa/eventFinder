package eventFinderServer.model;

import java.io.Serializable;

public class ReviewAssociationId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String eventId;
	private long customerId;
	
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	
	

}
