package org.example.Entity;

import org.apache.commons.lang3.tuple.MutablePair;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class RecurringEvent extends Event{
    ArrayList<Schedule> schedules;

    public RecurringEvent(String name, User creator, Date startDate, Date endDate, Interval dailySlot, Integer duration, Integer gap, Integer totalSlots, Integer dailyLimit) {
        super(name, creator, startDate, endDate, dailySlot, duration, gap, totalSlots, dailyLimit);
    }

    @Override
    public MutablePair<Event,Schedule> bookSlot(Date slotStartTime, String requesterEmail) throws ParseException {
        return null;
    }

    @Override
    public ArrayList<Interval> getAvailableSlots(Date date) throws ParseException {
        return null;
    }

    @Override
    public Schedule getSchedule() {
        return null;
    }
}
