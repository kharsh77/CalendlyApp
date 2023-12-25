package org.example;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.example.Entity.Availability;
import org.example.Entity.Interval;
import org.example.Entity.OneEvent;
import org.example.Entity.User;
import org.example.Utils.Helper;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */

    static Date parseDate(String str) throws ParseException {
        return Helper.stringToDate(str);
    }
    @Test
    public void shouldAnswerWithTrue() throws ParseException {
        UserService userService = new UserService();
        CalenderService calenderService = new CalenderService(userService);


//        User user1 = userService.register("John", "john@gmail.com");
//
//
//        Interval NineFiveInterval = new Interval(parseDate("2023-12-23 09:00"), parseDate("2023-12-23 17:00"));
//
//        OneEvent interviewEvent = calenderService.createEvent("Interview:: System Engineer", user1,
//                parseDate("2023-12-23 00:00"), parseDate("2023-12-26 00:00"), NineFiveInterval,
//                35, 10, 4, 2);
//
//        assertTrue(interviewEvent.getAvailableSlots(parseDate("2023-12-24")).size() > 0);
//        Helper.print(interviewEvent.getAvailableSlots(parseDate("2023-12-24")));

//        assertTrue(interviewEvent.bookSlot(parseDate("2023-12-24 10:30")));
//        assertTrue(interviewEvent.bookSlot(parseDate("2023-12-24 09:00")));
//        assertFalse(interviewEvent.bookSlot(parseDate("2025-12-24 11:15")));
//        assertFalse(interviewEvent.bookSlot(parseDate("2023-12-24 09:45")));
//        assertFalse(interviewEvent.bookSlot(parseDate("2023-12-24 11:15")));
//
//        assertTrue(interviewEvent.getAvailableSlots(parseDate("2023-12-25")).size() > 0);
//        Helper.print(interviewEvent.getAvailableSlots(parseDate("2023-12-25")));
//
//        assertTrue(interviewEvent.bookSlot(parseDate("2023-12-25 12:45")));
//        assertTrue(interviewEvent.bookSlot(parseDate("2023-12-25 09:00")));
//        assertFalse(interviewEvent.bookSlot(parseDate("2023-12-25 11:15")));



        assertTrue( true );
    }

    @Test
    public void checkAvailabilitySlotLogic() throws ParseException {
        Interval i = new Interval(Helper.stringToDate("2023-12-25 00:00"), Helper.stringToDate("2023-12-25 23:59"));
        Interval i1 = new Interval(Helper.stringToDate("2023-12-25 00:05"), Helper.stringToDate("2023-12-25 00:10"));
        Interval i2 = new Interval(Helper.stringToDate("2023-12-25 00:15"), Helper.stringToDate("2023-12-25 00:20"));
        Interval i3 = new Interval(Helper.stringToDate("2023-12-25 00:25"), Helper.stringToDate("2023-12-25 00:30"));
        ArrayList<Interval> x = new ArrayList<>();
        x.add(i1);
        x.add(i2);
        x.add(i3);
        ArrayList<Interval> c = Availability.getAllAvailableIntervals(x, i);
        Helper.print(c);
        assertTrue( true );
    }
}
