package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.model.PublicQuestionModel;
import com.codecool.peermentoringbackend.model.PublicUserModel;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class SearchService {

    private QuestionRepository questionRepository;
    private ModelMapper modelMapper;

    @Autowired
    public SearchService(QuestionRepository questionRepository, ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    public List<PublicQuestionModel> search(List<String> words){
        List<PublicQuestionModel> questions = new ArrayList<>();
        for (String word : words) {
            Optional<List<QuestionEntity>> distinctByTitleLikeOrDescriptionLike = questionRepository.findDistinctByTitleContainingOrDescriptionContaining(word, word);
            if (distinctByTitleLikeOrDescriptionLike.isPresent()){
                List<QuestionEntity> questionEntities = distinctByTitleLikeOrDescriptionLike.get();
                for (QuestionEntity questionEntity : questionEntities) {
                    PublicQuestionModel publicQuestionModel = modelMapper.map(questionEntity, PublicQuestionModel.class);
                    publicQuestionModel.setUsername(questionEntity.getUser().getUsername());
                    publicQuestionModel.setUserId(questionEntity.getUser().getId());
                    publicQuestionModel.setNumberOfAnswers(questionEntity.getAnswers().size());
                    questions.add(publicQuestionModel);
                }
            }
        }
        return questions;
    }
}
