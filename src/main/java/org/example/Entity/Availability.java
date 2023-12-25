package org.example.Entity;

import org.example.Utils.Helper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Availability {
    static int MIN_SLOT_SIZE = 30;
    ArrayList<Interval> intervals = new ArrayList<>();

    public Availability(ArrayList<Interval> intervals) {
        this.intervals = intervals;
    }

    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    public static ArrayList<Interval> getAvailableSlots(Date date, ArrayList<Schedule> bookedSchedules) throws ParseException {
        ArrayList<Interval> bookedInterVals = new ArrayList<>();
        for(Schedule s: bookedSchedules){
            bookedInterVals.add(s.getInterval());
        }
        String dateString = Helper.getDateToString(date).split(" ")[0];
        Date startDate = Helper.stringToDate(dateString + " " + "00:00");
        Date endDate = Helper.stringToDate(dateString + " " + "23:59");
        Interval interval = new Interval(startDate, endDate);

        return getAllAvailableIntervals(bookedInterVals, interval);
    }


    public static ArrayList<Interval> getAllAvailableIntervals(ArrayList<Interval> bookedInterVals, Interval interval) throws ParseException {
        // Dividing slots into blocks of 1min
        int timeOffset = 1;

        int totalSots = Helper.findDifferenceInMin(interval.getStartTime(),  interval.getEndTime()) / timeOffset;

        // Each index in array represent if that 1min time is booked or available. False ->Not booked | True -> booked
        boolean[] slotsAvailability = new boolean[totalSots];

        Iterator<Interval> bookedIterator = bookedInterVals.iterator();

        Interval currentInterval = bookedIterator.hasNext()? bookedIterator.next() : null;

        Date slotStartime = interval.getStartTime();

        // Updating boolean array based on the bookedInterval Array
        for(int i=0; i<slotsAvailability.length; i++){
            if(currentInterval !=null && slotStartime.after(currentInterval.startTime) && slotStartime.before(currentInterval.endTime)){
                slotsAvailability[i] = true;
            }

            if(currentInterval !=null && (slotStartime.equals(currentInterval.startTime) || slotStartime.equals(currentInterval.endTime))){
                slotsAvailability[i] = true;
            }
            if(currentInterval!=null && slotStartime.after(currentInterval.endTime)){
                currentInterval = bookedIterator.hasNext() ? bookedIterator.next() : null;
            }

            slotStartime = Helper.addDureation(slotStartime, timeOffset);
        };

        return getAllIntervals(slotsAvailability, interval);
    };

    // Converting boolean array into timeSlots
    private static ArrayList<Interval> getAllIntervals(boolean[] slots, Interval interval) throws ParseException {
        ArrayList<Interval> out = new ArrayList<>();
        int timeOffset = 1;

        Date slotStartime = interval.getStartTime();

        int lastIdx=0;
        int i=0;
        for(; i<slots.length; i++){
            if(slots[i]){
                Date endTime = Helper.addDureation(slotStartime, timeOffset * (i-lastIdx));
                if(Helper.findDifferenceInMin(slotStartime, endTime) > MIN_SLOT_SIZE)
                    out.add(new Interval(slotStartime, endTime));
                lastIdx = i;
                slotStartime = endTime;
            }
        }

        Date endTime = Helper.addDureation(slotStartime, timeOffset * (i-lastIdx));
        if(Helper.findDifferenceInMin(slotStartime, endTime) > MIN_SLOT_SIZE)
            out.add(new Interval(slotStartime, endTime));

        return out;
    }

    static int[] utilFunc(int[] slotsAvailability, ArrayList<Interval> bookedInterVals1, Interval interval){
        Iterator<Interval> bookedIterator = bookedInterVals1.iterator();

        Interval currentInterval = bookedIterator.hasNext()? bookedIterator.next() : null;

        Date slotStartime = interval.getStartTime();

        // Updating boolean array based on the bookedInterval Array
        for(int i=0; i<slotsAvailability.length; i++){
            if(currentInterval !=null && slotStartime.after(currentInterval.startTime) && slotStartime.before(currentInterval.endTime)){
                slotsAvailability[i] ++;
            }

            if(currentInterval !=null && (slotStartime.equals(currentInterval.startTime) || slotStartime.equals(currentInterval.endTime))){
                slotsAvailability[i] ++;
            }
            if(currentInterval!=null && slotStartime.after(currentInterval.endTime)){
                currentInterval = bookedIterator.hasNext() ? bookedIterator.next() : null;
            }
            slotStartime = Helper.addDureation(slotStartime, 1);
        };
        return slotsAvailability;
    }
    public static ArrayList<Interval> getAllAvailableIntervals(ArrayList<Interval> bookedInterVals1, ArrayList<Interval> bookedInterVals2, Date date) throws ParseException {
        String dateString = Helper.getDateToString(date).split(" ")[0];
        Date startDate = Helper.stringToDate(dateString + " " + "00:00");
        Date endDate = Helper.stringToDate(dateString + " " + "23:59");
        Interval interval = new Interval(startDate, endDate);

        // Dividing slots into blocks of 1min
        int timeOffset = 1;

        int totalSots = Helper.findDifferenceInMin(interval.getStartTime(),  interval.getEndTime()) / timeOffset;

        // Each index in array represent if that 1min time is booked or available. False ->Not booked | True -> booked
        int[] slotsAvailability = new int[totalSots];


        utilFunc(slotsAvailability, bookedInterVals1, interval);
        utilFunc(slotsAvailability, bookedInterVals2, interval);

        boolean[] x = new boolean[totalSots];

        for(int i=0; i< slotsAvailability.length; i++){
            if(slotsAvailability[i]==2){
                x[i] = true;
            }else{
                x[i] = false;
            }
        }

        return getAllIntervals(x, interval);
    };
}