package org.example.Entity;

import org.example.Utils.Helper;

import java.util.ArrayList;
import java.util.Date;

public class Schedule implements Comparable<Schedule>{
    static int counter=0;
    String id;
    String eventId;
    Interval interval;
    ScheduleStatus status = ScheduleStatus.PENDING;
    ArrayList<Invite> invites = new ArrayList<>();

    public Schedule(String eventId, Interval interval, String inviteeEmail){
        this.id = Integer.toString(++counter);
        this.eventId = eventId;
        this.interval = interval;
        this.invites.add(new Invite(inviteeEmail, InviteStatus.CONFIRMED));
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public void setInvites(ArrayList<Invite> invites) {
        this.invites = invites;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public Interval getInterval() {
        return interval;
    }

    public ArrayList<Invite> getInvites() {
        return invites;
    }

    public String getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public boolean isSameDate(Date date){
        return Helper.getDateToString(date).split(" ")[0]
                .equals(
                        Helper.getDateToString(this.interval.getStartTime()).split(" ")[0]
                );
    };


    @Override
    public int compareTo(Schedule o) {
        Interval val = ((Schedule) o).getInterval();
        return val.compareTo(this.interval);
    }
}
