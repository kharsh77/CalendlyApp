package org.example.Models;

import lombok.Getter;
import org.example.Entity.ScheduleStatus;

@Getter
public class ConfirmSlotRequest {
    String eventId;
    String scheduleId;
    ScheduleStatus scheduleStatus;
}
