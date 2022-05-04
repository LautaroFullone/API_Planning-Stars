package com.utn.tesis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="parties")
public class Party {
    @Id
    private String id;

    private String name;
    private Integer maxPlayer;
    private Boolean isActive;
    private String createdBy;
    private String createdDate;

    @JsonIgnore
    @ManyToMany(mappedBy = "partiesList")
    private List<User> userList;

    @JsonIgnore
    @OneToMany(mappedBy = "party")
    private List<UserStory> userStories;



}
