package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.Module_;
import com.codecool.peermentoringbackend.model.PublicUserModel;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public PublicUserModel getPublicUserDataByUserId(Long userId) {
        UserEntity userEntity = userRepository.findDistinctById(userId);
        PublicUserModel userModel = new PublicUserModel(userEntity.getUsername(), userEntity.getLastName(), userEntity.getFirstName(), userEntity.getCountry(), userEntity.getCity(), userEntity.getModule());
        return userModel;
    }



    @Transactional
    public void savePersonalData(String firstName, String lastName, String country, String city, Module_ module, Long userId){
        Map<String, Object> parameterMap = new HashMap<>();
        List<String> setClause = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE UserEntity u SET  ");

        System.out.println("module: " + module);
        if (!firstName.isEmpty()){
            setClause.add(" u.firstName =:firstName,");
            parameterMap.put("firstName", firstName);
        }
        if (!lastName.isEmpty()){
            setClause.add(" u.lastName =:lastName,");
            parameterMap.put("lastName", lastName);
        }
        if (!country.isEmpty()){
            setClause.add(" u.country =:country,");
            parameterMap.put("country", country);
        }

        if (!city.isEmpty()){
            setClause.add(" u.city =:city,");
            parameterMap.put("city", city);
        }
        if (module!=null){
            setClause.add(" u.module =:module");
            parameterMap.put("module", module);
        }

        queryBuilder.append(String.join("", setClause));
        queryBuilder.append(" WHERE u.id = :userId");
        Query jpaQuery = entityManager.createQuery(queryBuilder.toString());
        jpaQuery.setParameter("userId", userId);

        for(String key :parameterMap.keySet()) {
            jpaQuery.setParameter(key, parameterMap.get(key));
        }

        jpaQuery.executeUpdate();

    }

}
