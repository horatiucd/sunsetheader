package com.hcd.sunsetheader.review;

import static org.springframework.data.domain.ExampleMatcher.matchingAny;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.hcd.sunsetheader.exception.EntityNotFoundException;
import com.hcd.sunsetheader.exception.OperationNotAllowedException;
import com.hcd.sunsetheader.review.Review.Status;

@Service
public class ReviewService {

	private final ReviewRepository repository;
		
	public ReviewService(ReviewRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	public List<Review> findAll() {
		return repository.findAll();
	}
	
	@Transactional
	public Review findOne(Long id) {
		Optional<Review> entity = repository.findById(id);
		if (entity.isEmpty()) {
			throw new EntityNotFoundException("Review not found.");
		}
		return entity.get();
	}
	
	@Transactional
	public Review open(Long id) {		
		final Review review = findOne(id);
		
		if (review.getStatus() != Status.DRAFT) {
			throw new OperationNotAllowedException(review.getStatus() + " reviews cannot be opened.");
		}
		
		review.setStatus(Status.OPEN);
		review.setDateOpened(LocalDateTime.now());
		
		return repository.save(review);
	}
			
	@Transactional
	public Review close(Long id) {
		final Review review = findOne(id);
		
		if (review.getStatus() != Status.OPEN) {
			throw new OperationNotAllowedException(review.getStatus() + " reviews cannot be closed.");
		}
		
		review.setStatus(Status.CLOSED);
		review.setDateClosed(LocalDateTime.now());
		
		return repository.save(review);
	}
	
	@Transactional
	public Review cancel(Long id) {
		final Review review = findOne(id);
		
		if (review.getStatus() != Status.OPEN) {
			throw new OperationNotAllowedException(review.getStatus() + " reviews cannot be cancelled.");
		}
		
		review.setStatus(Status.CANCELLED);
		review.setDateCancelled(LocalDateTime.now());
		
		return repository.save(review);
	}
	
	@Transactional
	public List<Review> search(String filter) {
		final Review review = new Review();
		review.setDescription(filter);
		
		final ExampleMatcher matcher = matchingAny()
				.withMatcher("description", contains().ignoreCase());
		
		final Example<Review> example = Example.of(review, matcher);
		
	    return repository.findAll(example);
	}
}
