package tech.code.codetech.controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.model.GoogleApi;
import tech.code.codetech.service.GoogleApiAuthorizationService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class GoogleApiController {

    @Autowired
    private GoogleApiAuthorizationService googleApiAuthorizationService;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @PostMapping
    public String criarEvento(@RequestBody GoogleApi eventRequest) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, googleApiAuthorizationService.getCredentials(HTTP_TRANSPORT))
                .setApplicationName("CodeTech")
                .build();


        Event event = new Event()
                .setSummary(eventRequest.getSummary())
                .setDescription(eventRequest.getDescription());

        // Converter LocalDateTime para DateTime
        LocalDateTime startDateTime = eventRequest.getStartDateTime();
        LocalDateTime endDateTime = eventRequest.getEndDateTime();

        DateTime startGoogleDateTime = new DateTime(ZonedDateTime.of(startDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli());
        DateTime endGoogleDateTime = new DateTime(ZonedDateTime.of(endDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli());

        EventDateTime start = new EventDateTime().setDateTime(startGoogleDateTime);
        EventDateTime end = new EventDateTime().setDateTime(endGoogleDateTime);
        event.setStart(start);
        event.setEnd(end);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        return "Event created: " + event.getHtmlLink();
    }

    private LocalDateTime dateTimeToLocalDateTime(DateTime dateTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime.getValue()), ZoneId.systemDefault());
    }

    @GetMapping
    public List<GoogleApi> listEvents() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, googleApiAuthorizationService.getCredentials(HTTP_TRANSPORT))
                .setApplicationName("CodeTech")
                .build();

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(100)
                .setTimeMin(now)
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        if (items != null) {
            bubbleSortEventsByDescription(items);
        }
        List<GoogleApi> listaItems = new ArrayList<>();
        for (Event event : items) {
            GoogleApi googleApi = new GoogleApi();
            googleApi.setSummary(event.getSummary());
            googleApi.setDescription(event.getDescription());
//            googleApi.setLocation(event.getLocation());

            DateTime startDateTime = event.getStart().getDateTime();
            DateTime endDateTime = event.getEnd().getDateTime();

            // Formatar as datas para BRT (UTC-3)
            if (startDateTime != null) {
                googleApi.setStartDateTime(formatDateTimeToLocalDateTime(startDateTime));
            }
            if (endDateTime != null) {
                googleApi.setEndDateTime(formatDateTimeToLocalDateTime(endDateTime));
            }

            listaItems.add(googleApi);
        }
        return listaItems;
    }

    private static LocalDateTime formatDateTimeToLocalDateTime(DateTime dateTime) {
        // Criar um objeto Instant a partir do timestamp
        Instant instant = Instant.ofEpochMilli(dateTime.getValue());

        // Ajustar o Instant para o fuso horário BRT (UTC-3)
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("America/Sao_Paulo"));

        return zonedDateTime.toLocalDateTime();
    }

    private void bubbleSortEventsByDescription(List<Event> events) {
        int n = events.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String description1 = events.get(j).getDescription();
                String description2 = events.get(j + 1).getDescription();

                // Verificar se as descrições são nulas e tratar como vazias
                if (description1 == null) {
                    description1 = "";
                }
                if (description2 == null) {
                    description2 = "";
                }

                // Comparar as descrições em ordem crescente
                if (description1.compareTo(description2) < 0) {
                    // Trocar eventos se a descrição1 é maior que a descrição2
                    Event temp = events.get(j);
                    events.set(j, events.get(j + 1));
                    events.set(j + 1, temp);
                }
            }
        }
    }


}
