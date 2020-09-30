package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.model.DataModel;
import com.codecool.peermentoringbackend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "http://localhost:3000")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("")
    public List search(HttpServletRequest request, HttpServletResponse response, @RequestBody DataModel data) {
        return searchService.search(Arrays.asList(data.getData().split(" ")));
    }
}
