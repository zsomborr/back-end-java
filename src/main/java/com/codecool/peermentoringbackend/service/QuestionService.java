package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.*;
import com.codecool.peermentoringbackend.repository.AnswerRepository;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.hibernate.PropertyValueException;
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
import java.util.concurrent.RecursiveTask;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<QuestionEntity> getAll() {

        List<QuestionEntity> questionEntities = questionRepository.findAll();

        for (QuestionEntity question : questionEntities) {
            question.setUserData();
        }

        return questionEntities;
    }

    public boolean addNewQuestion(QuestionModel questionModel, String username) {

        try {
            QuestionEntity question = QuestionEntity.builder()
                    .title(questionModel.getTitle())
                    .description(questionModel.getDescription())
                    .submissionTime(LocalDateTime.now())
                    .user(userRepository.findDistinctByUsername(username))
                    .vote((long) 0)
                    .build();

            questionRepository.save(question);
        }
        catch (NullPointerException e) {
            return false;
        }
return true;
    }


    public QAndAsModel getQuestionByIdAndAnswers(Long questionId) {
        List<AnswerEntity> answerEntities = answerRepository.findAnswerEntitiesByQuestionId(questionId);
        QuestionEntity questionEntityById = questionRepository.findQuestionEntityById(questionId);
        questionEntityById.setUserData();

        for (AnswerEntity answer: answerEntities) {
            answer.setTransientData();
        }

        return new QAndAsModel(questionEntityById, answerEntities);
    }


    @Transactional
    public boolean editQuestion(QModelWithId questionModel, UserEntity userEntity, Long questionId) {
        try {
            UserEntity userWhoAskedQ = userRepository.findUserEntityByQuestionId(questionId);

            if(userWhoAskedQ.getId().equals(userEntity.getId())){
                Map<String, Object> parameterMap = new HashMap<>();
                List<String> setClause = new ArrayList<>();

                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("UPDATE QuestionEntity q SET ");

                if (!questionModel.getTitle().isEmpty()){
                    setClause.add(" q.title =:title");
                    parameterMap.put("title", questionModel.getTitle());
                }
                if (!questionModel.getDescription().isEmpty()){
                    setClause.add(" q.description =:description");
                    parameterMap.put("description", questionModel.getDescription());
                }

                queryBuilder.append(String.join(",", setClause));
                queryBuilder.append(" WHERE q.id = :questionId");
                Query jpaQuery = entityManager.createQuery(queryBuilder.toString());
                jpaQuery.setParameter("questionId", questionId);
                for(String key :parameterMap.keySet()) {
                    jpaQuery.setParameter(key, parameterMap.get(key));
                }

                jpaQuery.executeUpdate();
            } else {
                return false;
            }


        } catch (NullPointerException e){
            return false;
        }
        return true;
    }

    @Transactional
    public void vote(Vote vote, Long questionId) {
        Query jpaQuery = entityManager.createQuery("UPDATE QuestionEntity q SET q.vote = q.vote + :vote where q.id = :questionId");
        jpaQuery.setParameter("questionId", questionId);
        jpaQuery.setParameter("vote", vote.getVote());
        jpaQuery.executeUpdate();
    }
}
