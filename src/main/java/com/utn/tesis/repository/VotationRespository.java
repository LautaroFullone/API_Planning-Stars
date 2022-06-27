package com.utn.tesis.repository;

import com.utn.tesis.model.Votation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotationRespository extends JpaRepository<Votation,Integer> {
}
