package com.codecool.peermentoringbackend.service;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    @PersistenceContext
    private EntityManager entityManager;

    public List search(List<String> words){
        Map<String, Object> parameterMap = new HashMap<>();
        List<String> whereClause = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT distinct q FROM QuestionEntity q WHERE ");

        for(int i =0; i<words.size(); i++){

            whereClause.add("q.title like " + ":word" + i + " or q.description like " + ":word" + i);
            parameterMap.put("word"+i, "%" +words.get(i) + "%");
        }

        queryBuilder.append(String.join(" or ", whereClause));
        Query jpaQuery = entityManager.createQuery(queryBuilder.toString());

        for(String key :parameterMap.keySet()) {
            jpaQuery.setParameter(key, parameterMap.get(key));

        }

        return jpaQuery.getResultList();

    }
}
