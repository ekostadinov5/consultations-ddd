package mk.ukim.finki.emt.consultations.professormanagement.application.kafka.listeners;

import mk.ukim.finki.emt.consultations.professormanagement.application.service.ProfessorService;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.StudentRemovedFromConsultationSlotMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StudentRemovedFromConsultationSlotListener {

    private final ProfessorService professorService;

    public StudentRemovedFromConsultationSlotListener(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @KafkaListener(topics = "student-consultation-slot-remove", groupId = "consultations",
            containerFactory = "studentRemovedFromConsultationSlotKafkaListenerContainerFactory")
    public void listener(StudentRemovedFromConsultationSlotMessage message) {
        this.professorService.removeStudentFromConsultationSlot(message.getProfessorId(),
                message.getConsultationSlotId(), message.getStudentId());
    }

}
