package com.library.user.stats.application.entity;

import com.library.user.stats.application.enums.Rating;
import com.library.user.stats.application.enums.Status;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserStats {

    @Id
    private String id;
    private String userId;
    private String bookId;
    private Status status;
    private Rating rating;

}
