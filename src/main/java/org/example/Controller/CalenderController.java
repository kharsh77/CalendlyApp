package org.example.Controller;

import org.example.CalenderService;
import org.example.Entity.Interval;
import org.example.Models.*;
import org.example.Utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class CalenderController {

    @Autowired
    CalenderService calenderService;


    @PostMapping("/event")
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
            return new ErrorResponse(ex.getMessage());
        }
    }


    @PostMapping("/event/slots")
    Object getAvailableSlots(@RequestBody GetAvailableSlotsRequest req) throws ParseException {
        try {
            return calenderService.getAvailableSlots(req.getEventId(),
                    Helper.stringToDate(String.format("%s 00:00", req.getDate())));
        } catch (Exception ex) {
            return new ErrorResponse(ex.getMessage());
        }
    }


    @PostMapping("/event/slot/book")
    Object bookSlot(@RequestBody BookSlotRequest req) throws ParseException {
        try {
            return calenderService.bookSlot(
                    req.getEventId(),
                    Helper.stringToDate(String.format("%s %s", req.getDate(), req.getStartTime())),
                    req.getEmail()
            );
        } catch (Exception ex) {
            return new ErrorResponse(ex.getMessage());
        }
    }


    @PostMapping("/user/availability")
    Object checkMyAvailability(@RequestBody GetMyAvailabilityRequest req) throws ParseException {
        try {
            return calenderService.checkMyAvailability(req.getUserId(),
                    Helper.stringToDate(String.format("%s 00:00", req.getDate())), false);
        } catch (Exception ex) {
            return new ErrorResponse(ex.getMessage());
        }
    }

    @PostMapping("/user/slots/scheduled")
    Object viewMyScheduledEvents(@RequestBody GetMyAvailabilityRequest req) throws ParseException {
        try {
            return calenderService.viewMyScheduledEvents(req.getUserId(),
                    Helper.stringToDate(String.format("%s 00:00", req.getDate())));
        } catch (Exception ex) {
            return new ErrorResponse(ex.getMessage());
        }
    }

    @PostMapping("/slot/confirm")
    Object confirmSlot(@RequestBody ConfirmSlotRequest req) throws ParseException {
        try {
            return calenderService.confirmSlot(req.getEventId(),
                    req.getScheduleId(),
                    req.getScheduleStatus());
        } catch (Exception ex) {
            return new ErrorResponse(ex.getMessage());
        }
    }

    @PostMapping("/invite")
    Object createInvite(@RequestBody CreateInviteRequest req) throws ParseException {
        try {
            return calenderService.createInvite(req.getName(),
                    req.getCreatorUserId(),
                    req.getInviteeUserId(),
                    Helper.stringToDate(String.format("%s %s", req.getDate(), req.getStartTime())),
                    req.getDuration()
            );
        } catch (Exception ex) {
            return new ErrorResponse(ex.getMessage());
        }
    }

    @PostMapping("/user/overlap")
    Object checkOverlap(@RequestBody CheckOverlapRequest req) throws ParseException {
        try {
            return calenderService.checkOverlap(
                    req.getUserId(),
                    req.getCheckingForUserId(),
                    Helper.stringToDate(req.getDate())
            );
        } catch (Exception ex) {
            return new ErrorResponse(ex.getMessage());
        }
    }
}
