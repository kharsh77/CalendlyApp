package org.example.Models;

import lombok.Getter;


@Getter
public class CreateInviteRequest {
    String name;
    String creatorUserId;
    String inviteeUserId;
    String date;
    String startTime;
    Integer duration;
}












