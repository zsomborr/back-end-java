package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.*;
import com.codecool.peermentoringbackend.model.*;
import com.codecool.peermentoringbackend.repository.*;
import com.codecool.peermentoringbackend.security.JwtTokenServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;

    private ProjectTagRepository projectTagRepository;

    TechnologyTagRepository technologyTagRepository;

    JwtTokenServices jwtTokenServices;

    QuestionRepository questionRepository;

    AnswerRepository answerRepository;

    DiscordRepository discordRepository;

    private ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ProjectTagRepository projectTagRepository, TechnologyTagRepository technologyTagRepository, JwtTokenServices jwtTokenServices, QuestionRepository questionRepository, AnswerRepository answerRepository, DiscordRepository discordRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.projectTagRepository = projectTagRepository;
        this.technologyTagRepository = technologyTagRepository;
        this.jwtTokenServices = jwtTokenServices;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.discordRepository = discordRepository;
        this.modelMapper = modelMapper;
    }

    public PublicUserModel getPublicUserDataByUserId(Long userId) {
        UserEntity userEntity = userRepository.findDistinctById(userId);

        if(userEntity == null) return null;
        DiscordEntity discordEntity = discordRepository.getByUserId(userId);
        Rank rank = getUserRank(userId);
        PublicUserModel publicUserModel =  modelMapper.map(userEntity, PublicUserModel.class);
        publicUserModel.setRank(rank);
        if(discordEntity != null){
            publicUserModel.setDiscordId(discordEntity.getDiscordId());
            publicUserModel.setDiscordUsername(discordEntity.getDiscordUsername());
            publicUserModel.setDiscriminator(discordEntity.getDiscriminator());
        }
        return publicUserModel;
    }

    public LoggedUserModel getLoggedInUserData(HttpServletRequest request) {
        String username = jwtTokenServices.getUsernameFromToken(request);
        UserEntity userEntity = userRepository.findDistinctByUsername(username);
        List<ProjectEntity> projectTags = projectTagRepository.findProjectEntitiesByUserEntities(userEntity);
        List<TechnologyEntity> technologyTags = technologyTagRepository.findTechnologyEntitiesByUserEntities(userEntity);
        DiscordEntity discordEntity = discordRepository.getByUserId(userEntity.getId());
        if(discordEntity== null){

            return LoggedUserModel.builder()
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .city(userEntity.getCity())
                    .country(userEntity.getCountry())
                    .module(userEntity.getModule())
                    .username(userEntity.getUsername())
                    .email(userEntity.getEmail())
                    .projectTags(projectTags)
                    .technologyTags(technologyTags)
                    .allProjectTags(projectTagRepository.findAll())
                    .allTechnologyTags(technologyTagRepository.findAll())
                    .build();
        } else {
            return LoggedUserModel.builder()
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .city(userEntity.getCity())
                    .country(userEntity.getCountry())
                    .module(userEntity.getModule())
                    .username(userEntity.getUsername())
                    .email(userEntity.getEmail())
                    .projectTags(projectTags)
                    .technologyTags(technologyTags)
                    .allProjectTags(projectTagRepository.findAll())
                    .allTechnologyTags(technologyTagRepository.findAll())
                    .discordId(discordEntity.getDiscordId())
                    .discordUsername(discordEntity.getDiscordUsername())
                    .discriminator(discordEntity.getDiscriminator())
                    .build();
        }

    }


    public void savePersonalData(PublicUserModel publicUserModel, String usernameFromToken){
        UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
        if(userEntity == null){
            throw new NoSuchElementException("User not found with username: " + usernameFromToken);
        }
        if (publicUserModel.getFirstName() != null && !publicUserModel.getFirstName().isEmpty()){
            userEntity.setFirstName(publicUserModel.getFirstName());
        }
        if (publicUserModel.getLastName() != null && !publicUserModel.getLastName().isEmpty()){
            userEntity.setLastName(publicUserModel.getLastName());
        }
        if (publicUserModel.getCountry() != null && !publicUserModel.getCountry().isEmpty()){
            userEntity.setCountry(publicUserModel.getCountry());
        }
        if (publicUserModel.getCity() != null && !publicUserModel.getCity().isEmpty()){
            userEntity.setCity(publicUserModel.getCity());
        }
        if (publicUserModel.getModule() != null){
            userEntity.setModule(publicUserModel.getModule());
        }
        userRepository.save(userEntity);
    }

    public UserDataQAndAModel getLoggedInUserPage(HttpServletRequest request) {
        String username = jwtTokenServices.getUsernameFromToken(request);
        UserEntity userEntity = userRepository.findDistinctByUsername(username);
        List<QuestionEntity> questionEntities = questionRepository.findQuestionEntitiesByUser(userEntity);
        List<PublicQuestionModel> userQuestions = new ArrayList<>();
        for (QuestionEntity questionEntity : questionEntities) {
            PublicQuestionModel publicQuestionModel = modelMapper.map(questionEntity, PublicQuestionModel.class);
            publicQuestionModel.setUsername(questionEntity.getUser().getUsername());
            publicQuestionModel.setUserId(questionEntity.getUser().getId());
            publicQuestionModel.setNumberOfAnswers(questionEntity.getAnswers().size());
            userQuestions.add(publicQuestionModel);
        }
        List<AnswerEntity> userAnswers = answerRepository.findAnswerEntitiesByUser(userEntity);

        for (AnswerEntity answer : userAnswers) {
            answer.setTransientData();
        }
        return UserDataQAndAModel.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .city(userEntity.getCity())
                .country(userEntity.getCountry())
                .module(userEntity.getModule())
                .username(userEntity.getUsername())
                .userQuestions(userQuestions)
                .userAnswers(userAnswers)
                .build();
    }

    public boolean saveDiscordData(DiscordModel discordModel, String usernameFromToken) {
        UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
        if(discordRepository.existsByDiscordId(discordModel.getId())){
            return false;
        } else {
            DiscordEntity discordEntity = DiscordEntity.builder()
                    .discordId(discordModel.getId())
                    .discordUsername(discordModel.getUsername())
                    .discriminator(discordModel.getDiscriminator())
                    .user(userEntity)
                    .build();
            discordRepository.save(discordEntity);
            return true;
        }

    }

    public Rank getUserRank(Long userId){
        Long score = userRepository.getUserScore(userId);
        Optional<Rank> rankOptional = Rank.getRankByScore(score);
        return rankOptional.orElse(Rank.NOVICE);
    }


}
