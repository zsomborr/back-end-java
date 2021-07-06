package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.Module_;
import com.codecool.peermentoringbackend.model.PublicUserModel;
import com.codecool.peermentoringbackend.repository.*;
import com.codecool.peermentoringbackend.security.JwtTokenServices;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private ProjectTagRepository projectTagRepository;

    @MockBean
    private TechnologyTagRepository technologyTagRepository;

    @MockBean
    private JwtTokenServices jwtTokenServices;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private AnswerRepository answerRepository;

    @MockBean
    private DiscordRepository discordRepository;

    @Autowired
    private MapperService mapperService;



    private UserEntity userEntity;

    private UserService userService;

    @BeforeAll
    public void init(){
        userService = new UserService(userRepository, projectTagRepository, technologyTagRepository, jwtTokenServices, questionRepository, answerRepository, discordRepository, mapperService);
        userEntity = UserEntity.builder().username("testuser").email("testuser@email.com").password("password").build();
        userEntity = userRepository.save(userEntity);
    }

    @Test
    void savePersonalData_setFirstName_userIsModified(){
        PublicUserModel publicUserModel = PublicUserModel.builder().firstName("firstName").build();
        userService.savePersonalData(publicUserModel, userEntity.getUsername());
        Optional<UserEntity> userOptional = userRepository.findById(userEntity.getId());
        UserEntity user = userOptional.orElseThrow(NoSuchElementException::new);
        assertEquals(publicUserModel.getFirstName(), user.getFirstName());
    }

    @Test
    void savePersonalData_setLastName_userIsModified(){
        PublicUserModel publicUserModel = PublicUserModel.builder().lastName("lastName").build();
        userService.savePersonalData(publicUserModel, userEntity.getUsername());
        Optional<UserEntity> userOptional = userRepository.findById(userEntity.getId());
        UserEntity user = userOptional.orElseThrow(NoSuchElementException::new);
        assertEquals(publicUserModel.getLastName(), user.getLastName());
    }

    @Test
    void savePersonalData_setAllFields_userIsModified(){
        PublicUserModel publicUserModel = PublicUserModel.builder().firstName("firstName").lastName("lastName").city("city").country("country").module(Module_.PROGBASICS).build();
        userService.savePersonalData(publicUserModel, userEntity.getUsername());
        Optional<UserEntity> userOptional = userRepository.findById(userEntity.getId());
        UserEntity user = userOptional.orElseThrow(NoSuchElementException::new);
        assertThat(publicUserModel.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(publicUserModel.getLastName()).isEqualTo(user.getLastName());
        assertThat(publicUserModel.getCity()).isEqualTo(user.getCity());
        assertThat(publicUserModel.getCountry()).isEqualTo(user.getCountry());
        assertThat(publicUserModel.getModule()).isEqualTo(user.getModule());
    }

    @Test
    void savePersonalData_userNotFound_throwsNoSuchElementException(){
        PublicUserModel publicUserModel = PublicUserModel.builder().firstName("firstName").lastName("lastName").city("city").country("country").module(Module_.PROGBASICS).build();
        assertThrows(NoSuchElementException.class, () -> userService.savePersonalData(publicUserModel, ""));
    }

}