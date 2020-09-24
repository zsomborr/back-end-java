package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.model.AnswerModel;
import com.codecool.peermentoringbackend.model.QuestionModel;
import com.codecool.peermentoringbackend.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public List<AnswerEntity> getAllAnswersByQuestionId(Long questionId) {
        return answerRepository.findAnswerEntitiesByQuestionId(questionId);
    }

    public boolean addNewAnswer(AnswerModel answerModel) {

        try {
            AnswerEntity answerEntity = AnswerEntity.builder()
                    .userId(answerModel.getUserId())
                    .questionId(answerModel.getQuestionId())
                    .content(answerModel.getContent())
                    .submissionTime(LocalDateTime.now())
                    .build();

            answerRepository.save(answerEntity);
        }
        catch (DataIntegrityViolationException e) {
            return false;
        }
        return true;
    }



}
