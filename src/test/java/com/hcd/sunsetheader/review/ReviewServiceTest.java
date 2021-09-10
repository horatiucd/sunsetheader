package com.hcd.sunsetheader.review;

import com.hcd.sunsetheader.exception.EntityNotFoundException;
import com.hcd.sunsetheader.exception.OperationNotAllowedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ReviewServiceTest {

    private ReviewRepository mockRepository;

    private ReviewService reviewService;

    @BeforeEach
    void before() {
        mockRepository = Mockito.mock(ReviewRepository.class);
        reviewService = new ReviewService(mockRepository);
    }

    @Test
    void findAll() {
        final Review review = new Review();
        review.setId(10L);
        final List<Review> expectedReviews = List.of(review);

        when(mockRepository.findAll())
                .thenReturn(expectedReviews);

        List<Review> actualReviews = reviewService.findAll();
        Assertions.assertEquals(expectedReviews, actualReviews);

        verify(mockRepository).findAll();
    }

    @Test
    void findOne_notFound() {
        final long id = 1L;
        when(mockRepository.findById(id))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> reviewService.findOne(id));

        verify(mockRepository).findById(id);
    }

    @Test
    void findOne() {
        final Review expectedReview = new Review();
        expectedReview.setId(10L);

        when(mockRepository.findById(expectedReview.getId()))
                .thenReturn(Optional.of(expectedReview));

        Review actualReview = reviewService.findOne(expectedReview.getId());
        Assertions.assertEquals(expectedReview, actualReview);

        verify(mockRepository).findById(expectedReview.getId());
    }

    @Test
    void open_notAllowed() {
        final long id = 10L;
        final Review reviewToOpen = new Review(Review.Status.OPEN,
                "Open Review", LocalDateTime.now());
        reviewToOpen.setId(id);

        when(mockRepository.findById(reviewToOpen.getId()))
                .thenReturn(Optional.of(reviewToOpen));

        OperationNotAllowedException ex = Assertions.assertThrows(OperationNotAllowedException.class,
                () -> reviewService.open(id));
        Assertions.assertEquals(reviewToOpen.getStatus() + " reviews cannot be opened.", ex.getMessage());

        verify(mockRepository).findById(reviewToOpen.getId());
    }

    @Test
    void open() {
        final Review reviewToOpen = new Review(Review.Status.DRAFT,
                "Review", LocalDateTime.now());
        reviewToOpen.setId(20L);

        when(mockRepository.findById(reviewToOpen.getId()))
                .thenReturn(Optional.of(reviewToOpen));

        when(mockRepository.save(reviewToOpen))
                .thenReturn(reviewToOpen);

        Review review = reviewService.open(reviewToOpen.getId());
        Assertions.assertEquals(Review.Status.OPEN, review.getStatus());
        Assertions.assertNotNull(review.getDateOpened());

        verify(mockRepository).findById(reviewToOpen.getId());
        verify(mockRepository).save(reviewToOpen);
    }
}
