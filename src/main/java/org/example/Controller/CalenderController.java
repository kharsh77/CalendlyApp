package org.example.Controller;

import org.example.CalenderService;
import org.example.Entity.Event;
import org.example.Entity.Interval;
import org.example.Entity.ScheduleStatus;
import org.example.Entity.User;
import org.example.Models.*;
import org.example.UserService;
import org.example.Utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class CalenderController {

    @Autowired
    CalenderService calenderService;


    @PostMapping("/create/event")
    Object createEvent(@RequestBody CreateEventRequest req) throws Exception {

        Interval interval = new Interval(
                Helper.stringToDate(String.format("%s %s", req.getStartDate(), req.getStartTime())),
                Helper.stringToDate(String.format("%s %s", req.getStartDate(), req.getEndTime()))
        );
        try {
            return calenderService.createEvent(
                    req.getName(),
                    req.getUserId(),
                    Helper.stringToDate(String.format("%s 00:00", req.getStartDate())),
                    Helper.stringToDate(String.format("%s 00:00", req.getEndDate())),
                    interval,
                    req.getDuration(),
                    req.getGap(),
                    req.getTotalSlot(),
                    req.getDailyLimit()
            );
        } catch (Exception ex) {
            return ex.toString();
        }
    }


    @PostMapping("/get-available-slots")
    Object getAvailableSlots(@RequestBody GetAvailableSlotsRequest req) throws ParseException {
        try {
            return calenderService.getAvailableSlots(req.getEventId(),
                    Helper.stringToDate(String.format("%s 00:00", req.getDate())));
        } catch (Exception ex) {
            return ex.toString();
        }
    }


    @PostMapping("/event/book-slot")
    Object bookSlot(@RequestBody BookSlotRequest req) throws ParseException {
        try {
            return calenderService.bookSlot(
                    req.getEventId(),
                    Helper.stringToDate(req.getSlot()),
                    req.getEmail()
            );
        } catch (Exception ex) {
            return ex.toString();
        }
    }


    @PostMapping("/get-my-availability")
    Object checkMyAvailability(@RequestBody GetMyAvailabilityRequest req) throws ParseException {
        try {
            return calenderService.checkMyAvailability(req.getUserId(),
                    Helper.stringToDate(String.format("%s 00:00", req.getDate())), false);
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    @PostMapping("/get-my-scheduled-events")
    Object viewMyScheduledEvents(@RequestBody GetMyAvailabilityRequest req) throws ParseException {
        try {
            return calenderService.viewMyScheduledEvents(req.getUserId(),
                    Helper.stringToDate(String.format("%s 00:00", req.getDate())));
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    @PostMapping("/confirm-slot")
    Object confirmSlot(@RequestBody ConfirmSlotRequest req) throws ParseException {
        try {
            return calenderService.confirmSlot(req.getEventId(),
                    req.getScheduleId(),
                    req.getScheduleStatus());
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    @PostMapping("/invite/create")
    Object createInvite(@RequestBody CreateInviteRequest req) throws ParseException {
        try {
            return calenderService.createInvite(req.getName(),
                    req.getCreatorUserId(),
                    req.getInviteeUserId(),
                    Helper.stringToDate(req.getDate()),
                    req.getDuration()
            );
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    @PostMapping("/check-overlap")
    Object checkOverlap(@RequestBody CheckOverlapRequest req) throws ParseException {
        try {
            return calenderService.checkOverlap(
                    req.getUserId(),
                    req.getCheckingForUserId(),
                    Helper.stringToDate(req.getDate())
            );
        } catch (Exception ex) {
            return ex.toString();
        }
    }
}
