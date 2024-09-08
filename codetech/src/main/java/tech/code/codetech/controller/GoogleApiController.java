
package tech.code.codetech.controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
        import tech.code.codetech.model.GoogleApi;
import tech.code.codetech.service.GoogleApiAuthorizationService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class GoogleApiController {

    @Autowired
    private GoogleApiAuthorizationService googleApiAuthorizationService;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @PostMapping
    public String createEvent(@RequestBody GoogleApi eventRequest) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, googleApiAuthorizationService.getCredentials(HTTP_TRANSPORT))
                .setApplicationName("CodeTech")
                .build();

        Event event = new Event()
                .setSummary(eventRequest.getSummary())
                .setLocation(eventRequest.getLocation())
                .setDescription(eventRequest.getDescription());

        DateTime startDateTime = new DateTime(eventRequest.getStartDateTime());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone(eventRequest.getTimeZone());
        event.setStart(start);

        DateTime endDateTime = new DateTime(eventRequest.getEndDateTime());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone(eventRequest.getTimeZone());
        event.setEnd(end);

        if (eventRequest.getRecurrence() != null) {
            event.setRecurrence(eventRequest.getRecurrence());
        }

        if (eventRequest.getAttendeesEmails() != null) {
            List<EventAttendee> attendees = new ArrayList<>();
            for (String email : eventRequest.getAttendeesEmails()) {
                attendees.add(new EventAttendee().setEmail(email));
            }
            event.setAttendees(attendees);
        }

        if (eventRequest.getReminders() != null) {
            List<EventReminder> reminderOverrides = new ArrayList<>();
            for (GoogleApi.Reminder reminder : eventRequest.getReminders()) {
                reminderOverrides.add(new EventReminder().setMethod(reminder.getMethod()).setMinutes(reminder.getMinutes()));
            }
            Event.Reminders reminders = new Event.Reminders()
                    .setUseDefault(false)
                    .setOverrides(reminderOverrides);
            event.setReminders(reminders);
        }

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        return "Event created: " + event.getHtmlLink();
    }

    @GetMapping
    public List<Event> listEvents() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, googleApiAuthorizationService.getCredentials(HTTP_TRANSPORT))
                .setApplicationName("CodeTech")
                .build();

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        if (items != null) {
            bubbleSortEventsByStartTime(items);
        }

        return items;
    }

    private void bubbleSortEventsByStartTime(List<Event> events) {
        int n = events.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                DateTime start1 = events.get(j).getStart().getDateTime();
                DateTime start2 = events.get(j + 1).getStart().getDateTime();

                if (start1 == null) {
                    start1 = events.get(j).getStart().getDate();
                }
                if (start2 == null) {
                    start2 = events.get(j + 1).getStart().getDate();
                }

                if (start1.getValue() > start2.getValue()) {
                    Event temp = events.get(j);
                    events.set(j, events.get(j + 1));
                    events.set(j + 1, temp);
                }
            }
        }
    }
}
