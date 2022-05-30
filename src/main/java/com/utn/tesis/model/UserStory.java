package com.utn.tesis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.awt.*;

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
    private String tag;
    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    private Integer storyPoints;
    private String sprint;
    private String workArea;
    private String storyWritter;
    private String fileLink;
    private boolean isActive = true;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_party")
    private Party party;

    @Override
    public String toString() {
        return "UserStory{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", party=" + party +
                '}';
    }
}

