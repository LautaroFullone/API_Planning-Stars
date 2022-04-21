package com.utn.tesis.repository;

import com.utn.tesis.model.Party;
import com.utn.tesis.model.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStoryepository extends JpaRepository<UserStory,Integer> {
}
