package com.utn.tesis.service;

import com.utn.tesis.exception.types.*;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.model.Votation;
import com.utn.tesis.repository.UserStoryRepository;
import com.utn.tesis.repository.VotationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStoryService {
    private UserStoryRepository userStoryRepository;
    private VotationRespository votationRespository;

    @Autowired
    public UserStoryService(UserStoryRepository userStoryepository, VotationRespository votationRespository) {
        this.userStoryRepository = userStoryepository;
        this.votationRespository = votationRespository;
    }

    public void addUserstoryToParty(Party parti, Integer id) {
        UserStory us = getUserStory(id);
        us.setParty(parti);
        userStoryRepository.save(us);
    }

    public List<UserStory> getUserStories() {
        return userStoryRepository.findAll();
    }


    public UserStory creatUs(UserStory userStory) {
        return userStoryRepository.save(userStory);
    }

    public void deleteUs(Integer idUS) {
        UserStory us = getUserStory(idUS);
        userStoryRepository.deleteById(us.getId());
    }

    public UserStory getUserStory(Integer usId){
        return userStoryRepository.findById(usId)
                .orElseThrow(()-> new UsNotFoundException());
    }

    public UserStory modifyUs(Integer idUs ,UserStory userStory) {
        UserStory oldUs = getUserStory(idUs);
        Party usParty = oldUs.getParty();

        if(idUs != userStory.getId())
            throw new UsDoNotMatchException();

        oldUs = userStory;
        oldUs.setParty(usParty);
        return userStoryRepository.save(oldUs);
    }

    public void addVotationIntoUserStory(Votation votation, Integer userStoryId){
        UserStory us = getUserStory(userStoryId);

        us.getVotationsList().add(votation);
        votation.setUserStory(us);
        votationRespository.save(votation);
        userStoryRepository.save(us);
    }

    public List<Votation> getUserStoryVotations(Integer userStoryId) {
        return this.votationRespository.findByUserStoryId(userStoryId);
    }
    public List<UserStory>getUserStoriesFromPartyId(String partyId){
        return  this.userStoryRepository.findByPartyId(partyId);
    }

    public UserStory addStoryPoint(Integer usID,Integer storyPoint) {
        UserStory us = userStoryRepository.findById(usID).orElseThrow(()-> new UserNotFoundException());
        us.setStoryPoints(storyPoint);
         return userStoryRepository.save(us);

    }
}
