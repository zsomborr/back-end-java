package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.AnswerModel;
import com.codecool.peermentoringbackend.model.ApiResponse;
import com.codecool.peermentoringbackend.model.PublicAnswerModel;
import com.codecool.peermentoringbackend.model.PublicUserModel;
import com.codecool.peermentoringbackend.repository.AnswerRepository;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    private AnswerRepository answerRepository;

    private QuestionRepository questionRepository;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    public List<PublicAnswerModel> getAllAnswersByQuestionId(Long questionId, String userNameFromToken) {
        UserEntity userEntity = userRepository.findDistinctByUsername(userNameFromToken);
        List<AnswerEntity> answerEntities = answerRepository.findAnswerEntitiesByQuestionIdOrderByVoteDesc(questionId);
        List<PublicAnswerModel> answers = new ArrayList<>();
        for (AnswerEntity answerEntity: answerEntities) {
            PublicAnswerModel publicAnswerModel = modelMapper.map(answerEntity, PublicAnswerModel.class);
            publicAnswerModel.setUsername(answerEntity.getUser().getUsername());
            publicAnswerModel.setUserId(answerEntity.getUser().getId());
            publicAnswerModel.setQuestionId(answerEntity.getQuestion().getId());
            publicAnswerModel.setQuestionTitle(answerEntity.getQuestion().getTitle());
            if(answerEntity.getVoters().contains(userEntity)){
                publicAnswerModel.setVoted(true);
            }
            if(answerEntity.getUser().getUsername().equals(userEntity.getUsername())) publicAnswerModel.setMyAnswer(true);
            answers.add(publicAnswerModel);
        }
        return answers
                .stream()
                .sorted(Comparator.comparing(PublicAnswerModel::isAccepted)
                        .reversed())
                .collect(Collectors.toList());
    }

    public boolean addNewAnswer(AnswerModel answerModel, String username) {

        try {
            AnswerEntity answerEntity = AnswerEntity.builder()
                    .content(answerModel.getContent())
                    .submissionTime(LocalDateTime.now())
                    .user(userRepository.findDistinctByUsername(username))
                    .question(questionRepository.findDistinctById(answerModel.getQuestionId()))
                    .vote((long) 0)
                    .build();

            answerRepository.save(answerEntity);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }


    public boolean editAnswer(AnswerModel answerModel, long answerId, String usernameFromToken) {
        try {
            UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
            UserEntity userWhoAnswered = userRepository.findUserEntityByAnswerId(answerId);

            if (userWhoAnswered.getId().equals(userEntity.getId()) && !answerModel.getContent().isEmpty()) {
                Optional<AnswerEntity> answerOptional = answerRepository.findById(answerId);
                if (answerOptional.isPresent()) {
                    AnswerEntity answerEntity = answerOptional.get();
                    answerEntity.setContent(answerModel.getContent());
                    answerRepository.save(answerEntity);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;

    }

    public void acceptAnswer(Long answerId) {
        Optional<AnswerEntity> answerEntityOptional = answerRepository.findById(answerId);
        if (answerEntityOptional.isPresent()) {
            AnswerEntity answerEntity = answerEntityOptional.get();
            answerEntity.setAccepted(!answerEntity.isAccepted());
            Optional<AnswerEntity> acceptedOptional = answerRepository.findAnswerEntityByAcceptedTrue();
            if (acceptedOptional.isPresent()) {
                AnswerEntity currentAcceptedAnswer = acceptedOptional.get();
                currentAcceptedAnswer.setAccepted(false);
                answerRepository.save(currentAcceptedAnswer);
            }
            answerRepository.save(answerEntity);
        } else {
            throw new NoSuchElementException("Answer with id not found: " + answerId);
        }
    }

    public boolean deleteAnswer(long answerId) {
        Optional<AnswerEntity> answerOptional = answerRepository.findById(answerId);
        if (answerOptional.isPresent()) {
            answerRepository.deleteById(answerId);
            return true;
        } else {
            return false;
        }
    }

    public ApiResponse vote(Long answerId, String usernameFromToken) {
        UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
        AnswerEntity answerEntity = answerRepository.findDistinctById(answerId);
        if(answerEntity.getUser().getId().equals(userEntity.getId())){
            return new ApiResponse(false, "user can't vote for their own answers!");
        }
        if(!answerEntity.getVoters().contains(userEntity)){
            answerEntity.addUser(userEntity);
            answerEntity.setVote(answerEntity.getVote() + 1);
            answerRepository.save(answerEntity);
            return new ApiResponse(true, "Successfully voted on answer: " + answerEntity.getId());
        } else{
            answerEntity.removeUser(userEntity);
            answerEntity.setVote(answerEntity.getVote() - 1);
            answerRepository.save(answerEntity);
            return new ApiResponse(true, "Removed vote from answer: " + answerEntity.getId());
        }
    }
}
