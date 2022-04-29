package com.utn.tesis.repository;

import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepostory extends JpaRepository<Party,String> {
}
