package com.library.user.stats.application.util;

import com.library.user.stats.application.dto.UserStatsDTO;
import com.library.user.stats.application.entity.UserStats;

public class DTOUtil {

    public UserStatsDTO userStatsToDTO(UserStats userStats){
        UserStatsDTO userStatsDTO = new UserStatsDTO.Builder(userStats.getId())
                .withUserId(userStats.getUserId())
                .withBookId(userStats.getBookId())
                .withRating(userStats.getRating())
                .withStatus(userStats.getStatus())
                .build();
        return  userStatsDTO;
    }

    public UserStats dtoToUserStats(UserStatsDTO dto){
        UserStats userStats = new UserStats();
        userStats.setId(dto.getId());
        userStats.setUserId(dto.getUserId());
        userStats.setBookId(dto.getBookId());
        userStats.setStatus(dto.getStatus());
        userStats.setRating(dto.getRating());
        return  userStats;
    }
}
