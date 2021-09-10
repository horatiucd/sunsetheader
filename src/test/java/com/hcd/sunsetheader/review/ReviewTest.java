package com.hcd.sunsetheader.review;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hcd.sunsetheader.review.Review.Status;

class ReviewTest {

	private Review review;
	
	@BeforeEach
	public void setUp() {
		review = new Review();
	}
	
	@Test
	void sunsetDate_Draft() {
		LocalDateTime dateCreated = LocalDateTime.now();
		review.setStatus(Status.DRAFT);
		review.setDateCreated(dateCreated);
		
		Optional<LocalDateTime> sunsetDate = review.sunsetDate();
		Assertions.assertTrue(sunsetDate.isPresent());
		Assertions.assertEquals(dateCreated.plusDays(2), sunsetDate.get());
	}
	
	@Test
	void sunsetDate_Cancelled() {
		LocalDateTime dateCancelled = LocalDateTime.now();
		review.setStatus(Status.CANCELLED);
		review.setDateCancelled(dateCancelled);
		
		Optional<LocalDateTime> sunsetDate = review.sunsetDate();
		Assertions.assertTrue(sunsetDate.isPresent());
		Assertions.assertEquals(dateCancelled.plusYears(1), sunsetDate.get());
	}
	
	@Test
	void sunsetDate_NotPresent() {
		Assertions.assertTrue(review.sunsetDate().isEmpty());
		
		review.setStatus(Status.OPEN);
		Assertions.assertTrue(review.sunsetDate().isEmpty());
		
		review.setStatus(Status.CLOSED);
		Assertions.assertTrue(review.sunsetDate().isEmpty());		
	}
}
