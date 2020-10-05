package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.repository.UserRepository;
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

//    public List getMentorsByTags(List<ProjectEntity> projects, List<TechnologyEntity> technologies) {
//        Map<String, Object> parameterMap = new HashMap<>();
//        List<String> whereClause = new ArrayList<>();
//
//        StringBuilder queryBuilder = new StringBuilder();
//        queryBuilder.append("SELECT DISTINCT u FROM UserEntity u JOIN FETCH u.technologyTags t where ");
//
//        for(int i =0; i<technologies.size(); i++){
//
//            whereClause.add("t.technologyTag = " + ":word" + i );
//            parameterMap.put("word"+i, technologies.get(i).toString() );
//        }
//        System.out.println(Arrays.toString(parameterMap.keySet().toArray()));
//
//
//
//        queryBuilder.append(String.join(" and ", whereClause));
//        Query jpaQuery = entityManager.createQuery(queryBuilder.toString());
//
//        for(String key :parameterMap.keySet()) {
//            System.out.println("value: "+parameterMap.get(key));
//            jpaQuery.setParameter(key, parameterMap.get(key));
//        }
//
//        System.out.println(queryBuilder.toString());
//
//        return jpaQuery.getResultList();
//
//
//
//    }


    public List getMentorsByTags(List<ProjectEntity> projects, List<TechnologyEntity> technologies) {
        Map<String, Object> parameterMap = new HashMap<>();
        List<String> whereClause = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT DISTINCT u.id, u.first_name, u.last_name FROM user_entity u INNER JOIN user_entity_technology_tags t on u.id = t.user_entities_id INNER JOIN technology_entity tech on t.technology_tags_id=tech.id WHERE tech.technology_tag in (");

        for(int i =0; i<technologies.size(); i++){

            whereClause.add(":word" + i );
            parameterMap.put("word"+i, technologies.get(i).getTechnologyTag());
        }
//        System.out.println(Arrays.toString(parameterMap.keySet().toArray()));
//
//
//
        queryBuilder.append(String.join(" , ", whereClause));
        queryBuilder.append(")");
        Query jpaQuery = entityManager.createNativeQuery(queryBuilder.toString(), "Proba");

        for(String key :parameterMap.keySet()) {
            jpaQuery.setParameter(key, parameterMap.get(key));
        }

        System.out.println(queryBuilder.toString());

        List<Object[]> authors = jpaQuery.getResultList();
        System.out.println(authors);

        return authors;



    }
}
