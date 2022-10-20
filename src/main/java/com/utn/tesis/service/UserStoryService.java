package com.utn.tesis.service;

import com.utn.tesis.exception.types.*;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.model.Votation;
import com.utn.tesis.model.dto.PlanningDetailDTO;
import com.utn.tesis.model.dto.UserVoteDTO;
import com.utn.tesis.repository.UserStoryRepository;
import com.utn.tesis.repository.VotationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserStoryService {
    private UserStoryRepository userStoryRepository;
    private UserService userService;
    private VotationRespository votationRespository;

    @Autowired
    public UserStoryService(UserStoryRepository userStoryepository, VotationRespository votationRespository,UserService userService) {
        this.userStoryRepository = userStoryepository;
        this.votationRespository = votationRespository;
        this.userService= userService;
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

    public Votation getMinValue(List<Votation> votationList){

        Votation rta= new Votation();
        rta= votationList.get(0);
        for (Votation v : votationList) {
            if(rta.getValue() > v.getValue()){
                rta =v;
            }

        }
        return  rta;

    }
    public Votation getMaxValue(List<Votation> votationList){

        Votation rta= new Votation();
        rta= votationList.get(0);
        for (Votation v : votationList) {
            if(rta.getValue() < v.getValue()){
                rta =v;
            }

        }
        return  rta;

    }
    public List<UserVoteDTO> getUserVoteDTO(List<Votation> votationList){
        List<UserVoteDTO> userVoteDTOS = new ArrayList<UserVoteDTO>();
        UserVoteDTO userDTO ;

            for (Votation u: votationList) {
                userDTO =new UserVoteDTO();
                userDTO.setVote(u.getValue());
                userDTO.setName(userService.getUserById(Integer.valueOf(u.getUserID())).getName());
                userDTO.setIdUser(Integer.valueOf( u.getUserID()));
                userVoteDTOS.add(userDTO);
            }

        return userVoteDTOS;
    }
    public  int aproxNumber(List<Integer> numeros, int num) {
        int cercano = 0;
        int diferencia = Integer.MAX_VALUE;
        for (int i = 0; i < numeros.size(); i++) {
            if (numeros.get(i) == num) {
                return numeros.get(i);
            } else {
                if(Math.abs(numeros.get(i)-num)<diferencia){
                    cercano=numeros.get(i);
                    diferencia = Math.abs(numeros.get(i)-num);
                }
            }
        }
        return cercano;
    }
    public int getAverageValue(List<Votation> votationList){

        int rta = 0;
        int promedio = 0;
        int contador = 0;
        List<Integer> integerList= new ArrayList<Integer>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(5);
        integerList.add(8);
        integerList.add(13);
        integerList.add(21);
        integerList.add(34);
        integerList.add(89);

        float division = 0;
        for(Votation u: votationList) {

            contador += u.getValue();
        }
        division = contador / votationList.size();

        promedio = this.aproxNumber(integerList,(int)division);
        rta=promedio;
        return  rta;
    }
    public PlanningDetailDTO getPlanningDetail(Integer userStoryId,Integer connectedUsers,boolean saveStoryPoints) {

        List<Votation> votationList = this.getUserStoryVotations(userStoryId);
        PlanningDetailDTO planningDetailDTO = new PlanningDetailDTO();
        if(votationList.size() != 0) {

            Votation minVote = this.getMinValue(votationList);
            Votation maxVote = this.getMaxValue(votationList);

            UserVoteDTO min = new UserVoteDTO(minVote.getValue(), userService.getUserById(Integer.valueOf(minVote.getUserID())).getName(), Integer.valueOf(minVote.getUserID()));
            UserVoteDTO max = new UserVoteDTO(maxVote.getValue(), userService.getUserById(Integer.valueOf(maxVote.getUserID())).getName(), Integer.valueOf(maxVote.getUserID()));



            planningDetailDTO.setMinVote(min);
            planningDetailDTO.setMaxVote(max);
            planningDetailDTO.setUserVotes(this.getUserVoteDTO(votationList));

            planningDetailDTO.setUsersNotVote(connectedUsers - planningDetailDTO.getUserVotes().size());
            planningDetailDTO.setAverageVote(this.getAverageValue(votationList));

            if (saveStoryPoints == true) {
                UserStory us = getUserStory(userStoryId);
                us.setStoryPoints(planningDetailDTO.getAverageVote());
                userStoryRepository.save(us);
            }
        }else {
            throw new NoVotationContentException();
        }


    return  planningDetailDTO;
    }
    public void restartVotation(Integer idUs){
        List<Votation> votationList = new ArrayList<Votation>();
        votationList= votationRespository.findByUserStoryId(idUs);
        for (Votation vot:votationList) {
            votationRespository.deleteById(vot.getId());
        }
    }
}