package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilterService {

    @Autowired
    private UserRepository userRepository;




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

//    public List<UserEntity> getMentorsByTags(List<ProjectEntity> projects, List<TechnologyEntity> technologies) {
//        Map<String, Object> parameterMap = new HashMap<>();
//        List<String> whereClause = new ArrayList<>();
//
//        StringBuilder queryBuilder = new StringBuilder();
//        queryBuilder.append("SELECT distinct u FROM UserEntity u inner JOIN u.technologyTags t inner JOIN u.projectTags p WHERE t in :techTags a");
//
//        for(int i =0; i<projects.size(); i++){
//
//            whereClause.add("t in " + "lower(:word" + i + ") or lower(q.description) like " + "lower(:word" + i+ ")");
//            parameterMap.put("word"+i, projects.get(i) );
//        }
//
//
//
//    }
}
