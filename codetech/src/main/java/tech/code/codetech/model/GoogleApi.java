package tech.code.codetech.model;

import com.google.api.client.util.DateTime;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GoogleApi {

    private UUID id;
    private String summary;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public GoogleApi() {
        this.id = UUID.randomUUID(); // Gera um UUID único automaticamente
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
