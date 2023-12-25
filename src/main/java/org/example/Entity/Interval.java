package org.example.Entity;


import java.util.Date;
import java.util.Objects;

public class Interval implements Comparable<Interval>{
    Date startTime;
    Date endTime;

    public Interval(Date startTime, Date endTime){
        this.startTime=startTime;
        this.endTime=endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return Objects.equals(startTime, interval.startTime) &&
                Objects.equals(endTime, interval.endTime);
    }

    @Override
    public int compareTo(Interval o) {
        Date val = ((Interval) o).getStartTime();
        return val.before(this.getStartTime()) ? 1 : -1;
    }

    @Override
    public String toString() {
        return "Slot starts:" + startTime.toString();
    }
}
