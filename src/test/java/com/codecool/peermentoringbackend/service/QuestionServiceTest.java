package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.QModelWithId;
import com.codecool.peermentoringbackend.repository.AnswerRepository;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
public class QuestionServiceTest {

    @Autowired
    private QuestionRepository questionRepository;

    private QuestionService questionService;

    @MockBean
    private AnswerRepository answerRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TagService tagService;

    @MockBean
    private TechnologyTagRepository technologyTagRepository;

    private UserEntity userEntity;

    private QuestionEntity questionEntity;

    private QModelWithId questionModelTitle;
    private QModelWithId questionModelDesc;
    private QModelWithId questionModelEmpty;
    private QModelWithId questionModelIdNotFound;

    @BeforeEach
    public void init(){
        questionService = new QuestionService(questionRepository, answerRepository, userRepository, technologyTagRepository, tagService);
        userEntity = UserEntity.builder().username("testuser").id(1L).email("testuser@email.com").password("password").build();
        questionEntity = QuestionEntity.builder().title("title").description("description").user(userEntity).build();
        questionEntity = questionRepository.save(questionEntity);
        questionModelTitle = QModelWithId.builder().id(questionEntity.getId()).title("modifiedTitle").description("description").build();
        questionModelDesc = QModelWithId.builder().id(questionEntity.getId()).title("title").description("modifiedDescription").build();
        questionModelEmpty = QModelWithId.builder().id(questionEntity.getId()).title("").description("").build();
        questionModelIdNotFound = QModelWithId.builder().id(0L).title("modifiedTitle").description("modifiedDescription").build();
    }

    @Test
    public void editQuestion_modifyTitle_returnsTrueAndQuestionIsModified(){
        Mockito.when(userRepository.findDistinctByUsername(Mockito.anyString())).thenReturn(userEntity);
        Mockito.when(userRepository.findUserEntityByQuestionId(Mockito.anyLong())).thenReturn(userEntity);
        boolean isEdited = questionService.editQuestion(questionModelTitle, questionModelTitle.getId(), "");
        assertTrue(isEdited);
        Optional<QuestionEntity> questionOptional = questionRepository.findById(questionModelTitle.getId());
        QuestionEntity questionEntity = questionOptional.orElseThrow(NoSuchElementException::new);
        assertEquals(questionModelTitle.getTitle(), questionEntity.getTitle());
    }

    @Test
    public void editQuestion_modifyDesc_returnsTrueAndQuestionIsModified(){
        Mockito.when(userRepository.findDistinctByUsername(Mockito.anyString())).thenReturn(userEntity);
        Mockito.when(userRepository.findUserEntityByQuestionId(Mockito.anyLong())).thenReturn(userEntity);
        boolean isEdited = questionService.editQuestion(questionModelDesc, questionModelDesc.getId(), "");
        assertTrue(isEdited);
        Optional<QuestionEntity> questionOptional = questionRepository.findById(questionModelDesc.getId());
        QuestionEntity questionEntity = questionOptional.orElseThrow(NoSuchElementException::new);
        assertEquals(questionModelDesc.getDescription(), questionEntity.getDescription());
    }

    @Test
    public void editQuestion_emptyQuestionModel_returnsFalse(){
        Mockito.when(userRepository.findDistinctByUsername(Mockito.anyString())).thenReturn(userEntity);
        Mockito.when(userRepository.findUserEntityByQuestionId(Mockito.anyLong())).thenReturn(userEntity);
        boolean isEdited = questionService.editQuestion(questionModelEmpty, questionModelEmpty.getId(), "");
        assertFalse(isEdited);
    }

    @Test
    public void editQuestion_questionModelWithIdNotFound_returnsFalse(){
        Mockito.when(userRepository.findDistinctByUsername(Mockito.anyString())).thenReturn(userEntity);
        Mockito.when(userRepository.findUserEntityByQuestionId(Mockito.anyLong())).thenReturn(userEntity);
        boolean isEdited = questionService.editQuestion(questionModelIdNotFound, questionModelIdNotFound.getId(), "");
        assertFalse(isEdited);
    }





}