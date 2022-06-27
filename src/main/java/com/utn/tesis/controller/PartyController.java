package com.utn.tesis.controller;

import com.utn.tesis.model.Party;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/party")
public class PartyController {

    private PartyService partyService;

    @Autowired
    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }
//-------------------------------------------- P O S T -----------------------------------------------------------------
    @PostMapping()
    public ResponseEntity addParty(@RequestBody Party partyToAdd)  {
        Party party = partyService.addParty(partyToAdd);
        return ResponseEntity.status(HttpStatus.OK).body(party);
    }

    @GetMapping("/{partyId}")
    public ResponseEntity<Party> getPartyById(@PathVariable String partyId ){
        Party searchParty = partyService.getPartyById(partyId);
        return ResponseEntity.status(HttpStatus.OK).body(searchParty);
    }
// -------------------------------------------- G E T ------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Party>> getParties() {
        List<Party> partyList = partyService.getParties();

        if(partyList.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Total-Elements", Integer.toString(partyList.size()))
                    .body(partyList);
    }


    @GetMapping("/{partyId}/userstories")
    public ResponseEntity<List<UserStory>> getPartyUsList(@PathVariable String partyId) {
        List<UserStory> usList = partyService.getPartyUsList(partyId);
        if(usList.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Total-Elements", Integer.toString(usList.size()))
                    .body(usList);
    }
// -------------------------------------------- P U T ------------------------------------------------------------------
    @PutMapping("/{partyId}/userstory/{userStory}")
    public ResponseEntity addUserStoryToParty(@PathVariable String partyId,@PathVariable Integer userStory){
        partyService.adduserStoryToParty(partyId, userStory);
        return ResponseEntity.ok().build();
    }
// -------------------------------------------- D E L E T --------------------------------------------------------------
    @DeleteMapping("/{partyId}")
    public ResponseEntity deleteParty(@PathVariable String partyId){
        partyService.deleteParty(partyId);
        return ResponseEntity.ok().build();
    }

}
