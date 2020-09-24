package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.model.QuestionModel;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public boolean addNewQuestion(QuestionModel questionModel) {

        try {
            QuestionEntity question = QuestionEntity.builder()
                    .title(questionModel.getTitle())
                    .description(questionModel.getDescription())
                    .userId(questionModel.getUserId())
                    .submissionTime(LocalDateTime.now())
                    .build();

            questionRepository.save(question);
        }
        catch (DataIntegrityViolationException e) {
            return false;
        }
return true;
    }

    public QuestionEntity getQuestionById(Long questionId) {
        return questionRepository.findQuestionEntityById(questionId);
    }
}
