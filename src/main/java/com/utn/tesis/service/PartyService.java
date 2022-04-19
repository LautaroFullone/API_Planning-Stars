package com.utn.tesis.service;

import com.utn.tesis.model.Party;
import com.utn.tesis.repository.PartyRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartyService {
    @Autowired
    private PartyRepostory partyRepostory;

    public Party addParty(Party party) {
        System.out.println(party);
        return partyRepostory.save(party);
    }
}
