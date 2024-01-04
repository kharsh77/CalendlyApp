package org.example.Interface;

import org.example.Entity.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


public interface CalenderInterface {
    Availability checkMyAvailability(String userId, Date date, boolean skipPrint) throws Exception;
    Event createEvent(String name, String creator, Date startDate, Date endDate,
                      Interval dailySlot, Integer duration, Integer gap, Integer totalSlots, Integer dailyLimit) throws Exception;
    Availability getAvailableSlots(String eventId, Date date) throws Exception;
    String bookSlot(String eventId, Date slotTime, String bookingEmail) throws Exception;
    String confirmSlot(String eventId, String scheduleId, ScheduleStatus action) throws Exception;

    // Invite >> Schedule >> accept
    Availability checkOverlap(String userId1, String userId2, Date date) throws Exception;
    Event createInvite(String name, String creator, String invitee, Date startTime, Integer duration) throws Exception;
    Event getEvent(String eventId);

    // All invites
    ArrayList<Schedule> viewMyScheduledEvents(String userId, Date date) throws Exception;
}
