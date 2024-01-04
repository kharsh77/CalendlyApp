A calendly similar app which allows users to schedule and block meeting timeslots.

Types of Meeting:
1. Event: An organiser can create a meeting event with available timeSlot. Other user can choose a timeSlot and book a slot for meeting.   
2. InviteEvent:A user can invite other user for a one-one meeting.

Operations:
1. register: A user can register with a name and email.
2. createEvent: A meeting of type 'Event' can be created.
3. bookSlot: A specific timeslot can be booked for meeting type 'Event'.
4. confirmSlot: Organiser can accept or reject a meeting request.
5. createInvite: A meeting of type 'InviteEvent' can be created.
6. getAvailableSlots: Get all unbooked and available slots for a meeting type 'Event'.
7. checkMyAvailability: A user can check their available timeSlots for a day.
8. viewMyScheduledEvents: A user can view a specific booked meeting for a day.
9. checkOverlap: Available timeSlots between two users can be checked.

Assumptions:
1. IST timezone is assumed for all operations
2. Update and delete operations are not supported
3. Operations are assumed to be operated in thread safe condition
4. Meeting can be scheduled with granularity of minutes
5. Meetings have definite endtime and recurring events are not supported

A sample set of operations which can be performed:

        // 1. Register User
        User user1 = userService.register("John", "john@gmail.com");

        Interval NineFiveInterval = new Interval(parseDate("2023-12-23 09:00"), parseDate("2023-12-23 17:00"));

        // 2. Create a event
        calenderService.createEvent("Interview:: System Engineer", user1,
                parseDate("2023-12-23 00:00"), parseDate("2023-12-26 00:00"), NineFiveInterval,
                35, 10, 10, 3);


        // 3. Get available slots for the event
        calenderService.getAvailableSlots("1", parseDate("2023-12-24 00:00"));

        // 4. Book a slot in event
        calenderService.bookSlot("1", parseDate("2023-12-24 10:30"), "mark@gmail.com");
        calenderService.bookSlot("1", parseDate("2023-12-24 12:00"), "mark2@gmail.com");

        // 5. Check my availability for a day
        calenderService.checkMyAvailability("1", parseDate("2023-12-24 00:00"), false);

        // 6. Check my scheduled schedules
        calenderService.viewMyScheduledEvents("1", parseDate("2023-12-24 00:00"));

        // 7. confirm a slot
        calenderService.confirmSlot("1", "1", ScheduleStatus.CONFIRMED);
        calenderService.confirmSlot("1", "2", ScheduleStatus.REJECTED);

        calenderService.viewMyScheduledEvents("1", parseDate("2023-12-24 00:00"));

        // Check my availability for a day after slots are confirmed
        calenderService.checkMyAvailability("1", parseDate("2023-12-24 00:00"), false);

        calenderService.getAvailableSlots("1", parseDate("2023-12-24 00:00"));

        // 8. Invite for a one on one
        User user2 = userService.register("Ron", "ron@gmail.com");

        calenderService.createInvite("Invite: Project Discussion", user1, user2, parseDate("2023-12-24 14:00"), 30);
        calenderService.confirmSlot("2", "3", ScheduleStatus.CONFIRMED);

        calenderService.checkMyAvailability("1", parseDate("2023-12-24 00:00"), false);

        calenderService.checkMyAvailability("2", parseDate("2023-12-24 00:00"), false);

        calenderService.viewMyScheduledEvents("1", parseDate("2023-12-24 00:00"));
        calenderService.viewMyScheduledEvents("2", parseDate("2023-12-24 00:00"));

        // 9. Check overlap to find available timeSlot
        calenderService.checkOverlap("1", "2", parseDate("2023-12-24 00:00"));


Next Steps:
1. Recurring type of meeting can be extended by the app further.
2. Notification operations can be extended for notifying users when a meeting get created or confirmed.
3. Making few operations thread safe by using locks
4. Handling more edge cases


A sample output from the above operations:

    -----Operation: createEvent----
    New Event Created: Event {id='1', name='Interview:: System Engineer', creator=john@gmail.com}
    -----End of an operation----
    
    
    -----Operation: getAvailableSlots for eventId: 1----
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
    
    
    -----Operation: bookSlot for eventId: 1----
    Slot booked successfully at: Slot starts:Sun Dec 24 10:30:00 IST 2023
     Slot booked. Waiting for confirmation for slot at: Sun Dec 24 10:30:00 IST 2023
    -----End of an operation----
    
    
    -----Operation: bookSlot for eventId: 1----
    Slot booked successfully at: Slot starts:Sun Dec 24 12:00:00 IST 2023
     Slot booked. Waiting for confirmation for slot at: Sun Dec 24 12:00:00 IST 2023
    -----End of an operation----
    
    
    -----Operation: checkMyAvailability for userId: 1----
        Available Slot:: from: Sun Dec 24 00:00:00 IST 2023 to: Sun Dec 24 23:59:00 IST 2023
    
    -----End of an operation----
    
    
    -----Operation: viewMyScheduledEvents for userId: 1----
     Schedule:: EventId:1 ScheduleId:1 status:PENDING from:Sun Dec 24 10:30:00 IST 2023 to: Sun Dec 24 11:15:00 IST 2023
     Schedule:: EventId:1 ScheduleId:2 status:PENDING from:Sun Dec 24 12:00:00 IST 2023 to: Sun Dec 24 12:45:00 IST 2023
    
    -----End of an operation----
    
    
    -----Operation: confirmSlot  eventId: 1 scheduleId: 1 action: CONFIRMED----
     Slot updated
    -----End of an operation----
    
    
    -----Operation: confirmSlot  eventId: 1 scheduleId: 2 action: REJECTED----
     Slot updated
    -----End of an operation----
    
    
    -----Operation: viewMyScheduledEvents for userId: 1----
     Schedule:: EventId:1 ScheduleId:1 status:CONFIRMED from:Sun Dec 24 10:30:00 IST 2023 to: Sun Dec 24 11:15:00 IST 2023
     Schedule:: EventId:1 ScheduleId:2 status:REJECTED from:Sun Dec 24 12:00:00 IST 2023 to: Sun Dec 24 12:45:00 IST 2023
    
    -----End of an operation----
    
    
    -----Operation: checkMyAvailability for userId: 1----
        Available Slot:: from: Sun Dec 24 00:00:00 IST 2023 to: Sun Dec 24 10:30:00 IST 2023
        Available Slot:: from: Sun Dec 24 11:15:00 IST 2023 to: Sun Dec 24 23:59:00 IST 2023
    
    -----End of an operation----
    
    
    -----Operation: getAvailableSlots for eventId: 1----
        Available Slot:: from: Sun Dec 24 09:00:00 IST 2023 to: Sun Dec 24 09:45:00 IST 2023
        Available Slot:: from: Sun Dec 24 09:45:00 IST 2023 to: Sun Dec 24 10:30:00 IST 2023
        Available Slot:: from: Sun Dec 24 11:15:00 IST 2023 to: Sun Dec 24 12:00:00 IST 2023
        Available Slot:: from: Sun Dec 24 12:00:00 IST 2023 to: Sun Dec 24 12:45:00 IST 2023
        Available Slot:: from: Sun Dec 24 12:45:00 IST 2023 to: Sun Dec 24 13:30:00 IST 2023
        Available Slot:: from: Sun Dec 24 13:30:00 IST 2023 to: Sun Dec 24 14:15:00 IST 2023
        Available Slot:: from: Sun Dec 24 14:15:00 IST 2023 to: Sun Dec 24 15:00:00 IST 2023
        Available Slot:: from: Sun Dec 24 15:00:00 IST 2023 to: Sun Dec 24 15:45:00 IST 2023
        Available Slot:: from: Sun Dec 24 15:45:00 IST 2023 to: Sun Dec 24 16:30:00 IST 2023
    
    -----End of an operation----
    
    
    ----------Operation: createInvite---------
    New InviteEvent Created: Event {id='2', name='Invite: Project Discussion', creator=john@gmail.com}
    ----------End of an operation---------
    
    
    -----Operation: confirmSlot  eventId: 2 scheduleId: 3 action: CONFIRMED----
     Slot updated
    -----End of an operation----
    
    
    -----Operation: checkMyAvailability for userId: 1----
        Available Slot:: from: Sun Dec 24 00:00:00 IST 2023 to: Sun Dec 24 10:30:00 IST 2023
        Available Slot:: from: Sun Dec 24 11:15:00 IST 2023 to: Sun Dec 24 14:00:00 IST 2023
        Available Slot:: from: Sun Dec 24 14:30:00 IST 2023 to: Sun Dec 24 23:59:00 IST 2023
    
    -----End of an operation----
    
    
    -----Operation: checkMyAvailability for userId: 2----
        Available Slot:: from: Sun Dec 24 00:00:00 IST 2023 to: Sun Dec 24 14:00:00 IST 2023
        Available Slot:: from: Sun Dec 24 14:30:00 IST 2023 to: Sun Dec 24 23:59:00 IST 2023
    
    -----End of an operation----
    
    
    -----Operation: viewMyScheduledEvents for userId: 1----
     Schedule:: EventId:1 ScheduleId:1 status:CONFIRMED from:Sun Dec 24 10:30:00 IST 2023 to: Sun Dec 24 11:15:00 IST 2023
     Schedule:: EventId:1 ScheduleId:2 status:REJECTED from:Sun Dec 24 12:00:00 IST 2023 to: Sun Dec 24 12:45:00 IST 2023
     Schedule:: EventId:2 ScheduleId:3 status:CONFIRMED from:Sun Dec 24 14:00:00 IST 2023 to: Sun Dec 24 14:30:00 IST 2023
    
    -----End of an operation----
    
    
    -----Operation: viewMyScheduledEvents for userId: 2----
     Schedule:: EventId:2 ScheduleId:3 status:CONFIRMED from:Sun Dec 24 14:00:00 IST 2023 to: Sun Dec 24 14:30:00 IST 2023
    
    -----End of an operation----
    
    
    ---------Operation: checkOverlap for available slots of userId: 1 for userId: 2--------
        Available Slot:: from: Sun Dec 24 00:00:00 IST 2023 to: Sun Dec 24 10:30:00 IST 2023
        Available Slot:: from: Sun Dec 24 11:15:00 IST 2023 to: Sun Dec 24 14:00:00 IST 2023
        Available Slot:: from: Sun Dec 24 14:30:00 IST 2023 to: Sun Dec 24 23:59:00 IST 2023
    
    ---------End of an operation--------