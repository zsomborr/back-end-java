package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.repository.ProjectTagRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class TagService {

    @Autowired
    private ProjectTagRepository projectTagRepository;

    @Autowired
    private TechnologyTagRepository technologyTagRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean addNewProjectTag(ProjectEntity tag, String username) {
        boolean b = projectTagRepository.existsProjectEntityByProjectTag(tag.getProjectTag());

        UserEntity user = userRepository.findDistinctByUsername(username);

        if(b) {
            ProjectEntity tagToAdd = projectTagRepository.findProjectEntityByProjectTag(tag.getProjectTag());
            tagToAdd.addUser(user);
            projectTagRepository.save(tagToAdd);
            return true;
        } else {
            ProjectEntity newTag = ProjectEntity.builder()
                    .projectTag(tag.getProjectTag())
                    .userEntities(new HashSet<>())
                    .build();
            newTag.addUser(userRepository.findDistinctByUsername(username));
            projectTagRepository.save(newTag);
            return true;
        }

    }

    public boolean addNewTechnologyTag(TechnologyEntity tag, String username) {
        boolean b = technologyTagRepository.existsTechnologyEntityByTechnologyTag(tag.getTechnologyTag());

        UserEntity user = userRepository.findDistinctByUsername(username);

        if(b) {
            TechnologyEntity tagToAdd = technologyTagRepository.findTechnologyEntityByTechnologyTag(tag.getTechnologyTag());
            tagToAdd.addUser(user);
            technologyTagRepository.save(tagToAdd);
            return true;
        } else {
            TechnologyEntity newTag = TechnologyEntity.builder()
                    .technologyTag(tag.getTechnologyTag())
                    .userEntities(new HashSet<>())
                    .build();
            newTag.addUser(userRepository.findDistinctByUsername(username));
            technologyTagRepository.save(newTag);
            return true;
        }

    }
}
