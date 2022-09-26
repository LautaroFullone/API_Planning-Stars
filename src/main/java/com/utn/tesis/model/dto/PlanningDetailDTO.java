package com.utn.tesis.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.tesis.model.Votation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanningDetailDTO {

    private UserVoteDTO maxVote;
    private  UserVoteDTO minVote;
    private List<UserVoteDTO> userVotes;
    private Integer averageVote;
}
