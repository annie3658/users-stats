package com.library.user.stats.application.service;

import ch.qos.logback.classic.Logger;
import com.library.user.stats.application.dto.UserStatsDTO;
import com.library.user.stats.application.entity.UserStats;
import com.library.user.stats.application.repository.UserStatsRepository;
import com.library.user.stats.application.util.DTOUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserStatsService {

    private UserStatsRepository repository;
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserStatsService.class);
    private DTOUtil dtoUtil = new DTOUtil();

    @Autowired
    public UserStatsService(UserStatsRepository repository) {
        this.repository = repository;
    }

    private UserStats findById(String id){
        Optional<UserStats> userStats = repository.findById(id);
        if (userStats.isPresent()) {
            return userStats.get();
        }
        LOGGER.warn("The UserStats with id {} was not found", id);
        return null;
    }

    public UserStatsDTO findUserStatsById(String id){
        return dtoUtil.userStatsToDTO(findById(id));
    }

    public List<UserStatsDTO> getAll(){
        List<UserStats> userStats = repository.findAll();
        if(userStats.isEmpty()){
            LOGGER.warn("The userStats list is empty");
        }
        return userStats.stream()
                .map(cover -> dtoUtil.userStatsToDTO(cover))
                .collect(Collectors.toList());
    }

    public UserStatsDTO insert(UserStatsDTO userStatsDTO){

        return dtoUtil.userStatsToDTO(repository.insert(dtoUtil.dtoToUserStats(userStatsDTO)));
    }

    public UserStatsDTO update(UserStatsDTO userStatsDTO){

        return dtoUtil.userStatsToDTO(repository.save(dtoUtil.dtoToUserStats(userStatsDTO)));
    }

    public void delete(String id){
        LOGGER.info("Deleted UserStats with id: " + id);
        repository.deleteById(id);

    }

}
