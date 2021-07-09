package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.*;
import com.codecool.peermentoringbackend.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filter")
@CrossOrigin(origins = {"http://localhost:3000", "https://peermentor-frontend.netlify.app", "https://peermentor-analytics.netlify.app"}, allowCredentials = "true")
public class FilterController {

    @Autowired
    private FilterService filterService;

    @PostMapping("/get-mentors-by-tags")
    public List<PublicUserModel> getMentorsByTags(@RequestBody ProjectsAndTechs projectsAndTechs) {
        List<UserEntity> mentorsByTags = filterService.getMentorsByAllTags(projectsAndTechs.getTechnologies(), projectsAndTechs.getProjects());
        return filterService.filterForAllSpecificTags(mentorsByTags, projectsAndTechs.getTechnologies(), projectsAndTechs.getProjects());
    }



    @GetMapping("/get-mentors")
    private List<PublicUserModel> getAllMentors(){
        return filterService.getAllMentors();
    }

    @PostMapping("/get-mentor-by-name")
    private PublicUserModel getMentorByName(@RequestBody UserModel userModel){
        return filterService.getMentorByName(userModel.getUsername());
    }

    @PostMapping("/get-questions-by-tags")
    private List<PublicQuestionModel> getQuestionsByTags(@RequestBody TagsModel tags) {

        return filterService.filterQuestionsByTags(tags.getTechnologyTags());
    }



}
