    package tech.code.codetech;

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
    import com.google.api.client.http.javanet.NetHttpTransport;
    import com.google.api.client.json.JsonFactory;
    import com.google.api.client.json.gson.GsonFactory;
    import com.google.api.client.util.DateTime;
    import com.google.api.services.calendar.Calendar;
    import com.google.api.services.calendar.model.*;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import tech.code.codetech.service.GoogleApiAuthorizationService;

    import java.io.IOException;
    import java.security.GeneralSecurityException;
    import java.util.Arrays;
    import java.util.List;


    @SpringBootApplication
    public class CodetechApplication {

        public static void main(String[] args) throws GeneralSecurityException, IOException {
            SpringApplication.run(CodetechApplication.class, args);

        }
    }
