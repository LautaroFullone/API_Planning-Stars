package com.utn.tesis.controller;

import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.service.UserStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/us")
public class UserStoryController {

    private UserStoryService userStoryService;

    @Autowired
    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }
//-------------------------------------------- P O S T -----------------------------------------------------------------



// -------------------------------------------- G E T ------------------------------------------------------------------
@GetMapping
public ResponseEntity<List<UserStory>> getUsers() {
    List<UserStory>usList = userStoryService.getUs();

    if(usList.isEmpty())
        return ResponseEntity.noContent().build();
    else
        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Total-Elements", Integer.toString(usList.size()))
                .body(usList);
}
// -------------------------------------------- D E L E T --------------------------------------------------------------


}
