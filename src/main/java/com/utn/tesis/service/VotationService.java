package com.utn.tesis.service;

import com.utn.tesis.model.Votation;
import com.utn.tesis.repository.VotationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotationService {
    private VotationRespository votationRespository;
    @Autowired
    public VotationService(VotationRespository votationRespository) {
        this.votationRespository=votationRespository;
    }

    public List<Votation> getVotationFromUserstories(List<Integer> idUs) {
        return this.votationRespository.findByUserStoryIdIn(idUs);
    }
}
