package com.utn.tesis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;

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
    private TextArea description;
    private Integer storyPoint;
    private String sprint;
    private String workArea;
    private String stoyWritter;
    private String fileLink;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_party")
    private Party party;
}

