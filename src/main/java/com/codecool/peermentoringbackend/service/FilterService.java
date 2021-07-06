package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.PublicUserModel;
import com.codecool.peermentoringbackend.model.Rank;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilterService {

    private UserRepository userRepository;

    private QuestionRepository questionRepository;

    private TechnologyTagRepository technologyTagRepository;

    private UserService userService;

    private ModelMapper modelMapper;

    @Autowired
    public FilterService(UserRepository userRepository, QuestionRepository questionRepository, TechnologyTagRepository technologyTagRepository, UserService userService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.technologyTagRepository = technologyTagRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public List<PublicUserModel> getAllMentors() {
        List<UserEntity> techOrProjectTags = userRepository.getIfHasProjectOrTechTags();
        List<PublicUserModel> mentors = new ArrayList<>();
        for(UserEntity userEntity: techOrProjectTags){
            PublicUserModel publicUserModel = modelMapper.map(userEntity, PublicUserModel.class);
            Rank rank = userService.getUserRank(userEntity.getId());
            publicUserModel.setRank(rank);
            mentors.add(publicUserModel);
        }
        return mentors;
    }

    public PublicUserModel getMentorByName(String username) {
        UserEntity userEntity = userRepository.findDistinctByUsername(username);
        PublicUserModel publicUserModel = modelMapper.map(userEntity, PublicUserModel.class);
        Rank rank = userService.getUserRank(userEntity.getId());
        publicUserModel.setRank(rank);
        return publicUserModel;
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

    public List<PublicUserModel> filterForAllSpecificTags(List<UserEntity> userEntities,List<TechnologyEntity> technologies, List<ProjectEntity> projects){
        List<PublicUserModel> results = new ArrayList<>();
        for(UserEntity userEntity : userEntities){
            if(userEntity.getTechnologyTags().containsAll(technologies) && userEntity.getProjectTags().containsAll(projects)){
                PublicUserModel publicUserModel = modelMapper.map(userEntity, PublicUserModel.class);
                Rank rank = userService.getUserRank(userEntity.getId());
                publicUserModel.setRank(rank);
                results.add(publicUserModel);
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
