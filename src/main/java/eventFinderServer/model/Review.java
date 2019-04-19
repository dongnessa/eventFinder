package eventFinderServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Review {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double reviewScore;
	private String text;
	
	
	@ManyToOne
	@JsonIgnore
	private Event event;
	
	
	
	@ManyToOne
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
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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

	
}
