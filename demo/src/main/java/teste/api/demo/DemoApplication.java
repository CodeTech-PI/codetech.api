package teste.api.demo;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

	public static void main(String[] args) throws GeneralSecurityException, IOException {
		GoogleCalendarAuthorizationService calendario = new GoogleCalendarAuthorizationService();

		// Build a new authorized API client service.
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, calendario.getCredentials(HTTP_TRANSPORT))
				.setApplicationName("CodeTech")
				.build();

		// Inserir um novo evento
		// Refer to the Java quickstart on how to setup the environment:
// https://developers.google.com/calendar/quickstart/java
// Change the scope to CalendarScopes.CALENDAR and delete any stored
// credentials.

		Event event = new Event()
				.setSummary("Google I/O 2024")
				.setLocation("800 Howard St., San Francisco, CA 94103")
				.setDescription("A chance to hear more about Google's developer products.");

		DateTime startDateTime = new DateTime("2024-05-28T09:00:00-07:00");
		EventDateTime start = new EventDateTime()
				.setDateTime(startDateTime)
				.setTimeZone("America/Los_Angeles");
		event.setStart(start);

		DateTime endDateTime = new DateTime("2025-05-28T17:00:00-07:00");
		EventDateTime end = new EventDateTime()
				.setDateTime(endDateTime)
				.setTimeZone("America/Los_Angeles");
		event.setEnd(end);

		String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
		event.setRecurrence(Arrays.asList(recurrence));

		EventAttendee[] attendees = new EventAttendee[] {
				new EventAttendee().setEmail("lpage@example.com"),
				new EventAttendee().setEmail("sbrin@example.com"),
		};
		event.setAttendees(Arrays.asList(attendees));

		EventReminder[] reminderOverrides = new EventReminder[] {
				new EventReminder().setMethod("email").setMinutes(24 * 60),
				new EventReminder().setMethod("popup").setMinutes(10),
		};
		Event.Reminders reminders = new Event.Reminders()
				.setUseDefault(false)
				.setOverrides(Arrays.asList(reminderOverrides));
		event.setReminders(reminders);

		String calendarId = "primary";
		event = service.events().insert(calendarId, event).execute();
		System.out.printf("Event created: %s\n", event.getHtmlLink());


		// Insere o evento no calendário primário
		event = service.events().insert("primary", event).execute();
		System.out.printf("Event created: %s\n", event.getHtmlLink());

		// Listar os próximos 10 eventos do calendário primário.
		listUpcomingEvents(service);
	}

	private static void listUpcomingEvents(Calendar service) throws IOException {
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list("primary")
				.setMaxResults(10)
				.setTimeMin(now)
				.setSingleEvents(true)
				.execute();
		List<Event> items = events.getItems();

		if (items.isEmpty()) {
			System.out.println("No upcoming events found.");
		} else {
			System.out.println("Upcoming events");

			// Implementing Bubble Sort to sort events by start time
			bubbleSortEventsByStartTime(items);

			for (Event event : items) {
				DateTime start = event.getStart().getDateTime();
				if (start == null) {
					start = event.getStart().getDate();
				}
				System.out.printf("%s (%s)\n", event.getSummary(), start);
			}
		}
	}

	private static void bubbleSortEventsByStartTime(List<Event> events) {
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
					// Swap events[j] and events[j+1]
					Event temp = events.get(j);
					events.set(j, events.get(j + 1));
					events.set(j + 1, temp);
				}
			}
		}
	}
}
