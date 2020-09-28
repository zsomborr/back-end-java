package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.model.AnswerModel;
import com.codecool.peermentoringbackend.model.QuestionModel;
import com.codecool.peermentoringbackend.repository.AnswerRepository;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<AnswerEntity> getAllAnswersByQuestionId(Long questionId) {
        return answerRepository.findAnswerEntitiesByQuestionId(questionId);
    }

    public boolean addNewAnswer(AnswerModel answerModel, String username) {

        try {
            AnswerEntity answerEntity = AnswerEntity.builder()
                    .content(answerModel.getContent())
                    .submissionTime(LocalDateTime.now())
                    .user(userRepository.findDistinctByUsername(username))
                    .question(questionRepository.findDistinctById(answerModel.getQuestionId()))
                    .build();

            answerRepository.save(answerEntity);
        }
        catch (NullPointerException e) {
            return false;
        }
        return true;
    }



}
