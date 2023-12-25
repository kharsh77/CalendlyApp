package org.example.Entity;

import org.apache.commons.lang3.tuple.MutablePair;
import org.example.Utils.Helper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

abstract public class Event {
    static int counter = 0;
    String id;
    String name;
    User creator;
    Date startDate;

    Date endDate;
    Interval dailySlot;
    Integer duration;
    Integer gap;
    Integer totalSlots;
    Integer dailyLimit;
    ArrayList<Schedule> schedules= new ArrayList<>();

    public Event(String name, User creator, Date startDate, Date endDate,
                    Interval dailySlot, Integer duration, Integer gap, Integer totalSlots, Integer dailyLimit) {
        id = Integer.toString(++counter);
        this.name=name; this.creator=creator; this.startDate=startDate; this.endDate=endDate; this.dailySlot=dailySlot;
        this.duration=duration; this.gap=gap;this.totalSlots=totalSlots;this.dailyLimit=dailyLimit;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getCreator() {
        return creator;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Interval getDailySlot() {
        return dailySlot;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getGap() {
        return gap;
    }

    public Integer getTotalSlots() {
        return totalSlots;
    }

    public Integer getDailyLimit() {
        return dailyLimit;
    }

    abstract public MutablePair<Event,Schedule> bookSlot(Date slotStartTime, String requesterEmail) throws ParseException;
    abstract public ArrayList<Interval> getAvailableSlots(Date date) throws ParseException;

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    abstract public Schedule getSchedule();


    public ArrayList<Schedule> getSchedules(Date date) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        for(Schedule s: this.getSchedules()){
            if(Helper.getDateToString(s.interval.getStartTime()).split(" ")[0]
                    .equals(Helper.getDateToString(date).split(" ")[0])){
                schedules.add(s);
            }
        }
        return schedules;
    }

    void setSchedules(ArrayList<Schedule> scheduled) {
        this.schedules = scheduled;
    }


}
