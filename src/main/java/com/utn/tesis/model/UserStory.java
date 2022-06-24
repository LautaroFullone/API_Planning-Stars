package com.utn.tesis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.awt.*;
import java.util.List;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name ="userstories")
public class UserStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "This Field is mandatory")
    private String tag;

    @NotNull(message = "This Field is mandatory")
    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    @Type(type = "org.hibernate.type.TextType")
    @NotNull(message = "This Field is mandatory")
    private String description;

    @NotNull(message = "This Field is mandatory")
    private String sprint;

    @NotNull(message = "This Field is mandatory")
    private String workArea;

    @NotNull(message = "This Field is mandatory")
    private String storyWritter;

    @NotNull(message = "This Field is mandatory")
    private String fileLink;

    private Integer storyPoints;
    private boolean isActive = true;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_party")
    private Party party;

    @ToString.Exclude  @JsonIgnore
    @OneToMany(mappedBy = "userStory")
    private List<Votation> votationsList;

}

