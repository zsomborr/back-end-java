package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.model.QuestionModel;
import com.codecool.peermentoringbackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void addQuestion(@RequestBody QuestionModel questionModel) {
        questionService.addNewQuestion(questionModel);
    }

    @GetMapping("/{questionId}")
    public QuestionEntity getQuestionById(@PathVariable Long questionId) {
        return questionService.getQuestionById(questionId);
    }

}
