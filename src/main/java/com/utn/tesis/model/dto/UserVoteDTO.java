package com.utn.tesis.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVoteDTO {
    private Integer vote;
    private String name;
    private Integer idUser;
}
