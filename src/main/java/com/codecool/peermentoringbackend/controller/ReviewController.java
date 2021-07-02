package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.entity.ReviewEntity;
import com.codecool.peermentoringbackend.model.QuestionModel;
import com.codecool.peermentoringbackend.model.ReviewModel;
import com.codecool.peermentoringbackend.security.JwtTokenServices;
import com.codecool.peermentoringbackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = {"http://localhost:3000", "https://peermentor-frontend.netlify.app", "https://peermentor-analytics.netlify.app"}, allowCredentials = "true")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JwtTokenServices jwtTokenServices;

    @PostMapping("")
    public void addReview(HttpServletRequest request, HttpServletResponse response, @RequestBody ReviewModel review) throws IOException {

        String username = jwtTokenServices.getUsernameFromToken(request);

        boolean success = reviewService.addNewReview(review, username);

        if (success) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("couldn't add review");
        }

    }

    @GetMapping("")
    public List<ReviewEntity> getMyReviews(HttpServletRequest request) {
        String username = jwtTokenServices.getUsernameFromToken(request);
        return reviewService.getReviewsByUsername(username);
    }

    @GetMapping("/id/{userId}")
    public List<ReviewEntity> getReviewsByUserId(@PathVariable Long userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    @GetMapping("/{username}")
    public List<ReviewEntity> getReviewsByUsername(@PathVariable String username) {
        return reviewService.getReviewsByUsername(username);
    }

}
