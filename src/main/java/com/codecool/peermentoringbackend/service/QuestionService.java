package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.*;
import com.codecool.peermentoringbackend.repository.AnswerRepository;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    private AnswerRepository answerRepository;

    private UserRepository userRepository;

    private TechnologyTagRepository technologyTagRepository;

    private TagService tagService;

    private MapperService mapperService;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository, UserRepository userRepository, TechnologyTagRepository technologyTagRepository, TagService tagService, MapperService mapperService){
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.technologyTagRepository = technologyTagRepository;
        this.tagService = tagService;
        this.mapperService = mapperService;
    }

    public List<PublicQuestionModel> getAll(String usernameFromToken) {
        UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
        List<QuestionEntity> questionEntities = questionRepository.findAllDesc();
        return mapperService.mapQuestionEntityCollection(questionEntities, userEntity);
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
                    questionRepository.save(questionEntity);
                }
            }
        } catch (NullPointerException e){
            return false;
        }
        return edited;
    }


    public ApiResponse vote(Long questionId, String usernameFromToken) {
        UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
        QuestionEntity questionEntity = questionRepository.findDistinctById(questionId);
        if(questionEntity.getUser().getId().equals(userEntity.getId())){
            return new ApiResponse(false, "user can't vote for their own questions!");
        }
        if(!questionEntity.getVoters().contains(userEntity)){
            questionEntity.addUser(userEntity);
            questionEntity.setVote(questionEntity.getVote() + 1);
            questionRepository.save(questionEntity);
            return new ApiResponse(true, "Successfully voted on question: " + questionEntity.getId());
        } else{
            questionEntity.removeUser(userEntity);
            questionEntity.setVote(questionEntity.getVote() - 1);
            questionRepository.save(questionEntity);
            return new ApiResponse(true, "Removed vote from question: " + questionEntity.getId());
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

    public PublicQuestionModel getQuestionById(Long questionId, String usernameFromToken) {
        UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
        Optional<QuestionEntity> questionEntityOptional = questionRepository.findById(questionId);
        if(questionEntityOptional.isPresent()){
            QuestionEntity questionEntity = questionEntityOptional.get();
            return mapperService.mapEntityToPublicQuestionModel(questionEntity, userEntity);
        } else {
            throw new NoSuchElementException("Question not found with Id " + questionId);
        }
    }

    public boolean deleteQuestionAndRelatedAnswers(long questionId) {
        Optional<QuestionEntity> questionOptional = questionRepository.findById(questionId);
        if (questionOptional.isPresent()) {
            QuestionEntity questionEntity = questionOptional.get();
            Long id = questionEntity.getId();
            Optional<List<AnswerEntity>> allByQuestionId = answerRepository.findAllByQuestionId(id);
            if (allByQuestionId.isPresent()){
                List<AnswerEntity> answerEntities = allByQuestionId.get();
                answerRepository.deleteAll(answerEntities);
            }
            questionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }

    }
}
