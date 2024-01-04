package org.example.Entity;

import org.apache.commons.lang3.tuple.MutablePair;
import org.example.Utils.Helper;

import java.text.ParseException;
import java.util.*;

public class OneEvent extends Event{
    HashMap<String, Integer> dailyLimitMap = new HashMap<>();

    public String createDailyStringMapKey(Date date){
        return Helper.getDateToString(date).split(" ")[0];
    }

    public int getDailyLimitMap(Date date) {
        String key = this.createDailyStringMapKey(date);
        if(this.dailyLimitMap.containsKey(key)){
            return this.dailyLimitMap.get(key);
        }
        return 0;
    }

    public void setDailyLimitMap(Date date) {
        String key = this.createDailyStringMapKey(date);
        Integer val = this.getDailyLimitMap(date) > 0 ? this.getDailyLimitMap(date)+1 : 1;
        this.dailyLimitMap.put(key, val);
    }

    public int dayLimitRemaining(Date date){
        return this.getDailyLimit() - this.getDailyLimitMap(date);
    }

    public OneEvent(String name, User creator, Date startDate, Date endDate,
                    Interval dailySlot, Integer duration, Integer gap, Integer totalSlots, Integer dailyLimit){

        super(name, creator, startDate, endDate, dailySlot, duration, gap, totalSlots, dailyLimit);
    }

    // Private
    private boolean checkLimits(Date date){
        if(date.before(this.getStartDate()) || date.after(this.getEndDate())){
            System.out.println("No slots available for this day");
            return false;
        }

        if(this.dayLimitRemaining(date) == 0) {
            System.out.println("No slots remaining for the day");
            return false;
        }

        if(this.schedules.size() >= this.totalSlots) {
            System.out.println("All slots booked");
            return false;
        }
        return true;
    }

    private Schedule createSchedule(Interval interval, String requesterEmail) {
        Schedule schedule = new Schedule(this.id, interval, requesterEmail);
        this.schedules.add(schedule);
        this.setDailyLimitMap(interval.getStartTime());
        return schedule;
    }

    private ArrayList<Interval> getBookedIntervals(){
        ArrayList<Interval> booked = new ArrayList<>();
        for(Schedule s: this.schedules){
            if(s.getStatus().equals(ScheduleStatus.CONFIRMED))
                booked.add(s.interval);
        }
        return booked;
    }

    private ArrayList<Interval> getAllIntervals(Date date, boolean[] slots) throws ParseException {
        ArrayList<Interval> out = new ArrayList<>();
        int timeOffset = this.duration + this.gap;

        Date slotStartime = Helper.getTime(date, dailySlot.getStartTime());
        Date slotEndime = Helper.addDureation(slotStartime, timeOffset);;

        for(int i=0; i<slots.length; i++){
            if(slots[i]) {
                out.add(new Interval(slotStartime, slotEndime));
            }

            slotStartime = slotEndime;
            slotEndime = Helper.addDureation(slotStartime, timeOffset);
        }

        return out;
    }

    // Public methods
    public ArrayList<Interval> getAvailableSlots(Date date) throws ParseException {
        if(!this.checkLimits(date)) {
            System.out.println("No Available slots");
            return new ArrayList<>();
        }

        int timeOffset = this.duration + this.gap;

        int totalSots = Helper.findDifferenceInMin(dailySlot.getStartTime(),  dailySlot.getEndTime()) / timeOffset;

        boolean[] slotsAvailability = new boolean[totalSots];

        ArrayList<Interval> booked = getBookedIntervals();
        Iterator<Interval> bookedIterator = booked.iterator();

        Interval currentInterval = bookedIterator.hasNext()? bookedIterator.next() : null;

        Date slotStartime = Helper.getTime(date, dailySlot.getStartTime());
        Date slotEndime = Helper.addDureation(slotStartime, timeOffset);

        // ASSUMPTION: Slots duration dont change
        for(int i=0; i<slotsAvailability.length; i++){
            if(currentInterval != null && currentInterval.getStartTime().equals(slotStartime)){
                slotsAvailability[i] = false;
                currentInterval = bookedIterator.hasNext()? bookedIterator.next() : null;
            } else{
                slotsAvailability[i] = true;
            }
            slotStartime = slotEndime;
            slotEndime = Helper.addDureation(slotStartime, timeOffset);
        };

        return getAllIntervals(date, slotsAvailability);
    }

    @Override
    public Schedule getSchedule() {
        return null;
    }


    // Make it thread safe
    @Override
    public MutablePair<Event,Schedule> bookSlot(Date slotStartTime, String requesterEmail) throws ParseException {
        ArrayList<Interval> avail = getAvailableSlots(slotStartTime);
        if(avail==null) return null;

        for(Interval i: avail){
            if(i.getStartTime().equals(slotStartTime)){
                if(this.checkLimits(i.getStartTime())) {
                    Schedule schedule = this.createSchedule(i, requesterEmail);

                    System.out.println("Slot booked successfully at: "+ i.toString());
                    return new MutablePair<>(this, schedule);

                }
            }
        }

        System.out.println("Slot not booked successfully");
        return null;
    }

    @Override
    public String toString() {
        return "Event {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", creator=" + creator.email +
                '}';
    }
}
