package tech.code.codetech.model;

import com.google.api.client.util.DateTime;

import java.time.LocalDateTime;
import java.util.List;

public class GoogleApi {

    private String summary;
//    private String location;


    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

//    public String getLocation() {
//        return location;
//    }

//    public void setLocation(String location) {
//        this.location = location;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
