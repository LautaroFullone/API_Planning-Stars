package com.utn.tesis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name ="votations")
public class Votation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "This Field is mandatory")
    private String userID;

    @NotNull(message = "This Field is mandatory")
    private Integer value;

    @ToString.Exclude  @JsonIgnore
    @ManyToOne  @JoinColumn(name = "id_userStory")
    private UserStory userStory;


}
