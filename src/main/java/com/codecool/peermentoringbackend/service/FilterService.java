package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.UserModel;
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


    public List<UserEntity> getAllMentors() {
        List<UserEntity> techOrProjectTags = userRepository.getIfHasProjectOrTechTags();
        if(!techOrProjectTags.isEmpty()){
            return techOrProjectTags;
        } else {
            throw new NoSuchElementException();
        }
    }

    public UserEntity getMentorByName(String username) {
        return userRepository.findDistinctByUsername(username);
    }

    public List getMentorsByTechTags(List<TechnologyEntity> technologies) {
        Map<String, Object> parameterMap = new HashMap<>();
        List<String> whereClause = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT DISTINCT u FROM UserEntity u LEFT JOIN  u.technologyTags t LEFT JOIN u.projectTags p where t.technologyTag in (");

        for(int i =0; i<technologies.size(); i++){

            whereClause.add( ":word" + i );
            parameterMap.put("word"+i, technologies.get(i).getTechnologyTag() );
        }
        System.out.println(Arrays.toString(parameterMap.keySet().toArray()));

        queryBuilder.append(String.join(" , ", whereClause));
        queryBuilder.append(")");
        Query jpaQuery = entityManager.createQuery(queryBuilder.toString());

        for(String key :parameterMap.keySet()) {
            System.out.println("value: "+parameterMap.get(key));
            jpaQuery.setParameter(key, parameterMap.get(key));
        }

        System.out.println(queryBuilder.toString());

        return jpaQuery.getResultList();

    }

    public List getMentorsByProjectTags(List<ProjectEntity> projects) {
        Map<String, Object> parameterMap = new HashMap<>();
        List<String> whereClause = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT DISTINCT u FROM UserEntity u LEFT JOIN  u.technologyTags t LEFT JOIN  u.projectTags p where p.projectTag in (");

        for(int i =0; i<projects.size(); i++){

            whereClause.add( ":word" + i );
            parameterMap.put("word"+i, projects.get(i).getProjectTag() );
        }

        queryBuilder.append(String.join(" , ", whereClause));
        queryBuilder.append(")");
        Query jpaQuery = entityManager.createQuery(queryBuilder.toString());

        for(String key :parameterMap.keySet()) {
            jpaQuery.setParameter(key, parameterMap.get(key));
        }

        return jpaQuery.getResultList();

    }

    public List<UserEntity> filterForSpecificTags(List<UserEntity> userEntitiesByTechTags,List<UserEntity> userEntitiesByProjectTags, List<TechnologyEntity> technologies, List<ProjectEntity> projects){
        Set<UserEntity> set = new LinkedHashSet<>(userEntitiesByTechTags);
        set.addAll(userEntitiesByProjectTags);
        List<UserEntity> combinedList = new ArrayList<>(set);


        List<UserEntity> results = new ArrayList<>();
        System.out.println("technologies: "+Arrays.toString(technologies.toArray()));
       for(UserEntity userEntity : userEntitiesByTechTags){
           System.out.println("techtags of userEntities: "+Arrays.toString(userEntity.getTechnologyTags().toArray()));
           if(userEntity.getTechnologyTags().containsAll(technologies) && userEntity.getProjectTags().containsAll(projects)){
               results.add(userEntity);
           }
       }
       return results;
    }



}
