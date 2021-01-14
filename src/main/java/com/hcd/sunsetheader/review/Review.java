package com.hcd.sunsetheader.review;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Review implements Sunsetable {
					
	@Id
	@GeneratedValue
	private Long id;
	private String description;
	private Status status;
	
	private LocalDateTime dateCreated;
	private LocalDateTime dateOpened;
	private LocalDateTime dateClosed;
	private LocalDateTime dateCancelled;
							
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
			result = dateCreated.plusDays(2);
		} else if (status == Status.CANCELLED) {
			result = dateCancelled.plusYears(1);
		}
		return Optional.ofNullable(result);
	}
	
	public enum Status {
		DRAFT, OPEN, CLOSED, CANCELLED;
	}
}
