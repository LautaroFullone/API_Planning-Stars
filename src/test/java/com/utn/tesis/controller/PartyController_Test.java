package com.utn.tesis.controller;

import com.utn.tesis.UTILS_TESTCONSTANTS;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.model.Votation;
import com.utn.tesis.service.PartyService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PartyController_Test {

    PartyService partyService;
    PartyController partyController;
    @Before
    public void setUp(){
        partyService =mock(PartyService.class);
        partyController=new PartyController(partyService);
    }

    @Test
    public void addParty_TestOK(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Party party = UTILS_TESTCONSTANTS.getParty();

        when(partyService.addParty(party)).thenReturn(party);

        ResponseEntity response = partyController.addParty(party);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(party.getId(), ((Party) response.getBody()).getId());

    }
    @Test
    public void getPartyById_TestOK(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Party party = UTILS_TESTCONSTANTS.getParty();

        when(partyService.getPartyById(any())).thenReturn(party);

        ResponseEntity response = partyController.getPartyById("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(party.getId(), ((Party) response.getBody()).getId());

    }
    @Test
    public void getParties_TestOK(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<Party> partyList  = UTILS_TESTCONSTANTS.getPartyList();

        when(partyService.getParties()).thenReturn(partyList);

        ResponseEntity<List<Party>> response = partyController.getParties();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }
    @Test
    public void getParties_TestBAD(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<Party> partyList  = new ArrayList<Party>();

        when(partyService.getParties()).thenReturn(partyList);

        ResponseEntity<List<Party>> response = partyController.getParties();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    public void getPartyUsList_TestOK(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<UserStory> userStoryList  = UTILS_TESTCONSTANTS.getUsList();

        when(partyService.getPartyUsList(any())).thenReturn(userStoryList);

        ResponseEntity<List<UserStory>> response = partyController.getPartyUsList("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }
    @Test
    public void getPartyUsList_TestBAD(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<UserStory> userStoryList  = new ArrayList<UserStory>();

        when(partyService.getPartyUsList(any())).thenReturn(userStoryList);

        ResponseEntity<List<UserStory>> response = partyController.getPartyUsList("Test");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    public void getPartyVotationList_TestOK(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<Votation> votationList  = UTILS_TESTCONSTANTS.getVotationList();

        when(partyService.getPartyVotation(any())).thenReturn(votationList);

        ResponseEntity<List<Votation>> response = partyController.getPartyVotationList("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }
    @Test
    public void getPartyVotationList_TestBAD(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<Votation> votationList  = new ArrayList<Votation>();

        when(partyService.getPartyVotation(any())).thenReturn(votationList);

        ResponseEntity<List<Votation>> response = partyController.getPartyVotationList("Test");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void addUserStoryToParty_TestOK(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<List<Votation>> response = partyController.addUserStoryToParty("test",1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteParty_TestOK(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<List<Votation>> response = partyController.deleteParty("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
