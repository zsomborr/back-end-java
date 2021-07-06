package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.repository.ProjectTagRepository;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.map;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
class FilterServiceTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private QuestionRepository questionRepository;

    @Autowired
    private TechnologyTagRepository technologyTagRepository;

    @Autowired
    private ProjectTagRepository projectTagRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    private MapperService mapperService;

    private FilterService filterService;

    private final List<TechnologyEntity> filledTechnologyEntities = new ArrayList<>();
    private final List<TechnologyEntity> emptyTechnologyEntities = new ArrayList<>();
    private final List<TechnologyEntity> notOwnedTechnologyEntities = new ArrayList<>();

    private final List<ProjectEntity> filledProjectEntities = new ArrayList<>();
    private final List<ProjectEntity> emptyProjectEntities = new ArrayList<>();
    private final List<ProjectEntity> notOwnedProjectEntities = new ArrayList<>();

    private TechnologyEntity technologyEntityOne;
    private TechnologyEntity technologyEntityTwo;
    private TechnologyEntity technologyEntityThree;
    private TechnologyEntity technologyEntityFour;

    private ProjectEntity projectEntityOne;
    private ProjectEntity projectEntityTwo;
    private ProjectEntity projectEntityThree;

    private UserEntity userOne;
    private UserEntity userTwo;
    private UserEntity userThree;
    private UserEntity userFour;

    @BeforeEach
    public void setup(){
        mapperService = new MapperService(modelMapper);
        filterService = new FilterService(userRepository, questionRepository, technologyTagRepository, userService, mapperService);

        technologyEntityOne = TechnologyEntity.builder().technologyTag("tech1").build();
        technologyEntityTwo = TechnologyEntity.builder().technologyTag("tech2").build();
        technologyEntityThree = TechnologyEntity.builder().technologyTag("tech3").build();
        technologyEntityFour = TechnologyEntity.builder().technologyTag("no").build();

        projectEntityOne = ProjectEntity.builder().projectTag("proj1").build();
        projectEntityTwo = ProjectEntity.builder().projectTag("proj2").build();
        projectEntityThree = ProjectEntity.builder().projectTag("no").build();

        filledTechnologyEntities.addAll(Arrays.asList(technologyEntityOne, technologyEntityTwo));
        filledProjectEntities.addAll(Arrays.asList(projectEntityOne, projectEntityTwo));

        notOwnedTechnologyEntities.add(technologyEntityFour);
        notOwnedProjectEntities.add(projectEntityThree);


        userOne = UserEntity.builder()
                .username("userOne")
                .email("testuser1@email.com")
                .password("password")
                .technologyTags(new HashSet<>(Collections.singleton(technologyEntityOne)))
                .projectTags(new HashSet<>(Collections.singleton(projectEntityOne)))
                .build();
        userTwo = UserEntity.builder()
                .username("userTwo")
                .email("testuser2@email.com")
                .password("password")
                .technologyTags(new HashSet<>(Collections.singleton(technologyEntityTwo)))
                .build();
        userThree = UserEntity.builder()
                .username("userThree")
                .email("testuser3@email.com")
                .password("password")
                .projectTags(new HashSet<>(Collections.singleton(projectEntityTwo)))
                .build();
        userFour = UserEntity.builder()
                .username("userFour")
                .email("testuser4@email.com")
                .password("password")
                .technologyTags(new HashSet<>(Collections.singleton(technologyEntityThree)))
                .build();

        technologyTagRepository.saveAll(Arrays.asList(technologyEntityOne, technologyEntityTwo, technologyEntityThree, technologyEntityFour));

        projectTagRepository.saveAll(Arrays.asList(projectEntityOne, projectEntityTwo, projectEntityThree));

        userRepository.saveAll(Arrays.asList(userOne, userTwo, userThree, userFour));
    }

    @Test
    public void getMentorsByAllTags_technologyTagsAndProjectTagsAreNotEmpty_returnThreeUser(){
        List<UserEntity> mentorsByAllTags = filterService.getMentorsByAllTags(filledTechnologyEntities, filledProjectEntities);
        assertThat(mentorsByAllTags).containsExactlyInAnyOrder(userOne, userThree, userTwo);
    }

    @Test
    public void getMentorsByAllTags_technologyTagsIsNotEmptyAndProjectTagsIsEmpty_returnThreeUser(){
        List<UserEntity> mentorsByAllTags = filterService.getMentorsByAllTags(filledTechnologyEntities, emptyProjectEntities);
        assertThat(mentorsByAllTags).containsExactlyInAnyOrder(userOne, userTwo);
    }

    @Test
    public void getMentorsByAllTags_projectTagsIsNotEmptyAndTechnologyTagsIsEmpty_returnThreeUser(){
        List<UserEntity> mentorsByAllTags = filterService.getMentorsByAllTags(emptyTechnologyEntities, filledProjectEntities);
        assertThat(mentorsByAllTags).containsExactlyInAnyOrder(userOne, userThree);
    }

    @Test
    public void getMentorsByAllTags_technologyTagsAndProjectTagsIsEmpty_returnThreeUser(){
        List<UserEntity> mentorsByAllTags = filterService.getMentorsByAllTags(emptyTechnologyEntities, emptyProjectEntities);
        assertThat(mentorsByAllTags).containsExactlyInAnyOrder(userOne, userTwo, userThree, userFour);
    }

    @Test
    public void getMentorsByAllTags_noUserFoundWithTechnologiesAndProjects_returnEmptyList(){
        List<UserEntity> mentorsByAllTags = filterService.getMentorsByAllTags(notOwnedTechnologyEntities, notOwnedProjectEntities);
        assertThat(mentorsByAllTags).isEmpty();
    }
}