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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

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


    @Transactional
    public boolean editAnswer(AnswerModel answerModel, UserEntity userEntity, long answerId) {
        try {
            UserEntity userWhoAskedQ = userRepository.findUserEntityByAnswerId(answerId);

            if(userWhoAskedQ.getId().equals(userEntity.getId()) && !answerModel.getContent().isEmpty()){

                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("UPDATE AnswerEntity a SET ");
                queryBuilder.append(" a.content =:content");
                queryBuilder.append(" WHERE a.id = :answerId");
                Query jpaQuery = entityManager.createQuery(queryBuilder.toString());
                jpaQuery.setParameter("answerId", answerId);
                jpaQuery.setParameter("content", answerModel.getContent());
                jpaQuery.executeUpdate();
            } else {
                return false;
            }

        } catch (NullPointerException e){
            return false;
        }
        return true;

    }
}
