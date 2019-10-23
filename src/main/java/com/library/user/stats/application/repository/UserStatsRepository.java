package com.library.user.stats.application.repository;

import com.library.user.stats.application.entity.UserStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStatsRepository extends MongoRepository<UserStats, String> {

    List<UserStats> findUserStatsByUserId(String id);
    List<UserStats> findUserStatsByBookId(String id);
}
