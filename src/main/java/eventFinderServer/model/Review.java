package eventFinderServer.model;



import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
(name="ReviewEntity")
public class Review  {
	
	@EmbeddedId
	public ReviewAssociationId reviewId;
    
	private double reviewScore;
	private String text;
	
	public Review(Event e, Customer c) {
		reviewId =  new Review.ReviewAssociationId();
		reviewId.customerId = c.getId();
	    reviewId.eventId = e.getId();
		
	}
	
	
	@ManyToOne
	@JoinColumn(name="eventId",updatable = false, insertable = false, referencedColumnName = "id" )
	
	@JsonIgnore
	private Event event;
	
	
	
	
	@ManyToOne
	@JoinColumn(name="customerId",updatable = false, insertable = false, referencedColumnName = "id")
	@JsonIgnore
	private Customer customer;
	
	
	public Review() {
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

	

	public double getReviewScore() {
		return reviewScore;
	}

	public void setReviewScore(double reviewScore) {
		this.reviewScore = reviewScore;
	}

	
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	

	@Embeddable
	public static class ReviewAssociationId implements Serializable {
		
		/**
		 * 
		 */
		
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
		
		@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	 
	        if (o == null || getClass() != o.getClass())
	            return false;
	 
	        ReviewAssociationId that = (ReviewAssociationId ) o;
	        return Objects.equals(eventId, that.eventId) &&
	               Objects.equals(customerId, that.customerId);
	    }
		
		@Override
		public int hashCode() {
			return Objects.hash(eventId, customerId);
		}
		

	}

	
	
	
}
