package tech.code.codetech.model;

import java.util.List;

public class GoogleApi {

    private String summary;
    private String location;
    private String description;
    private String startDateTime;
    private String endDateTime;
    private String timeZone;
    private List<String> recurrence;
    private List<String> attendeesEmails;
    private List<Reminder> reminders;

    // Getters e setters

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public List<String> getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(List<String> recurrence) {
        this.recurrence = recurrence;
    }

    public List<String> getAttendeesEmails() {
        return attendeesEmails;
    }

    public void setAttendeesEmails(List<String> attendeesEmails) {
        this.attendeesEmails = attendeesEmails;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public static class Reminder {
        private String method;
        private int minutes;

        // Getters e setters

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }
    }
}
