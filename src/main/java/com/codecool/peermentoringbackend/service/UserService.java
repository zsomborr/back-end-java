package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.*;
import com.codecool.peermentoringbackend.model.*;
import com.codecool.peermentoringbackend.repository.*;
import com.codecool.peermentoringbackend.security.JwtTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectTagRepository projectTagRepository;

    @Autowired
    TechnologyTagRepository technologyTagRepository;

    @Autowired
    JwtTokenServices jwtTokenServices;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    DiscordRepository discordRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public PublicUserModel getPublicUserDataByUserId(Long userId) {
        UserEntity userEntity = userRepository.findDistinctById(userId);

        if(userEntity == null) return null;

        List<ProjectEntity> projectTags = projectTagRepository.findProjectEntitiesByUserEntities(userEntity);
        List<TechnologyEntity> technologyTags = technologyTagRepository.findTechnologyEntitiesByUserEntities(userEntity);
        DiscordEntity discordEntity = discordRepository.getByUserId(userId);
        if(discordEntity == null){
            return PublicUserModel.builder()
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .city(userEntity.getCity())
                    .country(userEntity.getCountry())
                    .module(userEntity.getModule())
                    .username(userEntity.getUsername())
                    .email(userEntity.getEmail())
                    .projectTags(projectTags)
                    .technologyTags(technologyTags)
                    .build();

        } else{
            return PublicUserModel.builder()
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .city(userEntity.getCity())
                    .country(userEntity.getCountry())
                    .module(userEntity.getModule())
                    .username(userEntity.getUsername())
                    .email(userEntity.getEmail())
                    .projectTags(projectTags)
                    .technologyTags(technologyTags)
                    .discordId(discordEntity.getDiscordId())
                    .discordUsername(discordEntity.getDiscordUsername())
                    .discriminator(discordEntity.getDiscriminator())
                    .build();
        }


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



    @Transactional
    public void savePersonalData(PublicUserModel publicUserModel, String usernameFromToken){
        UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
        String firstName = publicUserModel.getFirstName();
        String lastName = publicUserModel.getLastName();
        String country = publicUserModel.getCountry();
        String city = publicUserModel.getCity();
        Module_ module = publicUserModel.getModule();
        Long userId = userEntity.getId();

        Map<String, Object> parameterMap = new HashMap<>();
        List<String> setClause = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE UserEntity u SET  ");

        if (!firstName.isEmpty()){
            setClause.add(" u.firstName =:firstName,");
            parameterMap.put("firstName", firstName);
        }
        if (!lastName.isEmpty()){
            setClause.add(" u.lastName =:lastName,");
            parameterMap.put("lastName", lastName);
        }
        if (!country.isEmpty()){
            setClause.add(" u.country =:country,");
            parameterMap.put("country", country);
        }

        if (!city.isEmpty()){
            setClause.add(" u.city =:city,");
            parameterMap.put("city", city);
        }
        if (module!=null){
            setClause.add(" u.module =:module");
            parameterMap.put("module", module);
        }

        queryBuilder.append(String.join("", setClause));
        queryBuilder.append(" WHERE u.id = :userId");
        Query jpaQuery = entityManager.createQuery(queryBuilder.toString());
        jpaQuery.setParameter("userId", userId);

        for(String key :parameterMap.keySet()) {
            jpaQuery.setParameter(key, parameterMap.get(key));
        }

        jpaQuery.executeUpdate();

    }

    public UserDataQAndAModel getLoggedInUserPage(HttpServletRequest request) {
        String username = jwtTokenServices.getUsernameFromToken(request);
        UserEntity userEntity = userRepository.findDistinctByUsername(username);
        List<QuestionEntity> userQuestions = questionRepository.findQuestionEntitiesByUser(userEntity);

        for (QuestionEntity question : userQuestions) {
            question.setUserData();
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


}
