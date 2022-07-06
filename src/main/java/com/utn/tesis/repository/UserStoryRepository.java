package com.utn.tesis.repository;

import com.utn.tesis.model.UserStory;
import com.utn.tesis.model.Votation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory,Integer> {


    List<UserStory> findByPartyId(String partyId);
}
