package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.ProjectsAndTechs;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filter")
@CrossOrigin(origins = "http://localhost:3000")
public class FilterController {

    @Autowired
    private FilterService filterService;

//    @PostMapping("/get-mentors-by-tags")
//    public List<UserEntity> getMentorsByTags(@RequestBody ProjectsAndTechs projectsAndTechs) {
//
//        return filterService.getMentorsByTags(projectsAndTechs.getProjects(), projectsAndTechs.getTechnologies());
//
//    }


    @GetMapping("/get-mentors")
    private List<UserEntity> getAllMentors(){
        return filterService.getAllMentors();
    }

    @PostMapping("/get-mentor-by-name")
    private UserEntity getMentorByName(@RequestBody UserModel userModel){
        return filterService.getMentorByName(userModel.getUsername());
    }



}
