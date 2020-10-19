package mk.ukim.finki.emt.consultations.professormanagement.application.jobs;

import mk.ukim.finki.emt.consultations.professormanagement.application.service.ProfessorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final ProfessorService professorService;

    public ScheduledTasks(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void deleteFinishedConsultationSlots() {
        professorService.deleteFinishedConsultationSlots();
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void createConsultationSlot() {
        professorService.createConsultationSlot();
    }

}
