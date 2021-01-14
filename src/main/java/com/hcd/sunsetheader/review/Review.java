package com.hcd.sunsetheader.review;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Review implements Sunsetable {
	
	private static final int DRAFT_PERIOD = 2; 
	private static final int CANCELLED_PERIOD = 1; 
	
	public enum Status {

		DRAFT, // temporary
		OPEN,  // persistent
		CLOSED, //persistent
		CANCELLED; // retention
	}
	
	@Id
	@GeneratedValue
	private Long id;	
	private String description;
	private Status status;
	
	private LocalDateTime dateCreated;
	private LocalDateTime dateOpened;
	private LocalDateTime dateClosed;
	private LocalDateTime dateCancelled;
					
	public Review() {
		
	}
					
	public Review(Status status, 
			String description, LocalDateTime dateCreated) {
		this.description = description;
		this.status = status;
		this.dateCreated = dateCreated;
	}
			
	@Override
	public Optional<LocalDateTime> sunsetDate() {
		LocalDateTime result = null;
		if (status == Status.DRAFT) {
			result = dateCreated.plusDays(DRAFT_PERIOD);
		} else if (status == Status.CANCELLED) {
			result = dateCancelled.plusYears(CANCELLED_PERIOD);
		}
		return Optional.ofNullable(result);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
		
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
			
	public LocalDateTime getDateOpened() {
		return dateOpened;
	}

	public void setDateOpened(LocalDateTime dateOpened) {
		this.dateOpened = dateOpened;
	}

	public LocalDateTime getDateClosed() {
		return dateClosed;
	}

	public void setDateClosed(LocalDateTime dateClosed) {
		this.dateClosed = dateClosed;
	}

	public LocalDateTime getDateCancelled() {
		return dateCancelled;
	}

	public void setDateCancelled(LocalDateTime dateCancelled) {
		this.dateCancelled = dateCancelled;
	}

	@Override
	public int hashCode() {		
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Review other = (Review) obj;
		if (id == null) { 
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", status=" + status + ", description=" + description + "]";
	}		
}
