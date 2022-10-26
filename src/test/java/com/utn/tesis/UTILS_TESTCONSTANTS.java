package com.utn.tesis;

import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.model.Votation;
import com.utn.tesis.model.dto.LoginResponseDto;

import java.util.ArrayList;
import java.util.List;

public class UTILS_TESTCONSTANTS {
    public static List<User> getUserList(){
        List<User> users =new ArrayList<User>();
        User user =new User(1,"User 1","user@gmail.com","password");
        User user2 =new User(2,"User 2","user@gmail.com","password");
        User user3=new User(3,"User 3","user@gmail.com","password");
        users.add(user);
        users.add(user2);
        users.add(user3);

        return  users;
    }
    public  static User getUser(){
        User user =new User(1,"User 1","user@gmail.com","password");
        return user;
    }
    public static LoginResponseDto getloginDTO(){
        return  LoginResponseDto.builder().userDetails(getUser()).token("code").build();
    }

    public static Party getParty(){
        Party party = new Party();
        party.setId("AABB11");
        party.setName("Party-Test");
        party.setMaxPlayer(5);
        party.setIsActive(true);
        party.setCreatedBy("User-Test");
        party.setCreatedDate("12-12-2022");
        party.setPartyOwnerId("1");
        return  party;
    }
    public static List<Party> getPartyList(){
        List<Party> partyList=new ArrayList<Party>();

        Party party = new Party();
        party.setId("AABB11");
        party.setName("Party-Test");
        party.setMaxPlayer(5);
        party.setIsActive(true);
        party.setCreatedBy("User-Test");
        party.setCreatedDate("12-12-2022");
        party.setPartyOwnerId("1");

        Party party2 = new Party();
        party2.setId("AABB22");
        party2.setName("Party-Test-2");
        party2.setMaxPlayer(5);
        party2.setIsActive(true);
        party2.setCreatedBy("User-Test");
        party2.setCreatedDate("12-12-2022");
        party2.setPartyOwnerId("2");

        Party party3 = new Party();
        party3.setId("AABB33");
        party3.setName("Party-Test-3");
        party3.setMaxPlayer(5);
        party3.setIsActive(true);
        party3.setCreatedBy("User-Test");
        party3.setCreatedDate("12-12-2022");
        party3.setPartyOwnerId("3");

        partyList.add(party);
        partyList.add(party2);
        partyList.add(party3);

        return  partyList;
    }

    public static List<UserStory> getUsList(){
        List<UserStory> userStoryList =new ArrayList<UserStory>();

        UserStory us = new UserStory();
        us.setId(1);
        us.setTag("Tag-Test");
        us.setName("Us-Test-1");
        us.setDescription("TestDescription");
        us.setWorkArea("TestWork");
        us.setStoryWritter("Shubam");
        us.setFileLink("FileTest");
        us.setStoryPoints(1);
        userStoryList.add(us);

        UserStory us2 = new UserStory();
        us2.setId(2);
        us2.setTag("Tag-Test");
        us2.setName("Us-Test-2");
        us2.setDescription("TestDescription");
        us2.setWorkArea("TestWork");
        us2.setStoryWritter("Shubam");
        us2.setFileLink("FileTest");
        us2.setStoryPoints(2);
        userStoryList.add(us2);

        UserStory us3 = new UserStory();
        us3.setId(3);
        us3.setTag("Tag-Test");
        us3.setName("Us-Test-3");
        us3.setDescription("TestDescription");
        us3.setWorkArea("TestWork");
        us3.setStoryWritter("Shubam");
        us3.setFileLink("FileTest");
        us3.setStoryPoints(3);
        userStoryList.add(us3);

        return userStoryList;
    }
    public static List<Votation> getVotationList(){
        List<Votation> votationList = new ArrayList<Votation>();
        Votation vot =new Votation();
        vot.setId(1);
        vot.setUserID("1");
        vot.setValue(4);

        Votation vot2 =new Votation();
        vot2.setId(2);
        vot2.setUserID("2");
        vot2.setValue(4);

        Votation vot3 =new Votation();
        vot3.setId(3);
        vot3.setUserID("3");
        vot3.setValue(4);

        votationList.add(vot);
        votationList.add(vot3);
        votationList.add(vot2);
        return votationList;
    }
}
