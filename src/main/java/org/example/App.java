package org.example;

import org.example.Entity.*;
import org.example.Utils.Helper;

import java.text.ParseException;
import java.util.Date;

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


/*
-----Operation: createEvent----
New Event Created: Event {id='1', name='Interview:: System Engineer', creator=john@gmail.com}
-----End of an operation----


-----Operation: getAvailableSlots----
    Available Slot:: from: Sun Dec 24 09:00:00 IST 2023 to: Sun Dec 24 09:45:00 IST 2023
    Available Slot:: from: Sun Dec 24 09:45:00 IST 2023 to: Sun Dec 24 10:30:00 IST 2023
    Available Slot:: from: Sun Dec 24 10:30:00 IST 2023 to: Sun Dec 24 11:15:00 IST 2023
    Available Slot:: from: Sun Dec 24 11:15:00 IST 2023 to: Sun Dec 24 12:00:00 IST 2023
    Available Slot:: from: Sun Dec 24 12:00:00 IST 2023 to: Sun Dec 24 12:45:00 IST 2023
    Available Slot:: from: Sun Dec 24 12:45:00 IST 2023 to: Sun Dec 24 13:30:00 IST 2023
    Available Slot:: from: Sun Dec 24 13:30:00 IST 2023 to: Sun Dec 24 14:15:00 IST 2023
    Available Slot:: from: Sun Dec 24 14:15:00 IST 2023 to: Sun Dec 24 15:00:00 IST 2023
    Available Slot:: from: Sun Dec 24 15:00:00 IST 2023 to: Sun Dec 24 15:45:00 IST 2023
    Available Slot:: from: Sun Dec 24 15:45:00 IST 2023 to: Sun Dec 24 16:30:00 IST 2023

-----End of an operation----


-----Operation: bookSlot----
Slot booked successfully at: Slot starts:Sun Dec 24 10:30:00 IST 2023
 Slot booked. Waiting for confirmation for slot at: Sun Dec 24 10:30:00 IST 2023
-----End of an operation----


-----Operation: bookSlot----
Slot booked successfully at: Slot starts:Sun Dec 24 12:00:00 IST 2023
 Slot booked. Waiting for confirmation for slot at: Sun Dec 24 12:00:00 IST 2023
-----End of an operation----


-----Operation: checkMyAvailability----
    Available Slot:: from: Sun Dec 24 00:00:00 IST 2023 to: Sun Dec 24 23:59:00 IST 2023

-----End of an operation----


-----Operation: viewMyScheduledEvents----
 Schedule:: EventId:1 ScheduleId:1 status:PENDING from:Sun Dec 24 10:30:00 IST 2023 to: Sun Dec 24 11:15:00 IST 2023
 Schedule:: EventId:1 ScheduleId:2 status:PENDING from:Sun Dec 24 12:00:00 IST 2023 to: Sun Dec 24 12:45:00 IST 2023

-----End of an operation----


-----Operation: confirmSlot----
 Slot updated
-----End of an operation----


-----Operation: confirmSlot----
 Slot updated
-----End of an operation----


-----Operation: viewMyScheduledEvents----
 Schedule:: EventId:1 ScheduleId:1 status:CONFIRMED from:Sun Dec 24 10:30:00 IST 2023 to: Sun Dec 24 11:15:00 IST 2023
 Schedule:: EventId:1 ScheduleId:2 status:CONFIRMED from:Sun Dec 24 12:00:00 IST 2023 to: Sun Dec 24 12:45:00 IST 2023

-----End of an operation----


----------Operation: createInvite---------
New InviteEvent Created: org.example.Entity.InviteEvent@3532ec19
----------End of an operation---------


-----Operation: confirmSlot----
 Slot updated
-----End of an operation----


-----Operation: checkMyAvailability----
    Available Slot:: from: Sun Dec 24 00:00:00 IST 2023 to: Sun Dec 24 14:00:00 IST 2023
    Available Slot:: from: Sun Dec 24 14:30:00 IST 2023 to: Sun Dec 24 23:59:00 IST 2023

-----End of an operation----


-----Operation: checkMyAvailability----
    Available Slot:: from: Sun Dec 24 00:00:00 IST 2023 to: Sun Dec 24 14:00:00 IST 2023
    Available Slot:: from: Sun Dec 24 14:30:00 IST 2023 to: Sun Dec 24 23:59:00 IST 2023

-----End of an operation----


---------Operation: checkOverlap--------
-----Operation: checkMyAvailability----
    Available Slot:: from: Sun Dec 24 00:00:00 IST 2023 to: Sun Dec 24 14:00:00 IST 2023
    Available Slot:: from: Sun Dec 24 14:30:00 IST 2023 to: Sun Dec 24 23:59:00 IST 2023

-----End of an operation----


-----Operation: checkMyAvailability----
    Available Slot:: from: Sun Dec 24 00:00:00 IST 2023 to: Sun Dec 24 14:00:00 IST 2023
    Available Slot:: from: Sun Dec 24 14:30:00 IST 2023 to: Sun Dec 24 23:59:00 IST 2023

-----End of an operation----


    Available Slot:: from: Sun Dec 24 00:00:00 IST 2023 to: Sun Dec 24 14:00:00 IST 2023
    Available Slot:: from: Sun Dec 24 14:30:00 IST 2023 to: Sun Dec 24 23:59:00 IST 2023

---------End of an operation--------



 */




