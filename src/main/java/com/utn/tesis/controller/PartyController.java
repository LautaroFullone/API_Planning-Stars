package com.utn.tesis.controller;

import com.utn.tesis.exception.types.EmailExistException;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import com.utn.tesis.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/party")
public class PartyController {
    @Autowired
    private PartyService partyService;

    @PostMapping("/add")
    public ResponseEntity addParty(@RequestBody Party party)  {
        System.out.println("soy una puta");
        Party prty = partyService.addParty(party);
        System.out.println(prty);
        return ResponseEntity.status(HttpStatus.OK).body(prty);
    }

}
