package org.example.Entity;

public class Invite {
    String email;
    InviteStatus inviteStatus;
    Boolean isDeleted = false;

    public Invite(String email, InviteStatus inviteStatus) {
        this.email = email;
        this.inviteStatus = inviteStatus;
    }

    public void setInviteStatus(InviteStatus inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getEmail() {
        return email;
    }

    public InviteStatus getInviteStatus() {
        return inviteStatus;
    }
}
