package tech.code.codetech.api.controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.produto.response.ProdutoResponseDto;
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
import java.util.UUID;

@Tag(name = "GoogleApi")
@RestController
@RequestMapping("/api/events")
public class GoogleApiController {

    @Autowired
    private GoogleApiAuthorizationService googleApiAuthorizationService;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    // CONFIGURAÇÃO SWAGGER criarEvento()
    @Operation(summary = "Criar um evento", description = """
            Esse endpoint permite criar um novo evento na API Calendar.
            
            Respostas:
            - 201: Evento criado com sucesso.
            - 400: Requisição inválida (dados do evento não fornecidos).
            - 500: Erro interno do servidor ao tentar criar o evento.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Verifique se todos os dados necessários para criar o evento foram fornecidos corretamente."),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro ao tentar criar o evento. Verifique a configuração da API e as credenciais de autenticação.")
    })

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
        return "Event created: " + event.getHtmlLink() + "\n Id: " + event.getId();
    }

    // CONFIGURAÇÃO SWAGGER listEvents()
    @Operation(summary = "Listar todos os eventos", description = """
            Esse endpoint permite listar todos os eventos na API Calendar:
            
            - Retorna uma lista de objetos representando cada evento.
            
            Respostas:
            
            - 200: Requisição sucedida. Retorna a lista de eventos em JSON.
            - 204: Nenhum evento encontrado no calendário.
            - 500: Erro ao acessar a API Calendar. Verifique as credenciais e a configuração da API.
            - 403: Acesso negado. Verifique as permissões de autenticação.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GoogleApi.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhum evento encontrado no calendário."),
            @ApiResponse(responseCode = "500", description = "Erro ao acessar a API Calendar. Verifique as credenciais e a configuração da API."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Verifique as permissões de autenticação.")
    })

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

            // Verificar se o ID é um UUID válido
            try {
                UUID uuid = UUID.fromString(event.getId());
                googleApi.setId(uuid);
            } catch (IllegalArgumentException e) {
                // Caso o ID não seja um UUID válido, você pode lidar com isso de forma diferente
                // Por exemplo, armazene o ID como string ou use um UUID fictício
                googleApi.setId(UUID.randomUUID());  // Ou algum outro valor padrão
            }

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


    // CONFIGURAÇÃO SWAGGER buscarEventoPorId()
    @Operation(summary = "Buscar um evento pelo ID", description = """
            Esse endpoint permite listar um evento na API Calendar:
            
            - Retorna um objeto representando um evento.
            
            Respostas:
            
            - 200: Requisição sucedida. Retorna a lista de eventos em JSON.
            - 204: Nenhum evento encontrado no calendário.
            - 500: Erro ao acessar a API Calendar. Verifique as credenciais e a configuração da API.
            - 403: Acesso negado. Verifique as permissões de autenticação.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GoogleApi.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhum evento encontrado no calendário."),
            @ApiResponse(responseCode = "500", description = "Erro ao acessar a API Calendar. Verifique as credenciais e a configuração da API."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Verifique as permissões de autenticação.")
    })
    @GetMapping("/{eventId}")
    public GoogleApi buscarEventoPorId(@PathVariable UUID eventId) throws GeneralSecurityException, IOException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, googleApiAuthorizationService.getCredentials(HTTP_TRANSPORT))
                .setApplicationName("CodeTech")
                .build();

        // Converte o UUID para String para usar na API do Google
        String eventIdString = eventId.toString();

        // Obtém o evento atual usando o eventId (convertido para String)
        Event event = service.events().get("primary", eventIdString).execute();

        GoogleApi googleApi = new GoogleApi();
        googleApi.setSummary(event.getSummary());
        googleApi.setDescription(event.getDescription());

        // Define o ID do evento como UUID no GoogleApi
        googleApi.setId(eventId);

        // Converter as datas para LocalDateTime
        DateTime startDateTime = event.getStart().getDateTime();
        DateTime endDateTime = event.getEnd().getDateTime();

        if (startDateTime != null) {
            googleApi.setStartDateTime(formatDateTimeToLocalDateTime(startDateTime));
        }
        if (endDateTime != null) {
            googleApi.setEndDateTime(formatDateTimeToLocalDateTime(endDateTime));
        }

        return googleApi;
    }


    // CONFIGURAÇÃO SWAGGER atualizarEvento()
    @Operation(summary = "Atualiza um evento pelo ID", description = """
            Esse endpoint permite atualizar um evento da API Calendar:
            
            Respostas:
            
            - 200: Requisição sucedida. Retorna a lista de eventos em JSON.
            - 204: Nenhum evento encontrado no calendário.
            - 500: Erro ao acessar a API Calendar. Verifique as credenciais e a configuração da API.
            - 403: Acesso negado. Verifique as permissões de autenticação.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhum evento encontrado no calendário."),
            @ApiResponse(responseCode = "500", description = "Erro ao acessar a API Calendar. Verifique as credenciais e a configuração da API."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Verifique as permissões de autenticação.")
    })
    @PutMapping("/{eventId}")
    public String atualizarEvento(@PathVariable UUID eventId, @RequestBody GoogleApi eventRequest) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, googleApiAuthorizationService.getCredentials(HTTP_TRANSPORT))
                .setApplicationName("CodeTech")
                .build();

        // Converte o UUID para String para usar na API do Google
        String eventIdString = eventId.toString();

        // Obtém o evento atual usando o eventId (convertido para String)
        Event event = service.events().get("primary", eventIdString).execute();
        event.setSummary(eventRequest.getSummary());
        event.setDescription(eventRequest.getDescription());

        // Atualizar datas
        LocalDateTime startDateTime = eventRequest.getStartDateTime();
        LocalDateTime endDateTime = eventRequest.getEndDateTime();

        DateTime startGoogleDateTime = new DateTime(ZonedDateTime.of(startDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli());
        DateTime endGoogleDateTime = new DateTime(ZonedDateTime.of(endDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli());

        event.setStart(new EventDateTime().setDateTime(startGoogleDateTime));
        event.setEnd(new EventDateTime().setDateTime(endGoogleDateTime));

        // Atualiza o evento
        event = service.events().update("primary", eventIdString, event).execute();

        return "Evento atualizado: " + event.getHtmlLink() + "Id: " + event.getId();
    }


    @DeleteMapping("/{eventId}")
    public String deletarEvento(@PathVariable UUID eventId) throws GeneralSecurityException, IOException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, googleApiAuthorizationService.getCredentials(HTTP_TRANSPORT))
                .setApplicationName("CodeTech")
                .build();

        // Converte UUID para String para usar na API do Google
        String eventIdString = eventId.toString();

        // Deleta o evento no Google Calendar
        service.events().delete("primary", eventIdString).execute();

        return "Evento deletado com sucesso!";
    }


    private LocalDateTime dateTimeToLocalDateTime(DateTime dateTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime.getValue()), ZoneId.systemDefault());
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
                if (description1.compareTo(description2) > 0) {
                    // Trocar eventos se a descrição1 é maior que a descrição2
                    Event temp = events.get(j);
                    events.set(j, events.get(j + 1));
                    events.set(j + 1, temp);
                }
            }
        }
    }
}
