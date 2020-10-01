package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FilterService {

    @Autowired
    private UserRepository userRepository;

    public boolean isMentor(String username){
        if()
        return true;
    }


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
}
