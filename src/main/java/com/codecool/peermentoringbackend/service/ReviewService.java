package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.ReviewEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.ReviewModel;
import com.codecool.peermentoringbackend.repository.ReviewRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;


    public boolean addNewReview(ReviewModel review, String username) {

        UserEntity reviewedUser = userRepository.findDistinctById(review.getReviewedUser());

        if (reviewedUser == null) return false;

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .rating(review.getRating())
                .review(review.getReview())
                .reviewer(username)
                .reviewedUser(reviewedUser)
                .build();

        reviewRepository.save(reviewEntity);

        return true;
    }

    public List<ReviewEntity> getLoggedUsersReviews(String username) {
        UserEntity user = userRepository.findDistinctByUsername(username);
        return reviewRepository.findAllByReviewedUser(user);
    }
}
