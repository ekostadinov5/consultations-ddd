package mk.ukim.finki.emt.consultations.professormanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProfessorManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfessorManagementApplication.class, args);
    }

}
