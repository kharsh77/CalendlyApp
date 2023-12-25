package org.example;

import org.apache.commons.lang3.time.DateParser;
import org.apache.commons.lang3.time.DateUtils;
import org.example.Entity.*;
import org.example.Utils.Helper;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{

    static Date parseDate(String str) throws ParseException {
        return Helper.stringToDate(str);
    }

    public static void main(String[] args) throws ParseException, NoSuchMethodException {
        UserService userService = new UserService();
        CalenderService calenderService = new CalenderService(userService);

        // 1. Register User
        User user1 = userService.register("John", "john@gmail.com");

        Interval NineFiveInterval = new Interval(parseDate("2023-12-23 09:00"), parseDate("2023-12-23 17:00"));

        // 2. Create a event
        OneEvent interviewEvent = calenderService.createEvent("Interview:: System Engineer", user1,
                parseDate("2023-12-23 00:00"), parseDate("2023-12-26 00:00"), NineFiveInterval,
                35, 10, 10, 3);


        // 3. Book available slot for a event
        calenderService.getAvailableSlots("1", parseDate("2023-12-24 10:30"));

        // 4. Book a slot in event
        calenderService.bookSlot("1", parseDate("2023-12-24 10:30"), "mark@gmail.com");
        calenderService.bookSlot("1", parseDate("2023-12-24 12:00"), "mark2@gmail.com");

        // 5. Check my availability for a day
        calenderService.checkMyAvailability("1", parseDate("2023-12-24 00:00"));

        // 6. Check my scheduled schedules
        calenderService.viewMyScheduledEvents("1", parseDate("2023-12-24 00:00"));

        // 7. confirm a slot
        calenderService.confirmSlot("1", "1", ScheduleStatus.CONFIRMED);
        calenderService.confirmSlot("1", "2", ScheduleStatus.CONFIRMED);

        calenderService.viewMyScheduledEvents("1", parseDate("2023-12-24 00:00"));

        // 8. Invite for a one on one
        User user2 = userService.register("Ron", "ron@gmail.com");

        calenderService.createInvite("Invite: Project Discussion", user1, user2, parseDate("2023-12-24 014:00"), 30);
        calenderService.confirmSlot("2", "3", ScheduleStatus.CONFIRMED);

        calenderService.checkMyAvailability("1", parseDate("2023-12-24 00:00"));

        calenderService.checkMyAvailability("2", parseDate("2023-12-24 00:00"));

        // 9. Check overlap to find available timeSlot
        calenderService.checkOverlap("1", "2", parseDate("2023-12-24 10:30"));

    }
}





