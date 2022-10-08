package com.utn.tesis.controller;

import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.model.Votation;
import com.utn.tesis.model.dto.PlanningDetailDTO;
import com.utn.tesis.service.UserStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        UserStory userStory = userStoryService.creatUs(us);
        return ResponseEntity.ok(userStory);
    }
    @PostMapping
    @RequestMapping("/{usId}/planning-result/{storyPoint}")
    public ResponseEntity<UserStory>addStoryPoint(@PathVariable Integer usId,@PathVariable Integer storyPoint){
        UserStory userStory= userStoryService.addStoryPoint(usId,storyPoint);
        return ResponseEntity.ok(userStory);
    }
    @PostMapping
    @RequestMapping("/{idUserStory}/planning-restart")
        public ResponseEntity planningRestartVotation(@PathVariable Integer idUserStory ){
           userStoryService.restartVotation(idUserStory);
           return ResponseEntity.ok("The Votation was clear");
        }

// -------------------------------------------- G E T ------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<UserStory>> getUserStories() {
        List<UserStory>usList = userStoryService.getUserStories();
        if(usList.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                .header("X-Total-Elements", Integer.toString(usList.size()))
                .body(usList);
    }
    @GetMapping("/{userStoryId}/planning-details/{connectedUsers}")
    public ResponseEntity<PlanningDetailDTO>getPlanningDetails(@PathVariable Integer userStoryId,@PathVariable Integer connectedUsers){
        PlanningDetailDTO planningDetail = userStoryService.getPlanningDetail(userStoryId,connectedUsers);
        return ResponseEntity.ok(planningDetail);
    }
    @GetMapping("/{userStoryId}/votations")
    public ResponseEntity<List<Votation>> getUsVotationList(@PathVariable Integer userStoryId) {
        List<Votation> votationsList = userStoryService.getUserStoryVotations(userStoryId);
        if(votationsList.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Total-Elements", Integer.toString(votationsList.size()))
                    .body(votationsList);
    }
// -------------------------------------------- P U T ------------------------------------------------------------------
     @PutMapping("/{userStoryId}")
     public ResponseEntity<UserStory> modifyUserStory(@PathVariable Integer userStoryId, @RequestBody UserStory newUserStory){
        UserStory userStory= userStoryService.modifyUs(userStoryId,newUserStory);
        return ResponseEntity.ok(userStory);
     }

    @PutMapping("/{userStoryId}/votation")
    public ResponseEntity addVotationIntoUserStory(@PathVariable Integer userStoryId, @RequestBody Votation votation){
        userStoryService.addVotationIntoUserStory(votation, userStoryId);
        return ResponseEntity.ok().build();
    }
// -------------------------------------------- D E L E T --------------------------------------------------------------
    @DeleteMapping("/{userStoryId}")
    public  ResponseEntity deleteParty(@PathVariable Integer userStoryId){
        userStoryService.deleteUs(userStoryId);
        return ResponseEntity.ok().build();
    }

}
