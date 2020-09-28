package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.model.QAndAsModel;
import com.codecool.peermentoringbackend.model.QuestionModel;
import com.codecool.peermentoringbackend.model.RegResponse;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("")
    public List<QuestionEntity> getAllNotes() {
        return questionService.getAll();
    }


    @PostMapping("")
    public void addQuestion(HttpServletResponse response, @RequestBody QuestionModel questionModel) throws IOException {

        boolean success = questionService.addNewQuestion(questionModel);
        if (success) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("user id can't be null");
        }

    }


    @GetMapping("/{questionId}")
    public QAndAsModel getQuestionByIdAndAnswers(@PathVariable Long questionId) {
        return questionService.getQuestionByIdAndAnswers(questionId);
    }

}
