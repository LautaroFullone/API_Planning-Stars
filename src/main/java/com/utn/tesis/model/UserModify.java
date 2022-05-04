package com.utn.tesis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModify {

    private Integer id;
    private String name;
    private String password;

}
