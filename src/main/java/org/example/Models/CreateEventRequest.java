package org.example.Models;

import lombok.Getter;
import lombok.Setter;



// Interval NineFiveInterval = new Interval(parseDate("2023-12-23 09:00"), parseDate("2023-12-23 17:00"));
//
//        // 2. Create a event
//        calenderService.createEvent("Interview:: System Engineer", user1,
//                parseDate("2023-12-23 00:00"), parseDate("2023-12-26 00:00"), NineFiveInterval,
//                35, 10, 10, 4);

@Getter
public class CreateEventRequest {
    String name;
    String userId;
    String startDate;
    String endDate;
    String startTime;
    String endTime;
    Integer duration;
    Integer gap;
    Integer totalSlot;
    Integer dailyLimit;
}












