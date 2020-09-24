package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.model.QuestionModel;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionEntity> getAll() {
        return questionRepository.findAll();
    }

    public void addNewQuestion(QuestionModel questionModel) {
        QuestionEntity question = QuestionEntity.builder()
                .title(questionModel.getTitle())
                .description(questionModel.getDescription())
                .userId(1L)
                .submissionTime(LocalDateTime.now())
                .build();

        questionRepository.save(question);
    }
}
