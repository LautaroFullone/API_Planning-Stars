package com.utn.tesis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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

    @ToString.Exclude  @JsonIgnore
    @OneToMany(mappedBy = "party", cascade = CascadeType.REMOVE)
    private List<UserStory> userStories;


}
