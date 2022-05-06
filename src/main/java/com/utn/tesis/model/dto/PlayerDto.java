package com.utn.tesis.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.tesis.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDto {
    private Integer id;
    private String name;
    private String email;

    public static PlayerDto from(User user) {
        return PlayerDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

}