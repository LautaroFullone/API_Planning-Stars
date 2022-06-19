package com.utn.tesis.service;

import com.utn.tesis.exception.types.UsDoNotMatchException;
import com.utn.tesis.exception.types.UsNotFoundException;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.repository.UserStoryepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStoryService {
    private UserStoryepository userStoryRepository;

    @Autowired
    public UserStoryService(UserStoryepository userStoryepository) {
        this.userStoryRepository = userStoryepository;
    }

    public void addUserstoryToParty(Party parti, Integer id) {
        UserStory us=userStoryRepository.findById(id).orElseThrow(()-> new UsNotFoundException());
        us.setParty(parti);
        userStoryRepository.save(us);
    }

    public List<UserStory> getUs() {
        return userStoryRepository.findAll();
    }

    public UserStory creatUs(UserStory userStory) {
       return userStoryRepository.save(userStory);
    }

    public void deleteUs(Integer idUS) {
        UserStory us= userStoryRepository.findById(idUS).orElseThrow(()-> new UsNotFoundException());
        userStoryRepository.deleteById(us.getId());
    }

    public UserStory getUserStory(Integer usId){
        return userStoryRepository.findById(usId).orElseThrow(()-> new UsNotFoundException());
    }

    public UserStory modifyUs(Integer idUs ,UserStory userStory) {
        UserStory oldUs = userStoryRepository.findById(idUs).orElseThrow(()->new UsNotFoundException());
        Party usParty = oldUs.getParty();

        if(idUs != userStory.getId())
            throw new UsDoNotMatchException();

        oldUs = userStory;
        oldUs.setParty(usParty);
        return userStoryRepository.save(oldUs);
    }
}
