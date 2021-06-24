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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;


import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    private AnswerService answerService;

    private UserEntity userEntity;

    private AnswerEntity answerEntity;

    private AnswerModel answerModel;

    private QuestionEntity questionEntity;

    @BeforeEach
    public void setup(){
        userEntity = UserEntity.builder().username("testuser").id(1L).email("testuser@email.com").password("password").build();
        questionEntity = QuestionEntity.builder().user(userEntity).build();
        answerEntity = AnswerEntity.builder().content("sample").user(userEntity).question(questionEntity).build();
        answerEntity = answerRepository.save(this.answerEntity);

        answerModel = AnswerModel.builder().content("modified").build();
    }

    @Test
    public void editAnswer_noSuchAnswer_returnFalse(){
        Mockito.when(userRepository.findDistinctByUsername(Mockito.anyString())).thenReturn(userEntity);
        Mockito.when(userRepository.findUserEntityByAnswerId(Mockito.anyLong())).thenReturn(userEntity);
        boolean isEdited = answerService.editAnswer(answerModel, answerEntity.getId()+1, "");
        assertFalse(isEdited);
    }

    @Test
    public void editAnswer_noSuchUser_returnFalse(){
        Mockito.when(userRepository.findDistinctByUsername(Mockito.anyString())).thenReturn(null);
        Mockito.when(userRepository.findUserEntityByAnswerId(Mockito.anyLong())).thenReturn(userEntity);
        boolean isEdited = answerService.editAnswer(answerModel, answerEntity.getId(), "");
        assertFalse(isEdited);
    }

    @Test
    void editAnswer_successfulEdit_returnTrue(){
        Mockito.when(userRepository.findDistinctByUsername(Mockito.anyString())).thenReturn(userEntity);
        Mockito.when(userRepository.findUserEntityByAnswerId(Mockito.anyLong())).thenReturn(userEntity);
        boolean isEdited = answerService.editAnswer(answerModel, answerEntity.getId(), "");
        assertTrue(isEdited);
        Optional<AnswerEntity> answerOptional = answerRepository.findById(answerEntity.getId());
        AnswerEntity answerEntity = answerOptional.orElseThrow(NoSuchElementException::new);
        assertEquals(answerEntity.getContent(), answerModel.getContent());
    }
}