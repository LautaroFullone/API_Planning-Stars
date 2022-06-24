package com.utn.tesis.service;

import com.utn.tesis.exception.types.*;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.repository.PartyRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class PartyService {

    private PartyRepostory partyRepostory;
    private UserStoryService  userStoryService;

    @Autowired
    public PartyService(PartyRepostory partyRepostory, UserStoryService userStoryService) {
        this.partyRepostory = partyRepostory;
        this.userStoryService = userStoryService;
    }

    public Party addParty(Party party) {
        party.setId(UUID.randomUUID().toString().toUpperCase().substring(0,6));
        party.setIsActive(true);
        party.setCreatedDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
        return partyRepostory.save(party);
    }

    public Party getPartyById(String idParty) {
        return this.partyRepostory.findById(idParty)
                .orElseThrow(()-> new PartyNotFoundException());
    }

    public List<Party> getParties() {
        List<Party> parties = partyRepostory.findAll();
        return  parties;
    }

    public void deleteParty(String idParty) {
        if(partyRepostory.existsById(idParty))
            partyRepostory.deleteById(idParty);
        else
            throw new PartyNotFoundException();
    }

    public void adduserStoryToParty(String idParty, Integer userStory) {
        Party parti=partyRepostory.findById(idParty).orElseThrow(()->new PartyNotFoundException());
        UserStory usCreated= userStoryService.getUserStory(userStory);
        boolean usInParty = this.existUsInParty(parti.getUserStories(),usCreated);

        if(usInParty)
            throw new UsAlreadyInThePartyException();

        parti.getUserStories().add(usCreated); //add to the party
        userStoryService.addUserstoryToParty(parti,userStory); //Save changes in Userstory
        partyRepostory.save(parti);
    }

    public boolean existUsInParty(List<UserStory> userStoryList,UserStory userStory){
        boolean rta=false;
        for (UserStory us:userStoryList) {
            if(us.getId() == userStory.getId())
                rta=true;
        }
        return  rta;
    }

    public List<UserStory> getPartyUsList(String idParty) {
        Party party = partyRepostory.findById(idParty).orElseThrow(()->new PartyNotFoundException());
        return party.getUserStories();
    }
}
