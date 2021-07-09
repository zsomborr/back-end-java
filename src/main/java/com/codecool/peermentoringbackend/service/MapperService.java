package com.codecool.peermentoringbackend.service;

import com.codecool.peermentoringbackend.entity.AnswerEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.PublicAnswerModel;
import com.codecool.peermentoringbackend.model.PublicQuestionModel;
import com.codecool.peermentoringbackend.model.PublicUserModel;
import com.codecool.peermentoringbackend.model.Rank;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapperService {

    private ModelMapper modelMapper;

    @Autowired
    public MapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<PublicAnswerModel> mapAnswerEntityCollection(List<AnswerEntity> answerEntities, UserEntity userEntity){
        List<PublicAnswerModel> answerModels = new ArrayList<>();
        for (AnswerEntity answerEntity: answerEntities) {
            PublicAnswerModel publicAnswerModel = mapEntityToPublicAnswerModel(userEntity, answerEntity);
            answerModels.add(publicAnswerModel);
        }
        return answerModels;
    }

    private PublicAnswerModel mapEntityToPublicAnswerModel(UserEntity userEntity, AnswerEntity answerEntity) {
        PublicAnswerModel publicAnswerModel = modelMapper.map(answerEntity, PublicAnswerModel.class);
        publicAnswerModel.setUsername(answerEntity.getUser().getUsername());
        publicAnswerModel.setUserId(answerEntity.getUser().getId());
        publicAnswerModel.setQuestionId(answerEntity.getQuestion().getId());
        publicAnswerModel.setQuestionTitle(answerEntity.getQuestion().getTitle());
        if(answerEntity.getVoters().contains(userEntity)){
            publicAnswerModel.setVoted(true);
        }
        if(answerEntity.getUser().getUsername().equals(userEntity.getUsername())) publicAnswerModel.setMyAnswer(true);
        return publicAnswerModel;
    }

    public List<PublicQuestionModel> mapQuestionEntityCollection(List<QuestionEntity> questionEntities, UserEntity userEntity){
        List<PublicQuestionModel> publicQuestionModels = new ArrayList<>();
        for (QuestionEntity questionEntity : questionEntities) {
            PublicQuestionModel publicQuestionModel = mapEntityToPublicQuestionModel(questionEntity, userEntity);
            publicQuestionModels.add(publicQuestionModel);
        }
        return publicQuestionModels;
    }

    public List<PublicQuestionModel> mapQuestionEntityCollection(List<QuestionEntity> questionEntities){
        List<PublicQuestionModel> publicQuestionModels = new ArrayList<>();
        for (QuestionEntity questionEntity : questionEntities) {
            PublicQuestionModel publicQuestionModel = modelMapper.map(questionEntity, PublicQuestionModel.class);
            publicQuestionModel.setUsername(questionEntity.getUser().getUsername());
            publicQuestionModel.setUserId(questionEntity.getUser().getId());
            publicQuestionModel.setNumberOfAnswers(questionEntity.getAnswers().size());
            publicQuestionModels.add(publicQuestionModel);
        }
        return publicQuestionModels;
    }

    public PublicQuestionModel mapEntityToPublicQuestionModel(QuestionEntity questionEntity, UserEntity userEntity){
        PublicQuestionModel publicQuestionModel = modelMapper.map(questionEntity, PublicQuestionModel.class);
        publicQuestionModel.setUsername(questionEntity.getUser().getUsername());
        publicQuestionModel.setUserId(questionEntity.getUser().getId());
        publicQuestionModel.setNumberOfAnswers(questionEntity.getAnswers().size());
        if(questionEntity.getVoters().contains(userEntity)){
            publicQuestionModel.setVoted(true);
        }
        if(questionEntity.getUser().getUsername().equals(userEntity.getUsername())) publicQuestionModel.setMyQuestion(true);
        return publicQuestionModel;
    }

    public PublicQuestionModel mapEntityToPublicQuestionModel(QuestionEntity questionEntity){
        PublicQuestionModel publicQuestionModel = modelMapper.map(questionEntity, PublicQuestionModel.class);
        publicQuestionModel.setUsername(questionEntity.getUser().getUsername());
        publicQuestionModel.setUserId(questionEntity.getUser().getId());
        publicQuestionModel.setNumberOfAnswers(questionEntity.getAnswers().size());
        return publicQuestionModel;
    }

    public PublicUserModel mapEntityToPublicUserModel(UserEntity userEntity, Rank rank){
        PublicUserModel publicUserModel = modelMapper.map(userEntity, PublicUserModel.class);
        publicUserModel.setRank(rank);
        return publicUserModel;
    }

}
