package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.PublicAnswerModel;
import com.codecool.peermentoringbackend.model.PublicQuestionModel;
import com.codecool.peermentoringbackend.model.QAndAsModel;
import com.codecool.peermentoringbackend.model.QuestionModel;
import com.codecool.peermentoringbackend.repository.AnswerRepository;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.RecursiveTask;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TechnologyTagRepository technologyTagRepository;

    @Autowired TagService tagService;

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
                    .technologyTags(new HashSet<>())
                    .build();
            questionRepository.save(question);

            List<String> technologyTags = questionModel.getTechnologyTags();

            if (technologyTags != null) {
                for (String tag : technologyTags ) {
                    boolean b = tagService.addNewTechnologyTagToQuestion(tag, question);
                    if (!b) return false;
                    questionRepository.save(question);
                }
            }
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
}
