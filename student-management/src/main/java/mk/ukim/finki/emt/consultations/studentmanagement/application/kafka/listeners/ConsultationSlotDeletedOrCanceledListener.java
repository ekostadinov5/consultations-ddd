package mk.ukim.finki.emt.consultations.studentmanagement.application.kafka.listeners;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.ConsultationSlotDeletedOrCanceledMessage;
import mk.ukim.finki.emt.consultations.studentmanagement.application.service.StudentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsultationSlotDeletedOrCanceledListener {

    private final StudentService studentService;

    public ConsultationSlotDeletedOrCanceledListener(StudentService studentService) {
        this.studentService = studentService;
    }

    @KafkaListener(topics = "consultation-slot-delete-or-cancel", groupId = "consultations",
            containerFactory = "consultationSlotDeletedOrCanceledKafkaListenerContainerFactory")
    public void listener(ConsultationSlotDeletedOrCanceledMessage message) {
        this.studentService.removeFromDeletedOrCanceledConsultationSlot(message.getConsultationSlotId(),
                message.getProfessorTitleAndFullName(), message.getDate(), message.getStart(), message.getNotify());
    }

}
