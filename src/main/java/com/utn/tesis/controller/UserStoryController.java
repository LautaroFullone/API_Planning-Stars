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
@RequestMapping("/userStory")
public class UserStoryController {

    private UserStoryService userStoryService;

    @Autowired
    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }
//-------------------------------------------- P O S T -----------------------------------------------------------------
    @PostMapping
    public ResponseEntity<UserStory>addUserStory(@RequestBody UserStory us){
      UserStory userStory= userStoryService.creatUs(us);
      return ResponseEntity.ok(userStory);
    }


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
// -------------------------------------------- P U T ------------------------------------------------------------------
 @PutMapping("/{usId}")
 public  ResponseEntity<UserStory> modifyUserStory(@PathVariable Integer usId,@RequestBody UserStory newUserStory){
       UserStory userStory= userStoryService.modifyUs(usId,newUserStory);
        return ResponseEntity.ok(userStory);
 }
// -------------------------------------------- D E L E T --------------------------------------------------------------
@DeleteMapping("/{idUS}")
public  ResponseEntity deleteParty(@PathVariable Integer idUS){
    userStoryService.deleteUs(idUS);

    return ResponseEntity.ok().build();
}

}
