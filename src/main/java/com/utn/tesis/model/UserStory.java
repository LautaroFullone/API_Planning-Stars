package com.utn.tesis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="userstories")
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private Integer storyPoint;
    private String sprint;
    private String workArea;
    private String storyWritter;
    private String fileLink;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_party")
    private Party party;
}

