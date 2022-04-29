package com.utn.tesis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    @NotNull(message = "This Field is mandatory")
    private String name;
    @NotNull(message = "This Field is mandatory")
    private String email;
    @NotNull(message = "This Field is mandatory")
    private String password;
}
