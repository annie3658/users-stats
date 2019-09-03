package com.library.user.stats.application.controller;

import com.library.user.stats.application.dto.UserStatsDTO;
import com.library.user.stats.application.entity.UserStats;
import com.library.user.stats.application.service.UserStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stats")
public class UserStatsController {
    private UserStatsService service;

    @Autowired
    public UserStatsController(UserStatsService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserStatsDTO> getAllCovers(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserStatsDTO getUserStatsById(@PathVariable("id") String id){
        return service.findUserStatsById(id);
    }

    @PutMapping
    public UserStatsDTO insert(@Valid @RequestBody UserStatsDTO userStats){
        return service.insert(userStats);
    }

    @PostMapping
    public UserStatsDTO update (@Valid @RequestBody UserStatsDTO userStats){
        return service.update(userStats);
    }

    @DeleteMapping("/{id}")
    public  void delete(@PathVariable("id") String id) {
        service.delete(id);
    }


}
