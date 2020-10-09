package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.TagsModel;
import com.codecool.peermentoringbackend.repository.ProjectTagRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

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

    public boolean addNewTechnologyTagToQuestion(String tag, QuestionEntity questionEntity) {
        boolean b = technologyTagRepository.existsTechnologyEntityByTechnologyTag(tag);

        if(b) {
            TechnologyEntity tagToAdd = technologyTagRepository.findTechnologyEntityByTechnologyTag(tag);

            technologyTagRepository.save(tagToAdd);
            tagToAdd.addQuestion(questionEntity);
            return true;
        } else {
            TechnologyEntity newTag = TechnologyEntity.builder()
                    .technologyTag(tag)
                    .userEntities(new HashSet<>())
                    .questionEntities(new HashSet<>())
                    .build();

            technologyTagRepository.save(newTag);
            newTag.addQuestion(questionEntity);
            return true;
        }

    }

    public TagsModel getLoggedInUserTags(String username) {

        UserEntity distinctByUsername = userRepository.findDistinctByUsername(username);
        List<ProjectEntity> projectEntitiesByUserEntities = projectTagRepository.findProjectEntitiesByUserEntities(distinctByUsername);
        List<TechnologyEntity> technologyEntitiesByUserEntities = technologyTagRepository.findTechnologyEntitiesByUserEntities(distinctByUsername);
        return new TagsModel(projectEntitiesByUserEntities, technologyEntitiesByUserEntities);
    }

    public TagsModel getAllTags() {

        List<ProjectEntity> projectTags = projectTagRepository.findAll();
        List<TechnologyEntity> technologyTags = technologyTagRepository.findAll();

        return new TagsModel(projectTags, technologyTags);
    }

    public void removeProjectTagFromUser(ProjectEntity tag, String username) {
        ProjectEntity projectEntityByProjectTag = projectTagRepository.findProjectEntityByProjectTag(tag.getProjectTag());
        UserEntity distinctByUsername = userRepository.findDistinctByUsername(username);
        projectEntityByProjectTag.removeUser(distinctByUsername);
        projectTagRepository.save(projectEntityByProjectTag);
    }

    public void removeTechnologyTagFromUser(TechnologyEntity tag, String username) {
        TechnologyEntity technologyEntity = technologyTagRepository.findTechnologyEntityByTechnologyTag(tag.getTechnologyTag());
        UserEntity userEntity = userRepository.findDistinctByUsername(username);
        technologyEntity.removeUser(userEntity);
        technologyTagRepository.save(technologyEntity);
    }
}
