package org.example.Interface;

import org.example.Entity.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


public interface CalenderInterface {
    Availability checkMyAvailability(String userId, Date date, boolean skipPrint) throws ParseException;
    OneEvent createEvent(String name, User creator, Date startDate, Date endDate,
                      Interval dailySlot, Integer duration, Integer gap, Integer totalSlots, Integer dailyLimit);
    Availability getAvailableSlots(String eventId, Date date) throws ParseException;
    void bookSlot(String eventId, Date slotTime, String bookingEmail) throws ParseException;
    void confirmSlot(String eventId, String scheduleId, ScheduleStatus action);

    // Invite >> Schedule >> accept
    Availability checkOverlap(String userId1, String userId2, Date date) throws ParseException;
    OneEvent createInvite(String name, User creator, User invitee, Date startTime, Integer duration);
    Event getEvent(String eventId);

    // All invites
    ArrayList<OneEvent> viewMyScheduledEvents(String userId, Date date);
}
