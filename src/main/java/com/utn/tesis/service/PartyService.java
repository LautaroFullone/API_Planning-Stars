package com.utn.tesis.service;

import com.utn.tesis.exception.types.*;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.model.Votation;
import com.utn.tesis.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class PartyService {

    private PartyRepository partyRepostory;
    private UserStoryService  userStoryService;
    private VotationService votationService;

    @Autowired
    public PartyService(PartyRepository partyRepostory, UserStoryService userStoryService, VotationService votationService) {
        this.partyRepostory = partyRepostory;
        this.userStoryService = userStoryService;
        this.votationService = votationService;
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
        return this.userStoryService.getUserStoriesFromPartyId(idParty);
    }

    public List<Votation> getPartyVotation(String partyId) {
        List<UserStory> userStoryList =  this.userStoryService.getUserStoriesFromPartyId(partyId);
        List<Integer> idUs =new ArrayList<Integer>();
        for (UserStory us:userStoryList) {
            idUs.add(us.getId());
        }
         List<Votation> votationList = this.votationService.getVotationFromUserstories(idUs);
        return  votationList;
    }
}
