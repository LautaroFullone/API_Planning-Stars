package com.utn.tesis.controller;

import com.utn.tesis.model.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
@RequestMapping("/person")
public class PersonaController {
    @GetMapping()
    public Person getPerson(){
        return new Person("Lautaro", "Fullone");

    }

}
