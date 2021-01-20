package com.hcd.sunsetheader.review;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.CollectionModel.of;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcd.sunsetheader.event.SunsetEvent;
import com.hcd.sunsetheader.exception.EntityNotFoundException;
import com.hcd.sunsetheader.interceptor.DeprecatedResource;

@RestController
public class ReviewController {
		
	private final ReviewService service;
	private final ReviewModelAssembler assembler;
	private final ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public ReviewController(ReviewService service, 
			ReviewModelAssembler assembler,
			ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
		this.service = service;
		this.assembler = assembler;
	}

	@DeprecatedResource(since = "01 Jan 2021 00:00:00 GMT", 
			alternate = "/reviews/search?filter=pattern",
			policy = "https://technically-correct.eu/deprecation-policy",
			sunset = "31 Dec 2021 23:59:59 GMT")
	@GetMapping("/reviews")
	public ResponseEntity<?> all(HttpServletResponse response) {
		final List<Review> reviews = service.findAll();
				
	    List<EntityModel<Review>> content = reviews.stream()
	    		.map(assembler::toModel)
	    		.collect(toList());
	    
	    Link link = linkTo(methodOn(getClass()).all(response)).withSelfRel();	    
	    
		return ResponseEntity.ok()
				.body(of(content, link));
	}
	
	@GetMapping("/reviews/{id}")
	public ResponseEntity<?> one(HttpServletResponse response, @PathVariable Long id) {
		try {
			Review review = service.findOne(id);
			
			eventPublisher.publishEvent(new SunsetEvent(this, 
					response, review.sunsetDate()));
			
			return ResponseEntity.ok(assembler.toModel(review));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
			
	@PutMapping("/reviews/{id}/open")
	public ResponseEntity<?> open(@PathVariable Long id) {		
		final Review review = service.open(id);
		return ok(assembler.toModel(review));		
	}
	
	@PutMapping("/reviews/{id}/cancel")
	public ResponseEntity<?> cancel(@PathVariable Long id) {
		final Review review = service.cancel(id);
		return ok(assembler.toModel(review));		
	}
	
	@PutMapping("/reviews/{id}/close")
	public ResponseEntity<?> close(@PathVariable Long id) {
		final Review review = service.close(id);
		return ok(assembler.toModel(review));
	}
	
	@GetMapping("/reviews/search")
	public ResponseEntity<?> search(@RequestParam(name = "filter", required = true) String filter) {					
	    final List<Review> reviews = service.search(filter);
	    
	    List<EntityModel<Review>> content = reviews.stream()
	    		.map(assembler::toModel)
	    		.collect(toList());
	    
	    Link link = linkTo(methodOn(getClass()).search(filter)).withSelfRel();
	    	    	    	    
		return ResponseEntity.ok()				
				.body(of(content, link));
	}
}
