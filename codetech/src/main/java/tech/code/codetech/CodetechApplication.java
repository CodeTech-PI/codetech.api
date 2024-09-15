    package tech.code.codetech;

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import java.io.IOException;
    import java.security.GeneralSecurityException;

    @SpringBootApplication
    public class CodetechApplication {

        public static void main(String[] args) throws GeneralSecurityException, IOException {
            SpringApplication.run(CodetechApplication.class, args);

        }
    }
