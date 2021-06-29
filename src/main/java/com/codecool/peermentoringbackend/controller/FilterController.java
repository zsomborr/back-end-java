package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.ProjectsAndTechs;
import com.codecool.peermentoringbackend.model.TagsModel;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filter")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class FilterController {

    @Autowired
    private FilterService filterService;

    @PostMapping("/get-mentors-by-tags")
    public List<UserEntity> getMentorsByTags(@RequestBody ProjectsAndTechs projectsAndTechs) {
        List<UserEntity> mentorsByTags = filterService.getMentorsByAllTags(projectsAndTechs.getTechnologies(), projectsAndTechs.getProjects());
        return filterService.filterForAllSpecificTags(mentorsByTags, projectsAndTechs.getTechnologies(), projectsAndTechs.getProjects());
    }



    @GetMapping("/get-mentors")
    private List<UserEntity> getAllMentors(){
        return filterService.getAllMentors();
    }

    @PostMapping("/get-mentor-by-name")
    private UserEntity getMentorByName(@RequestBody UserModel userModel){
        return filterService.getMentorByName(userModel.getUsername());
    }

    @PostMapping("/get-questions-by-tags")
    private List<QuestionEntity> getQuestionsByTags(@RequestBody TagsModel tags) {

        return filterService.filterQuestionsByTags(tags.getTechnologyTags());
    }



}
