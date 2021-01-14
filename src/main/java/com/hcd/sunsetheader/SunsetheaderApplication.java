package com.hcd.sunsetheader;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hcd.sunsetheader.review.Review;
import com.hcd.sunsetheader.review.Review.Status;
import com.hcd.sunsetheader.review.ReviewRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SunsetheaderApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SunsetheaderApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initData(ReviewRepository repository) {		
	    return args -> {
	    	final Review draft = new Review(Status.DRAFT, "Draft review.", LocalDateTime.now());
	    	
	    	final Review open = new Review(Status.OPEN, "Open review.", LocalDateTime.now());
	    	open.setDateOpened(open.getDateCreated().plusMinutes(5));
	    	
	    	final Review closed = new Review(Status.CLOSED, "Closed review.", LocalDateTime.now());
	    	closed.setDateOpened(closed.getDateCreated().plusMinutes(10));
	    	closed.setDateClosed(closed.getDateOpened().plusDays(3));
	    	
	    	final Review cancelled = new Review(Status.CANCELLED, "Cancelled review.", LocalDateTime.now());
	    		    	
	    	cancelled.setDateOpened(cancelled.getDateCreated().plusMinutes(15));
	    	cancelled.setDateCancelled(cancelled.getDateCreated().plusMonths(1));
	    		    	
	    	List.of(draft, open, closed, cancelled)
	    		.forEach(review -> log.info("Load " + repository.save(review)));
	    };
	}	
}
