package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.model.QandUserModel;
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

    public List<QuestionEntity> search(List<String> words){
        Map<String, Object> parameterMap = new HashMap<>();
        List<String> whereClause = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT distinct q FROM QuestionEntity q  WHERE ");

        for(int i =0; i<words.size(); i++){
            whereClause.add("lower(q.title) like " + "lower(:word" + i + ") or lower(q.description) like " + "lower(:word" + i+ ")");
            parameterMap.put("word"+i, "%" +words.get(i) + "%");
        }

        queryBuilder.append(String.join(" and ", whereClause));
        Query jpaQuery = entityManager.createQuery(queryBuilder.toString());

        for(String key :parameterMap.keySet()) {
            jpaQuery.setParameter(key, parameterMap.get(key));

        }
        List<QuestionEntity> resultList = jpaQuery.getResultList();

        for (QuestionEntity questionEntity: resultList){
            questionEntity.setTransientData();
        }


        return resultList;

    }
}
