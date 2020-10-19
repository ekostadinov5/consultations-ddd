package mk.ukim.finki.emt.consultations.studentmanagement.application.kafka.senders;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.message.StudentRemovedFromConsultationSlotMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentRemovedFromConsultationSlotSender {

    private final KafkaTemplate<String, StudentRemovedFromConsultationSlotMessage> kafkaTemplate;

    public StudentRemovedFromConsultationSlotSender(KafkaTemplate<String, StudentRemovedFromConsultationSlotMessage>
                                                            kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName, StudentRemovedFromConsultationSlotMessage message) {
        kafkaTemplate.send(topicName, message);
    }

}
