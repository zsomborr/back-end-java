package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.model.QAndAsModel;
import com.codecool.peermentoringbackend.model.QuestionModel;
import com.codecool.peermentoringbackend.model.TagsModel;
import com.codecool.peermentoringbackend.security.JwtTokenServices;
import com.codecool.peermentoringbackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private JwtTokenServices jwtTokenServices;

    @Autowired
    private TagService tagService;

    @PostMapping("/add-project-tag")
    public void addProjectTagToUser(HttpServletRequest request, HttpServletResponse response, @RequestBody ProjectEntity tag) throws IOException {

        String username = jwtTokenServices.getUsernameFromToken(request);
        boolean success = false;
        if(tag.getProjectTag() != null) success = tagService.addNewProjectTag(tag, username);


        if (success) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("handle error");
        }

    }

    @PostMapping("/add-technology-tag")
    public void addTechnologyTagToUser(HttpServletRequest request, HttpServletResponse response, @RequestBody TechnologyEntity tag) throws IOException {

        String username = jwtTokenServices.getUsernameFromToken(request);

        boolean success = false;
        if (tag.getTechnologyTag() != null) success = tagService.addNewTechnologyTag(tag, username);

        if (success) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("handle error");
        }

    }

    @GetMapping("/get-user-tags")
    public TagsModel getLoggedInUserTags(HttpServletRequest request) {

        String username = jwtTokenServices.getUsernameFromToken(request);

        return tagService.getLoggedInUserTags(username);
    }

}
