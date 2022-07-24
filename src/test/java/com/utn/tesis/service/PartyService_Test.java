package com.utn.tesis.service;

import com.utn.tesis.UTILS_TESTCONSTANTS;
import com.utn.tesis.exception.types.PartyNotFoundException;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.repository.PartyRepository;
import com.utn.tesis.repository.PartyRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class PartyService_Test {

    private PartyRepository partyRepostory;
    private UserStoryService  userStoryService;
    private VotationService votationService;
    private  PartyService partyService;

    @Before
    public void setUp(){
        partyRepostory = mock(PartyRepository.class);
        userStoryService = mock(UserStoryService.class);
        votationService = mock(VotationService.class);
        partyService = new PartyService(partyRepostory,userStoryService,votationService);
    }
    @Test
    public void addParty_TestOK(){
        Party party= UTILS_TESTCONSTANTS.getParty();
        when(partyRepostory.save(any())).thenReturn(party);
        Party response = partyService.addParty(party);

        assertEquals(party.getId(),response.getId());
    }
    @Test
    public void getPartyById_TestOK() {
        Party party = UTILS_TESTCONSTANTS.getParty();
        when(partyRepostory.findById("testID")).thenReturn(Optional.of(party));

        Party response = partyService.getPartyById("testID");
        assertEquals(party.getId(),response.getId());
    }
    @Test
    public void getPartyById_TestBAD() {
        assertThrows(PartyNotFoundException.class,()->partyService.getPartyById("testID"));
    }

    @Test
    public void getParties_TestOK() {
        List<Party> partyList = UTILS_TESTCONSTANTS.getPartyList();
        when(partyRepostory.findAll()).thenReturn(partyList);
        List<Party> response = partyService.getParties();
        assertEquals(partyList.size(),response.size());
        assertEquals(partyList.get(0).getId(),response.get(0).getId());
    }

    @Test
    public void deleteParty_TestOK() {
        when(partyRepostory.existsById(anyString())).thenReturn(true);
        doNothing().when(partyRepostory).deleteById(anyString());
        partyService.deleteParty("idTest");
    }

    @Test
    public void deleteParty_TestBAD() {
        assertThrows(PartyNotFoundException.class,()->partyService.deleteParty("testID"));
    }



}
