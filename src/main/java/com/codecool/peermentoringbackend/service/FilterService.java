package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.*;

@Service
public class FilterService {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TechnologyTagRepository technologyTagRepository;


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
        Map<String, Object> parameterMap = new HashMap<>();
        List<String> whereClause = new ArrayList<>();
        List<String> whereClause2 = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT DISTINCT u FROM UserEntity u LEFT JOIN  u.technologyTags t LEFT JOIN u.projectTags p where ");

        if(!projects.isEmpty() && !technologies.isEmpty()){
            queryBuilder.append("t.technologyTag in (");

            for(int i =0; i<technologies.size(); i++){
                whereClause.add( ":word" + i );
                parameterMap.put("word"+i, technologies.get(i).getTechnologyTag() );
            }

            queryBuilder.append(String.join(" , ", whereClause));
            queryBuilder.append(") or p.projectTag in (");

            for(int i =technologies.size(); i<projects.size() + technologies.size(); i++){

                whereClause2.add( ":word" + i );
                parameterMap.put("word"+i, projects.get(i-technologies.size()).getProjectTag() );
            }

            queryBuilder.append(String.join(" , ", whereClause2));
            queryBuilder.append(")");
        } else if(!projects.isEmpty()){
            queryBuilder.append("p.projectTag in (");
            for(int i =technologies.size(); i<projects.size() + technologies.size(); i++){

                whereClause2.add( ":word" + i );
                parameterMap.put("word"+i, projects.get(i-technologies.size()).getProjectTag() );
            }

            queryBuilder.append(String.join(" , ", whereClause2));
            queryBuilder.append(")");

        }
        else if(!technologies.isEmpty()){
            queryBuilder.append("t.technologyTag in (");

            for(int i =0; i<technologies.size(); i++){
                whereClause.add( ":word" + i );
                parameterMap.put("word"+i, technologies.get(i).getTechnologyTag() );
            }

            queryBuilder.append(String.join(" , ", whereClause));
            queryBuilder.append(")");
        } else{
            return getAllMentors();
        }


        Query jpaQuery = entityManager.createQuery(queryBuilder.toString());

        for(String key :parameterMap.keySet()) {
            jpaQuery.setParameter(key, parameterMap.get(key));
        }

        return jpaQuery.getResultList();
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
        List<QuestionEntity> all = questionRepository.findAll();
        List<QuestionEntity> filtered = new ArrayList<>();
        List<TechnologyEntity> fullTags = new ArrayList<>();

        for ( TechnologyEntity t:tags ) {
            fullTags.add(technologyTagRepository.findTechnologyEntityByTechnologyTag(t.getTechnologyTag()));
        }

        for ( QuestionEntity q : all ) {
            if(q.getTechnologyTags().containsAll(fullTags)) filtered.add(q);
        }
        return filtered;
    }
}
