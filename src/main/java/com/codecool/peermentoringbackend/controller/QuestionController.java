package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.model.*;
import com.codecool.peermentoringbackend.security.JwtTokenServices;
import com.codecool.peermentoringbackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = {"http://localhost:3000", "https://peermentor-frontend.herokuapp.com/"}, allowCredentials = "true")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private JwtTokenServices jwtTokenServices;

    @GetMapping("")
    public List<QuestionEntity> getAllQuestion(HttpServletRequest request) {
        String usernameFromToken = jwtTokenServices.getUsernameFromToken(request);
        return questionService.getAll(usernameFromToken);
    }


    @PostMapping("")
    public void addQuestion(HttpServletRequest request, HttpServletResponse response, @RequestBody QuestionModel questionModel) throws IOException {

        String username = jwtTokenServices.getUsernameFromToken(request);

        boolean success = questionService.addNewQuestion(questionModel, username);
        if (success) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("user id can't be null");
        }

    }

    @GetMapping("/{questionId}")
    public QuestionEntity getQuestionById(HttpServletRequest request, @PathVariable Long questionId) {
        String usernameFromToken = jwtTokenServices.getUsernameFromToken(request);
        return questionService.getQuestionById(questionId, usernameFromToken);
    }


    @PostMapping("/edit/{questionId}")
    public void editQuestion(HttpServletRequest request, HttpServletResponse response, @RequestBody QModelWithId questionModel, @PathVariable String questionId) throws IOException {
        String usernameFromToken = jwtTokenServices.getUsernameFromToken(request);
        boolean success = questionService.editQuestion(questionModel, Long.parseLong(questionId), usernameFromToken);
        if (success) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("users can only edit their own questions");
        }
    }


    @PutMapping("/vote/{questionId}")
    public void voteQuestion(HttpServletRequest request, HttpServletResponse response, @PathVariable Long questionId) throws IOException {
        String usernameFromToken = jwtTokenServices.getUsernameFromToken(request);
        ApiResponse voteResponse = questionService.vote(questionId, usernameFromToken);
        if (voteResponse.isSuccess()) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }
        response.getWriter().println(voteResponse.getMessage());

    }

    @PostMapping("/add-tech-tag")
    public void addTechTagToQuestion(HttpServletResponse response, @RequestBody QuestionTagModel tagModel) throws IOException {

        boolean success = questionService.addNewTag(tagModel);
        if (success) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("there was an error while adding the tag to the question");
        }
    }

    @PostMapping("/remove-tech-tag")
    public void removeTechTagFromQuestion(HttpServletResponse response, @RequestBody QuestionTagModel tagModel) throws IOException {

        boolean success = questionService.removeTag(tagModel);
        if (success) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("there was an error while removing the tag from the question");
        }
    }

    @DeleteMapping("/delete/{questionId}")
    public ApiResponse deleteQuestion(@PathVariable String questionId){
        // answer model structure is questionable
        var apiResponse = new ApiResponse();
        boolean success = questionService.deleteQuestionAndRelatedAnswers(Long.parseLong(questionId));
        if (success) {
            apiResponse.setSuccess(true);
            apiResponse.setMessage("Deleted question and related answers successfully");
        } else {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Could not delete question");
        }
        return apiResponse;
    }
}
