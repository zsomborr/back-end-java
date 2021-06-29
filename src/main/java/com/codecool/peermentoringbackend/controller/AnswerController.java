package com.codecool.peermentoringbackend.controller;


import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.*;
import com.codecool.peermentoringbackend.repository.UserRepository;
import com.codecool.peermentoringbackend.security.JwtTokenServices;
import com.codecool.peermentoringbackend.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/answers")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private JwtTokenServices jwtTokenServices;


    @GetMapping("/{questionId}")
    public List<AnswerEntity> getAllAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.getAllAnswersByQuestionId(questionId);
    }

    @PostMapping("/add")
    public void addAnswer(HttpServletRequest request, HttpServletResponse response, @RequestBody AnswerModel answerModel) throws IOException {

        String username = jwtTokenServices.getUsernameFromToken(request);

        boolean success = answerService.addNewAnswer(answerModel, username);
        if (success) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("couldn't add answer");
        }

    }

    @PostMapping("/edit/{answerId}")
    public void editAnswer(HttpServletRequest request, HttpServletResponse response, @RequestBody AnswerModel answerModel, @PathVariable String answerId) throws IOException {
        if(answerModel.getContent().isEmpty()){
            response.setStatus(400);
            response.getWriter().println("can't save empty answer");
        } else {
            String usernameFromToken = jwtTokenServices.getUsernameFromToken(request);
            boolean success = answerService.editAnswer(answerModel, Long.parseLong(answerId), usernameFromToken);
            if (success) {
                response.setStatus(200);
            } else {
                response.setStatus(400);
                response.getWriter().println("users can only edit their own answers");
            }
        }
    }


    @PutMapping("/{answerId}/accept")
    public void acceptAnswer(@PathVariable(name = "answerId") Long answerId){
        answerService.acceptAnswer(answerId);
    }
  
    @DeleteMapping("/delete/{answerId}")
    public ApiResponse deleteAnswer(@PathVariable String answerId){
        // answer model structure is questionable
        var apiResponse = new ApiResponse();
        boolean success = answerService.deleteAnswer(Long.parseLong(answerId));
        if (success) {
           apiResponse.setSuccess(true);
           apiResponse.setMessage("Deleted answer successfully");
        } else {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Could not delete answer");
        }
        return apiResponse;
    }
}
