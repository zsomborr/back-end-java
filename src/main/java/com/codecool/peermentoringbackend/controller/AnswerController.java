package com.codecool.peermentoringbackend.controller;


import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.model.AnswerModel;
import com.codecool.peermentoringbackend.model.QAndAsModel;
import com.codecool.peermentoringbackend.model.QuestionModel;
import com.codecool.peermentoringbackend.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("/{questionId}")
    public List<AnswerEntity> getAllAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.getAllAnswersByQuestionId(questionId);
    }

    @PostMapping("/add")
    public void addAnswer(HttpServletResponse response, @RequestBody AnswerModel answerModel) throws IOException {

        boolean success = answerService.addNewAnswer(answerModel);
        if (success) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("user id can't be null");
        }

    }
}
