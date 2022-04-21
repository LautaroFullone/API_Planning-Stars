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
    private UserStoryepository userStoryepository;

    @Autowired
    public UserStoryService(UserStoryepository userStoryepository) {
        this.userStoryepository = userStoryepository;
    }

    public void addUserstoryToParty(Party parti, Integer id) {
        UserStory us=this.existUSByID(id);
        us.setParty(parti);
        userStoryepository.save(us);
    }
    public UserStory existUSByID(Integer idUS){
        UserStory us;
        if(userStoryepository.existsById(idUS)){
            us=userStoryepository.getById(idUS);
        }else {
            throw new UsNotFoundException();
        }
        return us;
    }

    public List<UserStory> getUs() {
        return userStoryepository.findAll();
    }

    public void creatUs(UserStory userStory) {
        userStoryepository.save(userStory);
    }
    public void deleteUs(Integer idUS) {
        UserStory us= this.existUSByID(idUS);
        userStoryepository.deleteById(us.getId());
    }

    public void modifyUs(Integer idUs ,UserStory userStory ,Party parti) {
        UserStory usModify ;
        usModify=this.existUSByID(idUs);
        System.out.println(usModify.getId());
        System.out.println(usModify.getParty().getId());
        if(usModify.getId() != userStory.getId() ){
            throw new UsDoNotMatchException();
        }else {
            usModify = userStory;
            usModify.setParty(parti);
        }
        userStoryepository.save(usModify);


    }
}
