package com.utn.tesis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "This Field is mandatory")
    private String name;
    @NotNull(message = "This Field is mandatory")
    private Integer maxPlayer;
    @NotNull(message = "This Field is mandatory")
    private Boolean isActive;
    @NotNull(message = "This Field is mandatory")
    private String createdBy;
    @NotNull(message = "This Field is mandatory")
    private String partyOwnerId;
    @NotNull(message = "This Field is mandatory")
    private String createdDate;



    @JsonIgnore
    @OneToMany(mappedBy = "party")
    private List<UserStory> userStories;



}
