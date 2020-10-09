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
import javax.swing.text.html.HTML;
import java.io.IOException;
import java.util.List;

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
            response.getWriter().println("projectTag sould not be null");
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
            response.getWriter().println("technologyTag sould not be null");
        }

    }

    @GetMapping("/get-user-tags")
    public TagsModel getLoggedInUserTags(HttpServletRequest request) {

        String username = jwtTokenServices.getUsernameFromToken(request);

        return tagService.getLoggedInUserTags(username);
    }

    @GetMapping("")
    public TagsModel getAllTags() {
        return tagService.getAllTags();
    }

    @PostMapping("/remove-project-tag")
    public void removeProjectTagFromUser(HttpServletRequest request, HttpServletResponse response, @RequestBody ProjectEntity tag) {

        String username = jwtTokenServices.getUsernameFromToken(request);

        tagService.removeProjectTagFromUser(tag, username);
    }

    @PostMapping("/remove-technology-tag")
    public void removeTechnologyTagFromUser(HttpServletRequest request, HttpServletResponse response, @RequestBody TechnologyEntity tag) {

        String username = jwtTokenServices.getUsernameFromToken(request);

        tagService.removeTechnologyTagFromUser(tag, username);
    }

    @GetMapping("/get-all-tech")
    public List<TechnologyEntity> getAllTechnologyTag() {
        return tagService.getAllTechnologyTag();
    }

}
