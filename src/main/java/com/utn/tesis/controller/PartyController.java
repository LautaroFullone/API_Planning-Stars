package com.utn.tesis.controller;

import com.utn.tesis.exception.types.EmailExistException;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
    public ResponseEntity addParty(@RequestBody Party party)  {
        Party prty = partyService.addParty(party);
        return ResponseEntity.status(HttpStatus.OK).body(prty);
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
    @GetMapping("/{idParty}/users")
    public ResponseEntity<List<User>> getPartyUserList(@PathVariable String idParty) {
        List<User> userList = partyService.getPartyUserList(idParty);
        if(userList.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Total-Elements", Integer.toString(userList.size()))
                    .body(userList);
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
    @PutMapping("/join/{idUser}/{idParty}")
    public void addUserToParty(@PathVariable Integer idUser,@PathVariable String idParty){
        partyService.addUserToParty(idUser,idParty);
    }

    @PutMapping("/{idParty}/userstory")
    public void addUserStoryToParty(@PathVariable String idParty,@RequestBody UserStory userStory){
        partyService.adduserStoryToParty(idParty,userStory);
    }
    @PutMapping("/{idParty}/userstory/{idUs}")
    public void modifyUs(@PathVariable Integer idUs,@RequestBody UserStory userStory,@PathVariable String idParty){
        partyService.modifyUs(idUs,userStory,idParty);
    }
// -------------------------------------------- D E L E T --------------------------------------------------------------
    @DeleteMapping("/{idParty}")
    public  ResponseEntity deleteParty(@PathVariable String idParty){
        partyService.deleteParty(idParty);

        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/userstory/{idUS}")
    public  ResponseEntity deleteParty(@PathVariable Integer idUS){
        partyService.deleteUs(idUS);

        return ResponseEntity.ok().build();
    }
}
