package mk.ukim.finki.emt.consultations.studentmanagement.application.kafka.senders;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.StudentAddedToConsultationSlotMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentAddedToConsultationSlotSender {

    private final KafkaTemplate<String, StudentAddedToConsultationSlotMessage> kafkaTemplate;

    public StudentAddedToConsultationSlotSender(KafkaTemplate<String, StudentAddedToConsultationSlotMessage>
                                                            kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName, StudentAddedToConsultationSlotMessage message) {
        kafkaTemplate.send(topicName, message);
    }

}
