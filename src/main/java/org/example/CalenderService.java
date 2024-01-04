package org.example;

import org.apache.commons.lang3.tuple.MutablePair;
import org.example.Entity.*;
import org.example.Interface.CalenderInterface;
import org.example.Utils.Helper;

import java.text.ParseException;
import java.util.*;

public class CalenderService implements CalenderInterface {
    UserService userService;
    HashMap<String, Event> events = new HashMap<>();
    HashMap<String, ArrayList<String>> userEventMapping = new HashMap<>();
    HashMap<String, ArrayList<Schedule>> userScheduleMapping = new HashMap<>();

    CalenderService(UserService userService){
        this.userService = userService;
    }


    void updateEvent(Event event) {
        this.events.put(event.getId(), event);
    }

    void updateUserEventMapping(String userId, String eventId){
        ArrayList<String> x = this.userEventMapping.getOrDefault(userId, new ArrayList<>());
        x.add(eventId);
        this.userEventMapping.put(userId, x);
    }

    ArrayList<String> getUserEventMapping(String userId){
        return this.userEventMapping.getOrDefault(userId, null);
    }

    void updateUserScheduleMapping(String userId, Schedule schedule){
        ArrayList<Schedule> x = this.userScheduleMapping.getOrDefault(userId, new ArrayList<>());
        int idx=-1;
        for(int i=0; i<x.size(); i++){
            if(x.get(i).getId().equals(schedule.getId())){
                idx = i;
            }
        }
        if(idx!=-1){
            x.remove(idx);
        }
        x.add(schedule);
        Collections.sort(x);

        this.userScheduleMapping.put(userId, x);
    }

    public ArrayList<Event> getAllEvents(ArrayList<String> eventIds){
        ArrayList<Event> out = new ArrayList<>();
        for(Map.Entry<String, Event> e: this.events.entrySet()){
            if(eventIds.contains(e.getKey())){
                out.add(e.getValue());
            }
        }
        return out;
    }

    public ArrayList<Schedule> getAllSchedules(ArrayList<String> eventIds, Date date){
        ArrayList<Schedule> out = new ArrayList<>();
        for(Map.Entry<String, Event> e: this.events.entrySet()){
            if(eventIds.contains(e.getKey())){
                // Add all schedules of current date
                for(Schedule s: e.getValue().getSchedules()){
                    if(s.isSameDate(date) && s.getStatus().equals(ScheduleStatus.CONFIRMED)){
                        out.add(s);
                    }
                }

            }
        }
        return out;
    }

    Schedule updateSchedule(Event event, String scheduleId, ScheduleStatus status){
        Schedule x = null;
        for(Schedule s: event.getSchedules()){
            if(s.getId().equals(scheduleId)){
                s.setStatus(status);
                x = s;
            }
        }
        if(x == null) return null;
        this.updateUserScheduleMapping(event.getCreator().getId(), x);
        return x;
    }


    @Override
    public void createEvent(String name, User creator, Date startDate, Date endDate,
                                Interval dailySlot, Integer duration, Integer gap, Integer totalSlots, Integer dailyLimit) {

        System.out.println("-----Operation: createEvent----");
        OneEvent oneEvent= new OneEvent(name, creator, startDate, endDate, dailySlot, duration, gap, totalSlots, dailyLimit);
        this.updateEvent(oneEvent);
        this.updateUserEventMapping(creator.getId(), oneEvent.getId());
        System.out.println("New Event Created: "+ oneEvent.toString() );
        System.out.println("-----End of an operation----\n\n");
    }

    @Override
    public void getAvailableSlots(String eventId, Date date) throws ParseException {
        System.out.println(String.format("-----Operation: getAvailableSlots for eventId: %s----", eventId));
        Event event = this.getEvent(eventId);
        Availability c = new Availability(event.getAvailableSlots(date));
        this.printAvailability(c);
        System.out.println("-----End of an operation----\n\n");
    }

    @Override
    public void bookSlot(String eventId, Date slotTime, String bookingEmail) throws ParseException {
        System.out.println(String.format("-----Operation: bookSlot for eventId: %s----", eventId));
        Event event = this.getEvent(eventId);
        if(event!=null){
            MutablePair<Event,Schedule> pair = event.bookSlot(slotTime, bookingEmail);
            if(pair != null) {
                this.updateUserScheduleMapping(event.getCreator().getId(), pair.getRight());
                this.updateEvent(pair.getLeft());
                System.out.println(" Slot booked. Waiting for confirmation for slot at: " + slotTime);
                System.out.println("-----End of an operation----\n\n");
                return;
            }
        }

        System.out.println("No such event with eventId: "+ eventId);
        System.out.println("-----End of an operation----\n\n");
    }

    @Override
    public Availability checkMyAvailability(String userId, Date date, boolean skipPrint) throws ParseException {
        if(!skipPrint) {
            System.out.println(String.format("-----Operation: checkMyAvailability for userId: %s----", userId));
        }
        ArrayList<String> eventIds = this.getUserEventMapping(userId);
        ArrayList<Schedule> schedules = this.getAllSchedules(eventIds, date);
        Collections.sort(schedules);

        ArrayList<Interval> x = Availability.getAvailableSlots(date, schedules);
        Availability c = new Availability(x);
        if(!skipPrint) {
            this.printAvailability(c);
            System.out.println("-----End of an operation----\n\n");
        }
        return c;
    }

    @Override
    public void viewMyScheduledEvents(String userId, Date date) {
        System.out.println(String.format("-----Operation: viewMyScheduledEvents for userId: %s----", userId));
        ArrayList<String> eventIds = this.getUserEventMapping(userId);
        ArrayList<Schedule> schedules = new ArrayList<>();
        for(String id: eventIds){
            ArrayList<Schedule> temp = new ArrayList<>();
            Event event = this.getEvent(id);
            schedules.addAll(event.getSchedules(date));
        };

        this.printSchedule(schedules);
        System.out.println("-----End of an operation----\n\n");
    }


    @Override
    public void confirmSlot(String eventId, String scheduleId, ScheduleStatus action) {
        System.out.println(String.format("-----Operation: confirmSlot  eventId: %s scheduleId: %s action: %s----", eventId, scheduleId, action));
        Event event = this.getEvent(eventId);
        Schedule s = this.updateSchedule(event, scheduleId, action);
        if(s==null){
            System.out.println(" Slot not updated");
        } else{
            System.out.println(" Slot updated");
        }
        System.out.println("-----End of an operation----\n\n");
    }


    @Override
    public Availability checkOverlap(String userId1, String userId2, Date date) throws ParseException {
        System.out.println(String.format("---------Operation: checkOverlap for available slots of userId: %s for userId: %s--------", userId1, userId2));
        ArrayList<Interval> i1 =this.checkMyAvailability(userId1, date, true).getIntervals();
        ArrayList<Interval> i2 =this.checkMyAvailability(userId2, date, true).getIntervals();

        Availability.getAllAvailableIntervals(i1, i2, date);
        Availability a = new Availability(i1);
        this.printAvailability(a);
        System.out.println("---------End of an operation--------\n\n");
        return a;
    }

    @Override
    public void createInvite(String name, User creator, User invitee, Date startTime, Integer duration) {
        System.out.println("----------Operation: createInvite---------");
        Interval i = new Interval(startTime, Helper.addDureation(startTime, duration));
        Event event = new InviteEvent(name, creator, invitee, i);

        this.updateEvent(event);
        this.updateUserEventMapping(creator.getId(), event.getId());
        this.updateUserEventMapping(invitee.getId(), event.getId());
        this.updateUserScheduleMapping(creator.getId(), event.getSchedule());
        this.updateUserScheduleMapping(invitee.getId(), event.getSchedule());
        System.out.println("New InviteEvent Created: "+ event.toString() );
        System.out.println("----------End of an operation---------\n\n");
    }

    @Override
    public Event getEvent(String eventId) {
        return this.events.getOrDefault(eventId, null);
    }

    // Utils
    static Date parseDate(String str) throws ParseException {
        return Helper.stringToDate(str);
    }

    public void printSchedule(ArrayList<Schedule> schedules){
        StringBuilder sb = new StringBuilder();
        for(Schedule s: schedules){
            sb.append(String.format(" Schedule:: EventId:%s ScheduleId:%s status:%s from:%s to: %s\n",
                    s.getEventId(), s.getId(),s.getStatus(), s.getInterval().getStartTime(), s.getInterval().getEndTime()));
        }
        System.out.println(sb.toString());
    }

    public void printAvailability(Availability availability){
        StringBuilder sb = new StringBuilder();
        for(Interval i: availability.getIntervals()){
            sb.append("    Available Slot:: from: "+ i.getStartTime() +" to: "+ i.getEndTime() + "\n");
        }
        System.out.println(sb.toString());
    }
}
