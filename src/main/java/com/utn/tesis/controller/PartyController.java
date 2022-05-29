package com.utn.tesis.controller;

import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.model.dto.PlayerDto;
import com.utn.tesis.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/{idParty}")
    public ResponseEntity<Party> getPartyById(@PathVariable String idParty ){
        Party searchParty = partyService.getPartyById(idParty);
        return ResponseEntity.ok(searchParty);
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
    @GetMapping("/{idParty}/players")
    public ResponseEntity<List<PlayerDto>> getPartyPlayersList(@PathVariable String idParty) {
        List<PlayerDto> playersList = partyService.getPartyPlayersList(idParty);
        if(playersList.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Total-Elements", Integer.toString(playersList.size()))
                    .body(playersList);
    }
    @GetMapping("/{idParty}/userstories")
    public ResponseEntity<List<UserStory>> getPartyUsList(@PathVariable String idParty) {
        List<UserStory> usList = partyService.getPartyUsList(idParty);
        if(usList.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Total-Elements", Integer.toString(usList.size()))
                    .body(usList);
    }
// -------------------------------------------- P U T ------------------------------------------------------------------
    @PutMapping("/{idParty}/user/{idUser}")
    public ResponseEntity addUserToParty(@PathVariable Integer idUser,@PathVariable String idParty){
        partyService.addUserToParty(idUser,idParty);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idParty}/userstory/{userStory}")
    public ResponseEntity addUserStoryToParty(@PathVariable String idParty,@PathVariable Integer userStory){
        partyService.adduserStoryToParty(idParty,userStory);
        return ResponseEntity.ok().build();
    }

// -------------------------------------------- D E L E T --------------------------------------------------------------
    @DeleteMapping("/{idParty}")
    public ResponseEntity deleteParty(@PathVariable String idParty){
        partyService.deleteParty(idParty);
        return ResponseEntity.ok().build();
    }

}
