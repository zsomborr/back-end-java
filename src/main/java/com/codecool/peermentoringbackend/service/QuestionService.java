package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.*;
import com.codecool.peermentoringbackend.repository.AnswerRepository;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TechnologyTagRepository technologyTagRepository;

    @Autowired TagService tagService;

    @PersistenceContext
    private EntityManager entityManager;

    public List<QuestionEntity> getAll(UserEntity userEntity) {
        List<QuestionEntity> questionEntities = questionRepository.findAll();
        for (QuestionEntity question : questionEntities) {
            question.setUserData();
            if(question.getVoters().contains(userEntity)){
                question.setVoted(true);
            }
        }
        return questionEntities;
    }

    public boolean addNewQuestion(QuestionModel questionModel, String username) {

        try {
            QuestionEntity question = QuestionEntity.builder()
                    .title(questionModel.getTitle())
                    .description(questionModel.getDescription())
                    .submissionTime(LocalDateTime.now())
                    .user(userRepository.findDistinctByUsername(username))
                    .technologyTags(new HashSet<>())
                    .vote((long) 0)
                    .anonym(questionModel.isAnonym())
                    .build();
            questionRepository.save(question);

            List<String> technologyTags = questionModel.getTechnologyTags();

            if (technologyTags != null) {
                for (String tag : technologyTags ) {
                    boolean b = tagService.addNewTechnologyTagToQuestion(tag, question);
                    if (!b) return false;
                    questionRepository.save(question);
                }
            }
        }
        catch (NullPointerException e) {
            return false;
        }
return true;
    }


    public QAndAsModel getQuestionByIdAndAnswers(Long questionId, UserEntity userEntity) {
        List<AnswerEntity> answerEntities = answerRepository.findAnswerEntitiesByQuestionId(questionId);
        QuestionEntity questionEntityById = questionRepository.findQuestionEntityById(questionId);
        if(questionEntityById.getVoters().contains(userEntity)){
            questionEntityById.setVoted(true);
        }
        questionEntityById.setUserData();

        for (AnswerEntity answer: answerEntities) {
            answer.setTransientData();
        }

        return new QAndAsModel(questionEntityById, answerEntities);
    }


    @Transactional
    public boolean editQuestion(QModelWithId questionModel, UserEntity userEntity, Long questionId) {
        try {
            UserEntity userWhoAskedQ = userRepository.findUserEntityByQuestionId(questionId);

            if(userWhoAskedQ.getId().equals(userEntity.getId())){
                Map<String, Object> parameterMap = new HashMap<>();
                List<String> setClause = new ArrayList<>();

                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("UPDATE QuestionEntity q SET ");

                if (!questionModel.getTitle().isEmpty()){
                    setClause.add(" q.title =:title");
                    parameterMap.put("title", questionModel.getTitle());
                }
                if (!questionModel.getDescription().isEmpty()){
                    setClause.add(" q.description =:description");
                    parameterMap.put("description", questionModel.getDescription());
                }

                queryBuilder.append(String.join(",", setClause));
                queryBuilder.append(" WHERE q.id = :questionId");
                Query jpaQuery = entityManager.createQuery(queryBuilder.toString());
                jpaQuery.setParameter("questionId", questionId);
                for(String key :parameterMap.keySet()) {
                    jpaQuery.setParameter(key, parameterMap.get(key));
                }

                jpaQuery.executeUpdate();
            } else {
                return false;
            }


        } catch (NullPointerException e){
            return false;
        }
        return true;
    }

    @Transactional
    public RegResponse vote(Vote vote, Long questionId, UserEntity userEntity) {
        QuestionEntity questionEntity = questionRepository.findDistinctById(questionId);
        if(questionEntity.getUser().getId() == userEntity.getId()){
            return new RegResponse(false, "user can't vote for their own questions!");
        }
        if(!questionEntity.getVoters().contains(userEntity)){
            questionEntity.addUser(userEntity);
            Query jpaQuery = entityManager.createQuery("UPDATE QuestionEntity q SET q.vote = q.vote + :vote where q.id = :questionId");
            jpaQuery.setParameter("questionId", questionId);
            jpaQuery.setParameter("vote", vote.getVote());
            jpaQuery.executeUpdate();

            return new RegResponse(true, "success");
        } else{
            return new RegResponse(false, "user already voted for this question");
        }

    }

    public boolean addNewTag(QuestionTagModel tagModel) {

        QuestionEntity question= questionRepository.findQuestionEntityById(tagModel.getQuestionId());

        boolean b = tagService.addNewTechnologyTagToQuestion(tagModel.getTechnologyTag(), question);
        if(b) questionRepository.save(question);

        return b;
    }

    public boolean removeTag(QuestionTagModel tagModel) {

        return tagService.removeTechnologyTagFromQuestion(tagModel);
    }
}
