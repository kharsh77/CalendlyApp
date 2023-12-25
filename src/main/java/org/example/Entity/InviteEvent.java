package org.example.Entity;

import org.apache.commons.lang3.tuple.MutablePair;
import org.example.Utils.Helper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class InviteEvent extends Event{
    public InviteEvent(String name, User creator, User invitee, Interval interval) {
        super(name, creator, interval.getStartTime(), interval.getEndTime(), new Interval(
                interval.getStartTime(), Helper.addDureation(interval.getStartTime(), 1400)
        ), Helper.findDifferenceInMin(interval.getStartTime(), interval.getEndTime()), 0, 1, 1);
        this.addSchedule(interval, invitee.getEmail());
    }

    void addSchedule(Interval interval, String inviteeEmail){
        ArrayList<Schedule> a = new ArrayList<>();
        a.add(new Schedule(this.getId(), interval, inviteeEmail));
        this.setSchedules(a);
    }


    public Schedule getSchedule() {
        return this.schedules.get(0);
    }

    @Override
    public MutablePair<Event, Schedule> bookSlot(Date slotStartTime, String requesterEmail) throws ParseException {
        return null;
    }

    // Public methods
    public ArrayList<Interval> getAvailableSlots(Date date) throws ParseException {
        ArrayList<Schedule> schedules = new ArrayList<>();
        schedules.add(this.getSchedule());
        return Availability.getAvailableSlots(date, schedules);
    }
}
