package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilterService {

    private UserRepository userRepository;

    private QuestionRepository questionRepository;

    private TechnologyTagRepository technologyTagRepository;

    @Autowired
    public FilterService(UserRepository userRepository, QuestionRepository questionRepository, TechnologyTagRepository technologyTagRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.technologyTagRepository = technologyTagRepository;
    }

    public List<UserEntity> getAllMentors() {
        List<UserEntity> techOrProjectTags = userRepository.getIfHasProjectOrTechTags();
        if(!techOrProjectTags.isEmpty()){
            return techOrProjectTags;
        } else {
            return new ArrayList<>();
        }
    }

    public UserEntity getMentorByName(String username) {
        return userRepository.findDistinctByUsername(username);
    }



    public List<UserEntity> getMentorsByAllTags(List<TechnologyEntity> technologies, List<ProjectEntity> projects) {
        Set<TechnologyEntity> technologyEntities = new HashSet<>(technologies);
        Set<ProjectEntity> projectEntities = new HashSet<>(projects);
        List<UserEntity> mentorsByAllTags = new ArrayList<>();
        if(!projects.isEmpty() && !technologies.isEmpty()){
            Optional<List<UserEntity>> distinctByTechnologyTagsAndProjectTags = userRepository.findDistinctByTechnologyTagsInOrProjectTagsIn(technologyEntities, projectEntities);
            if (distinctByTechnologyTagsAndProjectTags.isPresent()){
                mentorsByAllTags = distinctByTechnologyTagsAndProjectTags.get();
            }
        } else if(!projects.isEmpty()) {
            Optional<List<UserEntity>> distinctByProjectTags = userRepository.findDistinctByProjectTagsIn(projectEntities);
            if (distinctByProjectTags.isPresent()){
                mentorsByAllTags = distinctByProjectTags.get();
            }
        } else if(!technologies.isEmpty()){
            Optional<List<UserEntity>> distinctByTechnologyTags = userRepository.findDistinctByTechnologyTagsIn(technologyEntities);
            if (distinctByTechnologyTags.isPresent()) {
                mentorsByAllTags = distinctByTechnologyTags.get();
            }
        } else{
            mentorsByAllTags = userRepository.findAll();
        }
        return mentorsByAllTags;
    }

    public List<UserEntity> filterForAllSpecificTags(List<UserEntity> userEntities,List<TechnologyEntity> technologies, List<ProjectEntity> projects){
        List<UserEntity> results = new ArrayList<>();
        for(UserEntity userEntity : userEntities){
            if(userEntity.getTechnologyTags().containsAll(technologies) && userEntity.getProjectTags().containsAll(projects)){
                results.add(userEntity);
            }
        }
        return results;
    }


    public List<QuestionEntity> filterQuestionsByTags(List<TechnologyEntity> tags) {
        List<QuestionEntity> all = questionRepository.findAllDesc();
        List<QuestionEntity> filtered = new ArrayList<>();
        List<TechnologyEntity> fullTags = new ArrayList<>();

        for ( TechnologyEntity t:tags ) {
            fullTags.add(technologyTagRepository.findTechnologyEntityByTechnologyTag(t.getTechnologyTag()));
        }

        for ( QuestionEntity q : all ) {
            if(q.getTechnologyTags().containsAll(fullTags)){
                q.setUserData();
                q.setNumberOfAnswers(q.getAnswers().size());
                filtered.add(q);
            }
        }
        return filtered;
    }
}
