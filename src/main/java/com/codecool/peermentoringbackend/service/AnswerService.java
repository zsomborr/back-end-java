package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.AnswerModel;
import com.codecool.peermentoringbackend.model.QuestionModel;
import com.codecool.peermentoringbackend.repository.AnswerRepository;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AnswerService {

    private AnswerRepository answerRepository;

    private QuestionRepository questionRepository;

    private UserRepository userRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }


    public List<AnswerEntity> getAllAnswersByQuestionId(Long questionId, String userNameFromToken) {
        UserEntity userEntity = userRepository.findDistinctByUsername(userNameFromToken);
        List<AnswerEntity> answerEntities = answerRepository.findAnswerEntitiesByQuestionId(questionId);
        for (AnswerEntity answer: answerEntities) {
            answer.setTransientData();
            if(answer.getUsername().equals(userEntity.getUsername())) answer.setMyAnswer(true);
        }
        return answerEntities;
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
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }


    public boolean editAnswer(AnswerModel answerModel, long answerId, String usernameFromToken) {
        try {
            UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
            UserEntity userWhoAnswered = userRepository.findUserEntityByAnswerId(answerId);

            if (userWhoAnswered.getId().equals(userEntity.getId()) && !answerModel.getContent().isEmpty()) {
                Optional<AnswerEntity> answerOptional = answerRepository.findById(answerId);
                if (answerOptional.isPresent()) {
                    AnswerEntity answerEntity = answerOptional.get();
                    answerEntity.setContent(answerModel.getContent());
                    answerRepository.save(answerEntity);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;

    }

    public void acceptAnswer(Long answerId) {
        Optional<AnswerEntity> answerEntityOptional = answerRepository.findById(answerId);
        if (answerEntityOptional.isPresent()) {
            AnswerEntity answerEntity = answerEntityOptional.get();
            answerEntity.setAccepted(!answerEntity.isAccepted());
            Optional<AnswerEntity> acceptedOptional = answerRepository.findAnswerEntityByAcceptedTrue();
            if (acceptedOptional.isPresent()) {
                AnswerEntity currentAcceptedAnswer = acceptedOptional.get();
                currentAcceptedAnswer.setAccepted(false);
                answerRepository.save(currentAcceptedAnswer);
            }
            answerRepository.save(answerEntity);
        } else {
            throw new NoSuchElementException("Answer with id not found: " + answerId);
        }
    }

    public boolean deleteAnswer(long answerId) {
        Optional<AnswerEntity> answerOptional = answerRepository.findById(answerId);
        if (answerOptional.isPresent()) {
            answerRepository.deleteById(answerId);
            return true;
        } else {
            return false;
        }
    }
}
