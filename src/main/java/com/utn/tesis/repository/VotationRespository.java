package com.utn.tesis.repository;

import com.utn.tesis.model.Votation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotationRespository extends JpaRepository<Votation,Integer> {
    List<Votation> findByUserStoryIdIn(List<Integer> idUs);
}
