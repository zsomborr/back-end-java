package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.QModelWithId;
import com.codecool.peermentoringbackend.model.ApiResponse;
import com.codecool.peermentoringbackend.repository.AnswerRepository;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class QuestionServiceTest {

    @Autowired
    private QuestionRepository questionRepository;

    private QuestionService questionService;

    @MockBean
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private TagService tagService;

    @MockBean
    private TechnologyTagRepository technologyTagRepository;

    private UserEntity userEntity;
    private UserEntity voteUserEntity;

    private QuestionEntity questionEntity;
    private QuestionEntity questionEntityTwo;

    private AnswerEntity answerEntity;

    private QModelWithId questionModelTitle;
    private QModelWithId questionModelDesc;
    private QModelWithId questionModelEmpty;
    private QModelWithId questionModelIdNotFound;

    @BeforeEach
    public void init(){
        ModelMapper modelMapper = new ModelMapper();
        questionService = new QuestionService(questionRepository, answerRepository, userRepository, technologyTagRepository, tagService, modelMapper);

        userEntity = UserEntity.builder().username("testuser").email("testuser@email.com").password("password").build();
        voteUserEntity = UserEntity.builder().username("voteuser").email("voteuser@email.com").password("password").votedQuestions(new HashSet<>()).build();

        questionEntity = QuestionEntity.builder().title("title").description("description").user(userEntity).vote(0L).voters(new HashSet<>()).build();
        questionEntityTwo = QuestionEntity.builder().title("title").description("description").user(userEntity).vote(0L).voters(new HashSet<>()).build();

        questionEntity = questionRepository.save(questionEntity);
        questionEntityTwo = questionRepository.save(questionEntityTwo);

        answerEntity = AnswerEntity.builder().user(userEntity).question(questionEntity).build();

        answerRepository.save(answerEntity);

        Set<QuestionEntity> questions = new HashSet<>();
        questions.add(questionEntity);
        userEntity.setQuestions(questions);


        userEntity = userRepository.save(userEntity);
        voteUserEntity = userRepository.save(voteUserEntity);

        questionModelTitle = QModelWithId.builder().id(questionEntity.getId()).title("modifiedTitle").description("description").build();
        questionModelDesc = QModelWithId.builder().id(questionEntity.getId()).title("title").description("modifiedDescription").build();
        questionModelEmpty = QModelWithId.builder().id(questionEntity.getId()).title("").description("").build();
        questionModelIdNotFound = QModelWithId.builder().id(0L).title("modifiedTitle").description("modifiedDescription").build();
    }

    @Test
    public void editQuestion_modifyTitle_returnsTrueAndQuestionIsModified(){
        boolean isEdited = questionService.editQuestion(questionModelTitle, questionModelTitle.getId(), userEntity.getUsername());
        assertTrue(isEdited);
        Optional<QuestionEntity> questionOptional = questionRepository.findById(questionModelTitle.getId());
        QuestionEntity questionEntity = questionOptional.orElseThrow(NoSuchElementException::new);
        assertEquals(questionModelTitle.getTitle(), questionEntity.getTitle());
    }

    @Test
    public void editQuestion_modifyDesc_returnsTrueAndQuestionIsModified(){
        boolean isEdited = questionService.editQuestion(questionModelDesc, questionModelDesc.getId(), userEntity.getUsername());
        assertTrue(isEdited);
        Optional<QuestionEntity> questionOptional = questionRepository.findById(questionModelDesc.getId());
        QuestionEntity questionEntity = questionOptional.orElseThrow(NoSuchElementException::new);
        assertEquals(questionModelDesc.getDescription(), questionEntity.getDescription());
    }

    @Test
    public void editQuestion_emptyQuestionModel_returnsFalse(){
        boolean isEdited = questionService.editQuestion(questionModelEmpty, questionModelEmpty.getId(), userEntity.getUsername());
        assertFalse(isEdited);
    }

    @Test
    public void editQuestion_questionModelWithIdNotFound_returnsFalse(){
        boolean isEdited = questionService.editQuestion(questionModelIdNotFound, questionModelIdNotFound.getId(), userEntity.getUsername());
        assertFalse(isEdited);
    }

//    @Test
//    public void vote_successFullVoteUp_returnsTrue(){
//        Vote vote = new Vote(1L);
//        ApiResponse apiResponse = questionService.vote(vote, questionEntity.getId(), voteUserEntity.getUsername());
//        assertTrue(apiResponse.isSuccess());
//    }
//
//    @Test
//    public void vote_successFullVoteUp_votesIncreasedByOne(){
//        Vote vote = new Vote(1L);
//        ApiResponse apiResponse = questionService.vote(vote, questionEntity.getId(), voteUserEntity.getUsername());
//        assertTrue(apiResponse.isSuccess());
//        Optional<QuestionEntity> questionOptional = questionRepository.findById(questionEntity.getId());
//        QuestionEntity questionEntity = questionOptional.orElseThrow(NoSuchElementException::new);
//        assertEquals(1L, questionEntity.getVote());
//    }

//    @Test
//    public void vote_sameUser_returnsFalse(){
//        Vote vote = new Vote(1L);
//        ApiResponse apiResponse = questionService.vote(vote, questionEntity.getId(), userEntity.getUsername());
//        assertFalse(apiResponse.isSuccess());
//    }

    @Test
    public void deleteQuestionAndRelatedAnswers_deleteQuestionWithAllAnswers_returnTrue(){
        boolean isDeleted = questionService.deleteQuestionAndRelatedAnswers(questionEntity.getId());
        assertThat(isDeleted).isTrue();

    }

    @Test
    public void deleteQuestionAndRelatedAnswers_deleteQuestionWithNoAnswers_returnTrue(){
        boolean isDeleted = questionService.deleteQuestionAndRelatedAnswers(questionEntityTwo.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    public void deleteQuestionAndRelatedAnswers_noQuestionFound_returnFalse(){
        boolean isDeleted = questionService.deleteQuestionAndRelatedAnswers(questionEntity.getId() + 1231231312);
        assertThat(isDeleted).isFalse();
    }

}