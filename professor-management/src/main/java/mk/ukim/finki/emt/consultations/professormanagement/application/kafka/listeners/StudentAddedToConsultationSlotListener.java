package mk.ukim.finki.emt.consultations.professormanagement.application.kafka.listeners;

import mk.ukim.finki.emt.consultations.professormanagement.application.service.ProfessorService;
import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.StudentAddedToConsultationSlotMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StudentAddedToConsultationSlotListener {

    private final ProfessorService professorService;

    public StudentAddedToConsultationSlotListener(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @KafkaListener(topics = "student-consultation-slot-add", groupId = "consultations",
            containerFactory = "studentAddedToConsultationSlotKafkaListenerContainerFactory")
    public void listener(StudentAddedToConsultationSlotMessage message) {
        this.professorService.addStudentToConsultationSlot(message.getProfessorId(), message.getConsultationSlotId(),
                message.getStudentId(), message.getIndex(), message.getFullName(), message.getSubjectId(),
                message.getNote(), message.getCreatedOn());
    }

}
