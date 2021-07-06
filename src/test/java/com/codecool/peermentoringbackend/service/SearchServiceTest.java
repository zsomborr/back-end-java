package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.PublicQuestionModel;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
class SearchServiceTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MapperService mapperService;

    private SearchService searchService;

    private UserEntity userEntity;

    private QuestionEntity questionOne;
    private QuestionEntity questionTwo;
    private QuestionEntity questionThree;
    private QuestionEntity questionFour;
    private QuestionEntity questionFive;

    private PublicQuestionModel questionModelOne;
    private PublicQuestionModel questionModelTwo;
    private PublicQuestionModel questionModelThree;
    private PublicQuestionModel questionModelFour;
    private PublicQuestionModel questionModelFive;

    @BeforeEach
    public void setup(){
        searchService = new SearchService(questionRepository, mapperService);

        userEntity = UserEntity.builder().username("testuser").email("testuser@email.com").password("password").build();

        questionOne = QuestionEntity.builder().user(userEntity).title("this test is working one four.").description("this test is working.").build();
        questionTwo = QuestionEntity.builder().user(userEntity).title("this test is working.").description("this is not working two.").build();
        questionThree = QuestionEntity.builder().user(userEntity).title("this is not working three.").description("this test is working three.").build();
        questionFour = QuestionEntity.builder().user(userEntity).title("this is not working now.").description("this is not working.").build();
        questionFive = QuestionEntity.builder().user(userEntity).title("this is not working.").description("this is not working now.").build();

        userRepository.save(userEntity);

        questionRepository.saveAll(Arrays.asList(questionOne, questionTwo, questionThree, questionFour, questionFive));
        questionModelOne = PublicQuestionModel.builder().id(questionOne.getId()).userId(userEntity.getId()).username(userEntity.getUsername()).title("this test is working one four.").description("this test is working.").build();
        questionModelTwo = PublicQuestionModel.builder().id(questionTwo.getId()).userId(userEntity.getId()).username(userEntity.getUsername()).title("this test is working.").description("this is not working two.").build();
        questionModelThree = PublicQuestionModel.builder().id(questionThree.getId()).userId(userEntity.getId()).username(userEntity.getUsername()).title("this is not working three.").description("this test is working three.").build();
        questionModelFour = PublicQuestionModel.builder().id(questionFour.getId()).userId(userEntity.getId()).username(userEntity.getUsername()).title("this is not working now.").description("this is not working.").build();
        questionModelFive = PublicQuestionModel.builder().id(questionFive.getId()).userId(userEntity.getId()).username(userEntity.getUsername()).title("this is not working.").description("this is not working now.").build();

    }

    @Test
    public void search_singleWordInMiddleOfTitle_returnsQuestionOne(){
        List<PublicQuestionModel> questionEntities = searchService.search(Collections.singletonList("one"));
        assertThat(questionEntities).containsExactlyInAnyOrder(questionModelOne);
    }

    @Test
    public void search_singleWordInMiddleOfDescription_returnsQuestionTwo(){
        List<PublicQuestionModel> questionEntities = searchService.search(Collections.singletonList("two"));
        assertThat(questionEntities).containsExactlyInAnyOrder(questionModelTwo);
    }

    @Test
    public void search_singleWordInMiddleOfTitleAndDescription_returnsQuestionThree(){
        List<PublicQuestionModel> questionEntities = searchService.search(Collections.singletonList("three"));
        assertThat(questionEntities).containsExactlyInAnyOrder(questionModelThree);
    }

    @Test
    public void search_singleWordInMiddleOfTitleAndDescriptionAndInBothSeparately_returnsQuestionOneTwoAndThree(){
        List<PublicQuestionModel> questionEntities = searchService.search(Collections.singletonList("test"));
        assertThat(questionEntities).containsExactlyInAnyOrder(questionModelOne, questionModelTwo, questionModelThree);
    }

    @Test
    public void search_twoWordsInMiddleOfTitleAndDescription_returnsQuestionOneFourAndFive(){
        List<PublicQuestionModel> questionEntities = searchService.search(Arrays.asList("four", "now"));
        assertThat(questionEntities).containsExactlyInAnyOrder(questionModelOne, questionModelFour, questionModelFive);
    }

    @Test
    public void search_singleWordInNoneOfTheQuestions_returnsEmptyList(){
        List<PublicQuestionModel> questionEntities = searchService.search(Collections.singletonList("fail"));
        assertThat(questionEntities).isEmpty();
    }
}