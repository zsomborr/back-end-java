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
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    private AnswerRepository answerRepository;

    private UserRepository userRepository;

    private TechnologyTagRepository technologyTagRepository;

    private TagService tagService;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository, UserRepository userRepository, TechnologyTagRepository technologyTagRepository, TagService tagService){
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.technologyTagRepository = technologyTagRepository;
        this.tagService = tagService;
    }

    public List<QuestionEntity> getAll(String usernameFromToken) {
        UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
        List<QuestionEntity> questionEntities = questionRepository.findAllDesc();
        for (QuestionEntity question : questionEntities) {
            question.setUserData();
            if(question.getVoters().contains(userEntity)){
                question.setVoted(true);
            }
            if(question.getUsername().equals(userEntity.getUsername())) question.setMyQuestion(true);
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


    public QAndAsModel getQuestionByIdAndAnswers(Long questionId, String usernameFromToken) {
        UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
        List<AnswerEntity> answerEntities = answerRepository.findAnswerEntitiesByQuestionId(questionId);
        QuestionEntity questionEntityById = questionRepository.findQuestionEntityById(questionId);
        if(questionEntityById.getVoters().contains(userEntity)){
            questionEntityById.setVoted(true);
        }
        questionEntityById.setUserData();
        if(questionEntityById.getUsername().equals(userEntity.getUsername())) questionEntityById.setMyQuestion(true);


        for (AnswerEntity answer: answerEntities) {
            answer.setTransientData();
            if(answer.getUsername().equals(userEntity.getUsername())) answer.setMyAnswer(true);

        }

        return new QAndAsModel(questionEntityById, answerEntities);
    }


    public boolean editQuestion(QModelWithId questionModel, Long questionId, String usernameFromToken) {
        boolean edited = false;
        try {
            UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
            UserEntity userWhoAskedQ = userRepository.findUserEntityByQuestionId(questionId);

            if(userWhoAskedQ.getId().equals(userEntity.getId())){
                Optional<QuestionEntity> questionEntityOptional = questionRepository.findById(questionId);
                if(questionEntityOptional.isPresent()){
                    QuestionEntity questionEntity = questionEntityOptional.get();
                    if(!questionModel.getTitle().isEmpty()){
                        questionEntity.setTitle(questionModel.getTitle());
                        edited = true;
                    }
                    if(!questionModel.getDescription().isEmpty()){
                        questionEntity.setDescription(questionModel.getDescription());
                        edited = true;
                    }
                }
            }
        } catch (NullPointerException e){
            return false;
        }
        return edited;
    }

//    @Transactional
    public RegResponse vote(Vote vote, Long questionId, String usernameFromToken) {
        UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
        QuestionEntity questionEntity = questionRepository.findDistinctById(questionId);
        if(questionEntity.getUser().getId().equals(userEntity.getId())){
            return new RegResponse(false, "user can't vote for their own questions!");
        }
        if(!questionEntity.getVoters().contains(userEntity)){
            questionEntity.addUser(userEntity);
//            Query jpaQuery = entityManager.createQuery("UPDATE QuestionEntity q SET q.vote = q.vote + :vote where q.id = :questionId");
//            jpaQuery.setParameter("questionId", questionId);
//            jpaQuery.setParameter("vote", vote.getVote());
//            jpaQuery.executeUpdate();

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
