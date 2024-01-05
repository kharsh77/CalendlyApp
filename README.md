A calendly similar app which allows users to schedule and block meeting timeslots.

Types of Meeting:
1. Event: An organiser can create a meeting event with available timeSlot. Other user can choose a timeSlot and book a slot for meeting.   
2. InviteEvent:A user can invite other user for a one-one meeting.

Rest APIs:
1. POST /user (register) A user can register with a name and email.
2. POST /event (createEvent): A meeting of type 'Event' can be created.
3. POST /event/slots (bookSlot): A specific timeslot can be booked for meeting type 'Event'.
4. POST /event/slot/book (confirmSlot): Organiser can accept or reject a meeting request.
5. POST /invite (createInvite): A meeting of type 'InviteEvent' can be created.
6. POST /event/slots (getAvailableSlots): Get all unbooked and available slots for a meeting type 'Event'.
7. POST /user/availability (checkMyAvailability): A user can check their available timeSlots for a day.
8. POST /user/slots/scheduled (viewMyScheduledEvents): A user can view a specific booked meeting for a day.
9. POST /user/overlap (checkOverlap): Available timeSlots between two users can be checked.

Postman Collection link: https://www.postman.com/kharsh77/workspace/public-workspace/request/1228171-3aaa508c-2fe1-46e8-8200-cd79499a261d


Assumptions:
1. IST timezone is assumed for all operations
2. Update and delete operations are not supported
3. Operations are assumed to be operated in thread safe condition
4. Meeting can be scheduled with granularity of minutes
5. Meetings have definite endtime and recurring events are not supported


Next Steps:
1. Recurring type of meeting can be extended by the app further.
2. Notification operations can be extended for notifying users when a meeting get created or confirmed.
3. Making few operations thread safe by using locks
4. Handling more edge cases
