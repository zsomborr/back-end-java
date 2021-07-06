package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.AnswerModel;
import com.codecool.peermentoringbackend.repository.AnswerRepository;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;


import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
class AnswerServiceTest {

    @Autowired
    private AnswerRepository answerRepository;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private MapperService mapperService;

    private AnswerService answerService;

    private UserEntity userEntity;

    private AnswerEntity answerEntityOne;
    private AnswerEntity answerEntityTwo;

    private AnswerModel answerModel;

    private QuestionEntity questionEntity;

    @BeforeEach
    public void setup(){
        mapperService = new MapperService(modelMapper);
        answerService = new AnswerService(answerRepository,questionRepository, userRepository, mapperService);
        userEntity = UserEntity.builder().username("testuser").id(1L).email("testuser@email.com").password("password").build();
        questionEntity = QuestionEntity.builder().user(userEntity).build();
        answerEntityOne = AnswerEntity.builder().content("sample").user(userEntity).question(questionEntity).build();
        answerEntityTwo = AnswerEntity.builder().content("sample").user(userEntity).question(questionEntity).accepted(true).build();
        answerEntityOne = answerRepository.save(this.answerEntityOne);
        answerEntityTwo = answerRepository.save(this.answerEntityTwo);

        answerModel = AnswerModel.builder().content("modified").build();
    }

    @Test
    public void editAnswer_noSuchAnswer_returnFalse(){
        Mockito.when(userRepository.findDistinctByUsername(Mockito.anyString())).thenReturn(userEntity);
        Mockito.when(userRepository.findUserEntityByAnswerId(Mockito.anyLong())).thenReturn(userEntity);
        boolean isEdited = answerService.editAnswer(answerModel, answerEntityOne.getId()+14234234, "");
        assertFalse(isEdited);
    }

    @Test
    public void editAnswer_noSuchUser_returnFalse(){
        Mockito.when(userRepository.findDistinctByUsername(Mockito.anyString())).thenReturn(null);
        Mockito.when(userRepository.findUserEntityByAnswerId(Mockito.anyLong())).thenReturn(userEntity);
        boolean isEdited = answerService.editAnswer(answerModel, answerEntityOne.getId(), "");
        assertFalse(isEdited);
    }

    @Test
    public void editAnswer_successfulEdit_returnTrue(){
        Mockito.when(userRepository.findDistinctByUsername(Mockito.anyString())).thenReturn(userEntity);
        Mockito.when(userRepository.findUserEntityByAnswerId(Mockito.anyLong())).thenReturn(userEntity);
        boolean isEdited = answerService.editAnswer(answerModel, answerEntityOne.getId(), "");
        assertTrue(isEdited);
        Optional<AnswerEntity> answerOptional = answerRepository.findById(answerEntityOne.getId());
        AnswerEntity answerEntity = answerOptional.orElseThrow(NoSuchElementException::new);
        assertEquals(answerEntity.getContent(), answerModel.getContent());
    }

    @Test
    public void deleteAnswer_successfulDeletion_returnsTrue(){
        boolean isDeleted = answerService.deleteAnswer(answerEntityOne.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    public void deleteAnswer_answerIsNotFound_returnsFalse(){
        boolean isDeleted = answerService.deleteAnswer(answerEntityTwo.getId() + 11212123);
        assertThat(isDeleted).isFalse(); }
}