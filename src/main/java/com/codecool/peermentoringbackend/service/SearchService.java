package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class SearchService {

    private QuestionRepository questionRepository;

    @Autowired
    public SearchService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionEntity> search(List<String> words){
        Set<QuestionEntity> questionEntitiesSet = new LinkedHashSet<>();
        for (String word : words) {
            Optional<List<QuestionEntity>> distinctByTitleLikeOrDescriptionLike = questionRepository.findDistinctByTitleContainingOrDescriptionContaining(word, word);
            if (distinctByTitleLikeOrDescriptionLike.isPresent()){
                List<QuestionEntity> questionEntities = distinctByTitleLikeOrDescriptionLike.get();
                questionEntitiesSet.addAll(questionEntities);
            }
        }
        return new ArrayList<>(questionEntitiesSet);
    }
}
